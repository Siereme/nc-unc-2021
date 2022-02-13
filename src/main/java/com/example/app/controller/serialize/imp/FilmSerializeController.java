package com.example.app.controller.serialize.imp;

import com.example.app.controller.serialize.AbstractSerializeController;
import com.example.app.model.film.Film;
import com.example.app.repository.FilmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/serialize/films")
public class FilmSerializeController extends AbstractSerializeController<Film> {
    public static String FILM_FILE_PATH = "src/main/resources/database/Films.json";

    @Autowired
    public FilmSerializeController(FilmsRepository repository) {
        super(repository, FILM_FILE_PATH);
    }
}
