package com.example.app.controller.serialize.imp;

import com.example.app.controller.serialize.AbstractSerializeController;
import com.example.app.model.director.Director;
import com.example.app.repository.DirectorsRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/serialize/directors")
public class DirectorSerializeController extends AbstractSerializeController<Director> {
    public static String DIRECTOR_FILE_PATH = "src/main/resources/database/Directors.json";

    public DirectorSerializeController(DirectorsRepository repository) {
        super(repository, DIRECTOR_FILE_PATH);
    }
}
