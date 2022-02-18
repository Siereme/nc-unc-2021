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
        return "/films/all";
    }


    private List<Integer> getEntityIds(List<? extends IEntity> entityList){
        return entityList.stream()
                .map(IEntity::getId)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<String> getErrorMessages(List<Integer> entityIds, List<? extends IEntity> deserializeEntities, List<? extends IEntity> checkEntities){
        return entityIds.stream()
                .filter(id -> checkEntities.stream().noneMatch(entity -> id == entity.getId()))
                .map(id -> deserializeEntities.stream().filter(entity -> id == entity.getId()).findAny().orElse(null))
                .filter(Objects::nonNull)
                .map(entity -> entity.getClass().getSimpleName() + " " + entity.toString() + " " + "is not found")
                .collect(Collectors.toList());
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
        /*List<Actor> checkActors = actorsRepository.find(actorIds);
*/
        // TODO
        List<Actor> checkActors = new LinkedList<>();

        List<Integer> directorIds = getEntityIds(deserializeDirectors);
        List<Director> checkDirectors = directorsRepository.find(directorIds);

        if(genreIds.size() != checkGenres.size()){
            List<String> errorGenresMessages = getErrorMessages(genreIds, deserializeGenres, checkGenres);
            errors.addAll(errorGenresMessages);
        }
        if(actorIds.size() != checkActors.size()){
            List<String> errorActorsMessages = getErrorMessages(actorIds, deserializeActors, checkActors);
            errors.addAll(errorActorsMessages);
        }
        if(actorIds.size() != checkActors.size()){
            List<String> errorActorsMessages = getErrorMessages(directorIds, deserializeDirectors, checkDirectors);
            errors.addAll(errorActorsMessages);
        }
        return errors;
    }

}
