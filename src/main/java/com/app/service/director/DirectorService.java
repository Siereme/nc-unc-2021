package com.app.service.director;

import com.app.annotation.AddEntityHandler;
import com.app.model.director.Director;
import com.app.model.film.Film;
import com.app.repository.DirectorsRepository;
import com.app.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class DirectorService extends AbstractService<Director> {
    @Autowired
    DirectorsRepository directorsRepository;

    @Override
    public List<Director> findByContains(String query) {
        Criteria regex = Criteria.where("name").regex(query);
        return mongoTemplate.find(new Query().addCriteria(regex), Director.class);
    }

    public void addFilmToDirectors(Film film) {
        Integer filmId = film.getId();
        Collection<Integer> directors = film.getDirectorsIds();
        Collection<Director> directorCollection = (Collection<Director>) directorsRepository.findAllById(directors);
        for (Director director : directorCollection) {
            director.getFilmsIds().add(filmId);
            directorsRepository.save(director);
        }
    }

    public void removeFilmFromDirectors(Film film) {
        Integer filmId = film.getId();
        Collection<Integer> directors = film.getDirectorsIds();
        Collection<Director> directorCollection = (Collection<Director>) directorsRepository.findAllById(directors);
        for (Director director : directorCollection) {
            director.getFilmsIds().remove(filmId);
            directorsRepository.save(director);
        }
    }

    public void updateDirectorsByFilm(Film film) {
        Collection<Integer> directors = film.getDirectorsIds();
        Collection<Director> directorCollection = directorsRepository.findAll();
        Integer filmId = film.getId();
        for (Director director : directorCollection) {
            if (directors.contains(director.getId())) {
                director.getFilmsIds().add(filmId);
            } else {
                director.getFilmsIds().remove(filmId);
            }
            directorsRepository.save(director);
        }

    }

    @AddEntityHandler
    public void insert(Director director){
        directorsRepository.insert(director);
    }

}
