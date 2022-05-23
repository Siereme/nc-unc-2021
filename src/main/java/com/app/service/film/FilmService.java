package com.app.service.film;

import com.app.model.actor.Actor;
import com.app.model.film.Film;
import com.app.model.genre.Genre;
import com.app.repository.FilmsRepository;
import com.app.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class FilmService extends AbstractService<Film> {

    @Autowired
    private FilmsRepository filmsRepository;

    @Override
    public List<Film> findByContains(String query) {
        Criteria regex = Criteria.where("name").regex(query);
        return mongoTemplate.find(new Query().addCriteria(regex), Film.class);
    }

    public void removeGenreFromFilms(Genre genre) {
        Set<Integer> films = genre.getFilmsIds();
        Collection<Film> filmSet = (Collection<Film>) filmsRepository.findAllById(films);
        for (Film film : filmSet) {
            film.getGenresIds().remove(genre.getId());
            filmsRepository.save(film);
        }
    }

    public void addGenreToFilms(Genre genre) {
        Collection<Integer> films = genre.getFilmsIds();
        Collection<Film> filmCollection = (Collection<Film>) filmsRepository.findAllById(films);
        for (Film film : filmCollection) {
            film.getGenresIds().add(genre.getId());
            filmsRepository.save(film);
        }
    }

    public void updateFilmsByGenre(Genre genre) {
        Collection<Integer> films = genre.getFilmsIds();
        Collection<Film> filmCollection = filmsRepository.findAll();
        Integer genreId = genre.getId();
        for (Film film : filmCollection) {
            if (films.contains(film.getId())) {
                film.getGenresIds().add(genreId);
            } else {
                film.getGenresIds().remove(genreId);
            }
            filmsRepository.save(film);
        }
    }

}
