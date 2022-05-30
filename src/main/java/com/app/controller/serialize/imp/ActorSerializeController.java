package com.app.controller.serialize.imp;

import com.app.controller.serialize.AbstractSerializeController;
import com.app.model.actor.Actor;
import com.app.model.film.Film;
import com.app.repository.IRepository;
import com.app.repository.ActorsRepository;
import com.app.repository.FilmsRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.batch.core.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@RestController
@RequestMapping(path = "/serialize/actors")
public class ActorSerializeController extends AbstractSerializeController<Actor> {

    @Autowired
    private ActorsRepository actorsRepository;
    @Autowired
    private FilmsRepository filmsRepository;

    @Override
    protected IRepository<Actor> getRepository() {
        return actorsRepository;
    }

    @SuppressWarnings("unused")
    @Override
    protected String getFilePath() {
        return "src/main/resources/database/Actors.json";
    }

    @Override
    protected TypeReference<List<Actor>> getRef() {
        return new TypeReference<List<Actor>>() {
        };
    }

    @Override
    protected String getRedirectPath() {
        return "redirect:/actors";
    }


    @Override
    protected List<String> checkErrors(List<Actor> actorList) {

        List<Integer> deserializeFilmsIds = new LinkedList<>();

        // TODO использовать set
        for (Actor actor : actorList) {
            Collection<Integer> filmsIds = actor.getFilmsIds();
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
