package com.app.controller.serialize.imp;

import com.app.controller.serialize.AbstractSerializeController;
import com.app.model.actor.Actor;
import com.app.model.director.Director;
import com.app.model.film.Film;
import com.app.model.genre.Genre;
import com.app.repository.DirectorsRepository;
import com.app.repository.FilmsRepository;
import com.app.repository.GenresRepository;
import com.app.repository.IRepository;
import com.app.repository.ActorsRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/serialize/films")
public class FilmSerializeController extends AbstractSerializeController<Film> {

    @Autowired
    private FilmsRepository filmsRepository;
    @Autowired
    private GenresRepository genresRepository;
    @Autowired
    private ActorsRepository actorsRepository;
    @Autowired
    private DirectorsRepository directorsRepository;

    @Override
    protected IRepository<Film> getRepository() {
        return filmsRepository;
    }

    @Override
    protected String getFilePath() {
        return "src/main/resources/database/Films.json";
    }

    @Override
    protected TypeReference<List<Film>> getRef() {
        return new TypeReference<List<Film>>() {
        };
    }

    @Override
    protected String getRedirectPath() {
        return "redirect:/films";
    }

    @Override
    protected List<String> checkErrors(List<Film> filmList) {

        Set<Integer> deserializeGenresIdsSet = new HashSet<>();
        Set<Integer> deserializeActorsIdsSet = new HashSet<>();
        Set<Integer> deserializeDirectorsIdsSet = new HashSet<>();
        for (Film film : filmList) {
            deserializeActorsIdsSet.addAll(film.getActorsIds());
            deserializeDirectorsIdsSet.addAll(film.getDirectorsIds());
            deserializeGenresIdsSet.addAll(film.getGenresIds());
        }

        List<Integer> deserializeActorsIds = new LinkedList<>(deserializeActorsIdsSet);
        List<Integer> deserializeDirectorIds = new LinkedList<>(deserializeDirectorsIdsSet);
        List<Integer> deserializeGenresIds = new LinkedList<>(deserializeGenresIdsSet);

        List<Integer> checkActorsIds =
                actorsRepository.findAll().stream().map(Actor::getId).collect(Collectors.toList());
        List<String> errorActorMessages =
                getErrorMessages(deserializeActorsIds, checkActorsIds, Actor.class.getSimpleName());

        List<Integer> checkDirectorIds =
                directorsRepository.findAll().stream().map(Director::getId).collect(Collectors.toList());
        List<String> errorDirectorMessages =
                getErrorMessages(deserializeDirectorIds, checkDirectorIds, Director.class.getSimpleName());

        List<Integer> checkGenreIds =
                genresRepository.findAll().stream().map(Genre::getId).collect(Collectors.toList());
        List<String> errorGenreMessages =
                getErrorMessages(deserializeGenresIds, checkGenreIds, Genre.class.getSimpleName());

        List<String> errors = new LinkedList<>();
        errors.addAll(errorActorMessages);
        errors.addAll(errorDirectorMessages);
        errors.addAll(errorGenreMessages);

        return errors;

    }

}
