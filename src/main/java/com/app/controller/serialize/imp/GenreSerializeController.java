package com.app.controller.serialize.imp;

import com.app.controller.serialize.AbstractSerializeController;
import com.app.model.genre.Genre;
import com.app.repository.GenresRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping(path = "/serialize/genres")
public class GenreSerializeController extends AbstractSerializeController<Genre> {
    private final String filePath = "src/main/resources/database/Genres.json";

    @Autowired
    private GenresRepository genresRepository;

    @Override
    @PostConstruct
    protected void getRepository() {
        super.repository = genresRepository;
    }

    @Override
    @PostConstruct
    protected void getFilePath() {
        super.filePath = filePath;
    }

    @Override
    protected TypeReference<List<Genre>> getRef() {
        return new TypeReference<List<Genre>>() {};
    }

    @Override
    protected String getRedirectPath() {
        return "/genres/all";
    }

    @Override
    protected List<String> checkErrors(List<Genre> entityList) {
        return null;
    }
}
