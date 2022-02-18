package com.app.controller.serialize.imp;

import com.app.repository.FilmsRepository;
import com.app.controller.serialize.AbstractSerializeController;
import com.app.model.film.Film;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/serialize/films")
public class FilmSerializeController extends AbstractSerializeController<Film> {
    public static String FILM_FILE_PATH = "src/main/resources/database/Films.json";

    @Autowired
    public FilmSerializeController(FilmsRepository repository) {
        super(repository, FILM_FILE_PATH);
    }

    @Override
    protected TypeReference<List<Film>> getRef() {
        return new TypeReference<List<Film>>() {};
    }

    @Override
    protected String getRedirectPath() {
        return "/films/all";
    }
}
