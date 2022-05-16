package com.app.service.director;

import com.app.model.actor.Actor;
import com.app.model.director.Director;
import com.app.service.AbstractService;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorService extends AbstractService<Director> {
    @Override
    public List<Director> findByContains(String query) {
        Criteria regex = Criteria.where("name").regex(query);
        return mongoTemplate.find(new Query().addCriteria(regex), Director.class);
    }
}
