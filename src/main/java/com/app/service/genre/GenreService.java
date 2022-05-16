package com.app.service.genre;

import com.app.model.genre.Genre;
import com.app.service.AbstractService;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService extends AbstractService<Genre> {

    @Override
    public List<Genre> findByContains(String query) {
        Criteria regex = Criteria.where("name").regex(query);
        return mongoTemplate.find(new Query().addCriteria(regex), Genre.class);
    }
}
