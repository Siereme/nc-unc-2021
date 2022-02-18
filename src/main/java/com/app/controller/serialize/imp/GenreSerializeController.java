package com.app.controller.serialize.imp;

import com.app.model.genre.Genre;
import com.app.controller.serialize.AbstractSerializeController;
import com.app.repository.GenresRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/serialize/genres")
public class GenreSerializeController extends AbstractSerializeController<Genre> {
    public static String GENRE_FILE_PATH = "src/main/resources/database/Genres.json";

    public GenreSerializeController(GenresRepository repository) {
        super(repository, GENRE_FILE_PATH);
    }

    @Override
    protected TypeReference<List<Genre>> getRef() {
        return new TypeReference<List<Genre>>() {};
    }

    @Override
    protected String getRedirectPath() {
        return "/genres/all";
    }
}
