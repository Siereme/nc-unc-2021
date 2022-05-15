package com.app.controller.serialize.imp;

import com.app.controller.serialize.AbstractSerializeController;
import com.app.model.actor.Actor;
import com.app.model.film.Film;
import com.app.repository.AbstractRepository;
import com.app.repository.ActorsRepository;
import com.app.repository.FilmsRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping(path = "/serialize/actors")
public class ActorSerializeController extends AbstractSerializeController<Actor> {

    @Autowired
    private ActorsRepository actorsRepository;
    @Autowired
    private FilmsRepository filmsRepository;

    @Override
    protected AbstractRepository<Actor> getRepository() {
        return actorsRepository;
    }

    @SuppressWarnings("unused")
    @Override
    protected String getFilePath() {
        return "src/main/resources/database/Actors.json";
    }

    @Override
    protected TypeReference<List<Actor>> getRef() {
        return new TypeReference<List<Actor>>() {};
    }

    @Override
    protected String getRedirectPath() {
        return "redirect:/actors";
    }

    @Override
    protected List<String> checkErrors(List<Actor> actorList) {

        List<Film> deserializeFilms = new LinkedList<>();

//        actorList.forEach(film -> deserializeFilms.addAll(film.getFilms()));

        List<Integer> filmIds = getEntityIds(deserializeFilms);
//        List<Film> checkFilms = filmsRepository.find(filmIds);

//        List<String> errorFilmsMessages = getErrorMessages(filmIds, deserializeFilms, checkFilms);

//        return new LinkedList<>(errorFilmsMessages);
        return null;
    }


}
