package com.app.controller.serialize.imp;

import com.app.model.director.Director;
import com.app.repository.DirectorsRepository;
import com.app.controller.serialize.AbstractSerializeController;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/serialize/directors")
public class DirectorSerializeController extends AbstractSerializeController<Director> {
    public static String DIRECTOR_FILE_PATH = "src/main/resources/database/Directors.json";

    public DirectorSerializeController(DirectorsRepository repository) {
        super(repository, DIRECTOR_FILE_PATH);
    }

    @Override
    protected TypeReference<List<Director>> getRef() {
        return new TypeReference<List<Director>>() {};
    }

    @Override
    protected String getRedirectPath() {
        return "/directors/all";
    }
}
