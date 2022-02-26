package com.app.controller.serialize.imp;

import com.app.controller.serialize.AbstractSerializeController;
import com.app.model.IEntity;
import com.app.model.actor.Actor;
import com.app.model.director.Director;
import com.app.model.film.Film;
import com.app.model.genre.Genre;
import com.app.repository.ActorsRepository;
import com.app.repository.DirectorsRepository;
import com.app.repository.FilmsRepository;
import com.app.repository.GenresRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/serialize/films")
public class FilmSerializeController extends AbstractSerializeController<Film> {
    private String filePath = "src/main/resources/database/Films.json";

    @Autowired
    private FilmsRepository filmsRepository;
    @Autowired
    private GenresRepository genresRepository;
    @Autowired
    private ActorsRepository actorsRepository;
    @Autowired
    private DirectorsRepository directorsRepository;

    @Override
    @PostConstruct
    protected void getRepository() {
         super.repository = filmsRepository;
    }

    @Override
    @PostConstruct
    protected void getFilePath() {
        super.filePath = filePath;
    }

    @Override
    protected TypeReference<List<Film>> getRef() {
        return new TypeReference<List<Film>>() {};
    }

    @Override
    protected String getRedirectPath() {
        return "redirect:/films";
    }


    @Override
    protected List<String> checkErrors(List<Film> filmList) {
        List<String> errors = new LinkedList<>();

        List<Genre> deserializeGenres = new LinkedList<>();
        List<Actor> deserializeActors = new LinkedList<>();
        List<Director> deserializeDirectors = new LinkedList<>();

        filmList.forEach(film -> {
            deserializeGenres.addAll(film.getGenres());
            deserializeActors.addAll(film.getActors());
            deserializeDirectors.addAll(film.getDirectors());
        });

        List<Integer> genreIds = getEntityIds(deserializeGenres);
        List<Genre> checkGenres = genresRepository.find(genreIds);

        List<Integer> actorIds = getEntityIds(deserializeActors);
        List<Actor> checkActors = actorsRepository.find(actorIds);
        List<Integer> directorIds = getEntityIds(deserializeDirectors);
        List<Director> checkDirectors = directorsRepository.find(directorIds);

        List<String> errorGenresMessages = getErrorMessages(genreIds, deserializeGenres, checkGenres);
        errors.addAll(errorGenresMessages);

        List<String> errorActorsMessages = getErrorMessages(actorIds, deserializeActors, checkActors);
        errors.addAll(errorActorsMessages);

        List<String> errorDirectorsMessages = getErrorMessages(directorIds, deserializeDirectors, checkDirectors);
        errors.addAll(errorDirectorsMessages);

        return errors;
    }

}
