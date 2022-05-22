package com.app.service.actor;

import com.app.model.actor.Actor;
import com.app.model.film.Film;
import com.app.repository.ActorsRepository;
import com.app.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ActorService extends AbstractService<Actor> {

    @Autowired
    private ActorsRepository actorsRepository;

    @Override
    public List<Actor> findByContains(String query) {
        Criteria regex = Criteria.where("name").regex(query);
        return mongoTemplate.find(new Query().addCriteria(regex), Actor.class);
    }

    public void addFilmToActor(Film film) {
        Integer filmId = film.getId();
        Collection<Integer> actors = film.getActorsIds();
        Collection<Actor> actorCollection = (Collection<Actor>) actorsRepository.findAllById(actors);
        for (Actor actor : actorCollection) {
            actor.getFilmsIds().add(filmId);
            actorsRepository.save(actor);
        }
    }

}
