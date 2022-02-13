package com.example.app.controller.serialize;

import com.example.app.repository.AbstractRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;


public  class AbstractSerializeController<T> {

    private final String filePath;
    private final AbstractRepository<T> repository;

    public AbstractSerializeController(AbstractRepository<T> repository, String filePath) {
        this.repository = repository;
        this.filePath = filePath;
    }

    @RequestMapping(value = "/export")
    public ResponseEntity<Resource> exportJsonFile() throws IOException {
        List<T> entityList = repository.findAll();

        File file = new File(filePath);

        TypeReference<List<T>> typeReference = new TypeReference<List<T>>() {};
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

    @RequestMapping("/import")
    public void importJsonFile(@RequestParam("file") MultipartFile file) throws IOException {
        TypeReference<List<T>> typeReference = new TypeReference<List<T>>() {};
        List<T> entityList = mapper.readerFor(typeReference).readValue(new ByteArrayInputStream(file.getBytes()));
        System.out.println(entityList);
    }

    @Autowired
    private ObjectMapper mapper;


}

