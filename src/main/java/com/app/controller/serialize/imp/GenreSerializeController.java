package com.app.controller.serialize.imp;

import com.app.controller.serialize.AbstractSerializeController;
import com.app.model.actor.Actor;
import com.app.model.film.Film;
import com.app.model.genre.Genre;
import com.app.repository.FilmsRepository;
import com.app.repository.GenresRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(path = "/serialize/genres")
public class GenreSerializeController extends AbstractSerializeController<Genre> {
    private final String filePath = "src/main/resources/database/Genres.json";

    @Autowired
    private GenresRepository genresRepository;
    @Autowired
    private FilmsRepository filmsRepository;

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
        return "redirect:/genres";
    }

    @Override
    protected List<String> checkErrors(List<Genre> genreList) {
        List<String> errors = new LinkedList<>();

        List<Film> deserializeFilms = new LinkedList<>();

        genreList.forEach(film -> deserializeFilms.addAll(film.getFilms()));

        List<Integer> filmIds = getEntityIds(deserializeFilms);
        List<Film> checkFilms = filmsRepository.find(filmIds);

        if(filmIds.size() != checkFilms.size()){
            List<String> errorFilmsMessages = getErrorMessages(filmIds, deserializeFilms, checkFilms);
            errors.addAll(errorFilmsMessages);
        }
        return errors;
    }
}
