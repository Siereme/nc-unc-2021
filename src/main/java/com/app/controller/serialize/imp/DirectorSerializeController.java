package com.app.controller.serialize.imp;

import com.app.controller.serialize.AbstractSerializeController;
import com.app.model.director.Director;
import com.app.repository.DirectorsRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping(path = "/serialize/directors")
public class DirectorSerializeController extends AbstractSerializeController<Director> {
    private final String filePath = "src/main/resources/database/Directors.json";

    @Autowired
    private DirectorsRepository directorsRepository;

    @Override
    @PostConstruct
    protected void getRepository() {
        super.repository = directorsRepository;
    }

    @Override
    @PostConstruct
    protected void getFilePath() {
        super.filePath = filePath;
    }

    @Override
    protected TypeReference<List<Director>> getRef() {
        return new TypeReference<List<Director>>() {};
    }

    @Override
    protected String getRedirectPath() {
        return "/directors/all";
    }

    @Override
    protected List<String> checkErrors(List<Director> entityList) {
        return null;
    }
}
