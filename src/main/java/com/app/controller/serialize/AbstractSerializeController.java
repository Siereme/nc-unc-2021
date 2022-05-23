package com.app.controller.serialize;

import com.app.controller.FilmController;
import com.app.model.IEntity;
import com.app.repository.IRepository;
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

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Validated
public abstract class AbstractSerializeController<T extends IEntity> {
    private static final Logger logger = Logger.getLogger(FilmController.class);
    @Autowired
    private ObjectMapper mapper;

    protected abstract IRepository<T> getRepository();
    protected abstract String getFilePath();
    protected abstract TypeReference<List<T>> getRef();
    protected abstract String getRedirectPath();
    protected abstract List<String> checkErrors(List<T> entityList);

    protected List<Integer> getEntityIds(List<? extends IEntity> entityList){
        return entityList.stream()
                .map(IEntity::getId)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<String> getMessages(List<? extends IEntity> entities, String message){
        return entities.stream()
                .map(entity -> entity.getClass().getSimpleName() + " " + entity + " " + message)
                .collect(Collectors.toList());
    }

    protected List<String> getErrorMessages(List<Integer> entityIds, List<? extends IEntity> deserializeEntities, List<? extends IEntity> checkEntities){
         List<? extends IEntity> entities = entityIds.stream()
                .map(id -> deserializeEntities.stream().filter(entity -> id == entity.getId()).findFirst().orElse(null))
                .filter(Objects::nonNull)
                .filter(entity -> checkEntities.stream().noneMatch(checkEntity -> entity.getId() == checkEntity.getId() && entity.hashCode() == checkEntity.hashCode()))
                .collect(Collectors.toList());
         return getMessages(entities, "is not found");
    }


    @Transactional
    @RequestMapping(value = "/export")
    public ResponseEntity<Resource> exportJsonFile() throws IOException {
        List<T> entityList = getRepository().findAll();

        File file = new File(getFilePath());

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
    public ModelAndView importJsonFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        try {

            TypeReference<List<T>> typeReference = getRef();
            ByteArrayInputStream json = new ByteArrayInputStream(file.getBytes());
            List<T> fileEntityList = mapper.readerFor(typeReference).readValue(json);

            List<String> errorMessages = checkErrors(fileEntityList);
            if(errorMessages.size() > 0){
                attributes.addFlashAttribute("errors", errorMessages);
                return new ModelAndView(getRedirectPath() + "/errors");
            }

            List<T> entityList = getRepository().findAll();

            List<T> updateEntityList = new LinkedList<>(fileEntityList);
            updateEntityList.removeAll(entityList);

            List<String> addMassages = getMessages(updateEntityList, "was updated");
            List<String> successMessages = new ArrayList<>(addMassages);

//            updateEntityList.forEach(getRepository()::edit);

            attributes.addFlashAttribute("success", successMessages);
            return new ModelAndView(getRedirectPath() + "/success");
        } catch (Exception ex){
            logger.error(ex);
            attributes.addFlashAttribute("errors", Collections.singletonList(ex));
            return new ModelAndView(getRedirectPath() + "/errors");
        }
    }
}

