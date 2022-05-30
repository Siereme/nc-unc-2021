package com.app.controller.serialize.imp;

import com.app.controller.serialize.AbstractSerializeController;
import com.app.model.actor.Actor;
import com.app.model.film.Film;
import com.app.model.genre.Genre;
import com.app.repository.FilmsRepository;
import com.app.repository.GenresRepository;
import com.app.repository.IRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@RestController
@RequestMapping(path = "/serialize/genres")
public class GenreSerializeController extends AbstractSerializeController<Genre> {

    @Autowired
    private GenresRepository genresRepository;
    @Autowired
    private FilmsRepository filmsRepository;

    @Override
    protected IRepository<Genre> getRepository() {
        return genresRepository;
    }

    @Override
    protected String getFilePath() {
        return "src/main/resources/database/Genres.json";
    }

    @Override
    protected TypeReference<List<Genre>> getRef() {
        return new TypeReference<List<Genre>>() {
        };
    }

    @Override
    protected String getRedirectPath() {
        return "redirect:/genres";
    }

    @Override
    protected List<String> checkErrors(List<Genre> genreList) {

        List<Integer> deserializeFilmsIds = new LinkedList<>();

        // TODO использовать set
        for (Genre genre : genreList) {
            Collection<Integer> filmsIds = genre.getFilmsIds();
            for (Integer filmId : filmsIds) {
                if (!deserializeFilmsIds.contains(filmId)) {
                    deserializeFilmsIds.add(filmId);
                }
            }
        }

        List<Integer> checkFilmsIds = filmsRepository.findAll().stream().map(Film::getId).collect(Collectors.toList());

        List<String> errorFilmsMessages =
                getErrorMessages(deserializeFilmsIds, checkFilmsIds, Film.class.getSimpleName());

        return new LinkedList<>(errorFilmsMessages);
    }
}
