package com.app.controller.serialize;

import com.app.controller.FilmController;
import com.app.model.IEntity;
import com.app.repository.AbstractRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.transaction.Transactional;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Validated
public abstract class AbstractSerializeController<T extends IEntity> {
    private static final Logger logger = Logger.getLogger(FilmController.class);
    protected String filePath;
    protected AbstractRepository<T> repository;

    protected abstract void getRepository();
    protected abstract void getFilePath();
    protected abstract TypeReference<List<T>> getRef();
    protected abstract String getRedirectPath();
    protected abstract List<String> checkErrors(List<T> entityList);

    protected List<Integer> getEntityIds(List<? extends IEntity> entityList){
        return entityList.stream()
                .map(IEntity::getId)
                .distinct()
                .collect(Collectors.toList());
    }

    protected List<String> getErrorMessages(List<Integer> entityIds, List<? extends IEntity> deserializeEntities, List<? extends IEntity> checkEntities){
        return entityIds.stream()
                .filter(id -> checkEntities.stream().noneMatch(entity -> id == entity.getId()))
                .map(id -> deserializeEntities.stream().filter(entity -> id == entity.getId()).findAny().orElse(null))
                .filter(Objects::nonNull)
                .map(entity -> entity.getClass().getSimpleName() + " " + entity.toString() + " " + "is not found")
                .collect(Collectors.toList());
    }


    @Transactional
    @RequestMapping(value = "/export")
    public ResponseEntity<Resource> exportJsonFile() throws IOException {
        List<T> entityList = repository.findAll();

        File file = new File(filePath);

        TypeReference<List<T>> typeReference = getRef();
        mapper.writerFor(typeReference).writeValue(new FileOutputStream(file), entityList);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=database.json");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_JSON)
                .body(resource);
    }

    @Transactional
    @RequestMapping("/import")
    public ModelAndView importJsonFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) throws IOException {
        try {

            TypeReference<List<T>> typeReference = getRef();
            ByteArrayInputStream json = new ByteArrayInputStream(file.getBytes());
            List<T> fileEntityList = mapper.readerFor(typeReference).readValue(json);

            List<String> errorMessages = checkErrors(fileEntityList);
            if(errorMessages.size() > 0){
                attributes.addFlashAttribute("errors", errorMessages);
                return new ModelAndView(getRedirectPath() + "/errors");
            }

//            List<T> entityList = repository.findAll();
//
//            List<T> addEntityList = new LinkedList<>(fileEntityList);
//            addEntityList.removeAll(entityList);
//
//            List<T> editEntityList = new LinkedList<>(fileEntityList);
//            editEntityList.removeAll(addEntityList);
//
//            addEntityList.forEach(repository::edit);
//            editEntityList.forEach(repository::edit);
            fileEntityList.forEach(repository::edit);
        } catch (Exception ex){
            logger.error(ex);
        }
        return new ModelAndView(getRedirectPath() + "/all");
    }

    @Autowired
    private ObjectMapper mapper;


}

