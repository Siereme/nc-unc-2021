package com.example.app.controller.serialize.imp;

import com.example.app.controller.serialize.AbstractSerializeController;
import com.example.app.model.genre.Genre;
import com.example.app.repository.GenresRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/serialize/genres")
public class GenreSerializeController extends AbstractSerializeController<Genre> {
    public static String GENRE_FILE_PATH = "src/main/resources/database/Genres.json";

    public GenreSerializeController(GenresRepository repository) {
        super(repository, GENRE_FILE_PATH);
    }
}
