package com.app.service.genre;

import com.app.model.actor.Actor;
import com.app.model.director.Director;
import com.app.model.film.Film;
import com.app.model.genre.Genre;
import com.app.repository.ActorsRepository;
import com.app.repository.DirectorsRepository;
import com.app.repository.GenresRepository;
import com.app.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class GenreService extends AbstractService<Genre> {

    @Autowired
    private GenresRepository genresRepository;

    @Override
    public List<Genre> findByContains(String query) {
        Criteria regex = Criteria.where("name").regex(query);
        return mongoTemplate.find(new Query().addCriteria(regex), Genre.class);
    }

    public void addFilmToGenres(Film film) {
        Integer filmId = film.getId();
        Collection<Integer> genres = film.getGenresIds();
        Collection<Genre> genreCollection = (Collection<Genre>) genresRepository.findAllById(genres);
        for (Genre genre : genreCollection) {
            genre.getFilmsIds().add(filmId);
            genresRepository.save(genre);
        }
    }




}
