package com.app.service.film;

import com.app.model.actor.Actor;
import com.app.model.film.Film;
import com.app.service.AbstractService;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService extends AbstractService<Film> {


    @Override
    public List<Film> findByContains(String query) {
        Criteria regex = Criteria.where("name").regex(query);
        return mongoTemplate.find(new Query().addCriteria(regex), Film.class);
    }
}
