package com.app.service.film;

import com.app.model.actor.Actor;
import com.app.model.director.Director;
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
        Criteria regex = Criteria.where("tittle").regex(query);
        return mongoTemplate.find(new Query().addCriteria(regex), Film.class);
    }

    public void removeGenreFromFilms(Genre genre) {
        Integer genreId = genre.getId();
        Set<Integer> films = genre.getFilmsIds();
        Collection<Film> filmSet = (Collection<Film>) filmsRepository.findAllById(films);
        for (Film film : filmSet) {
            film.getGenresIds().remove(genreId);
            filmsRepository.save(film);
        }
    }

    public void removeActorFromFilms(Actor actor) {
        Integer actorId = actor.getId();
        Set<Integer> films = actor.getFilmsIds();
        Collection<Film> filmCollection = (Collection<Film>) filmsRepository.findAllById(films);
        for (Film film : filmCollection) {
            film.getActorsIds().remove(actorId);
            filmsRepository.save(film);
        }
    }

    public void removeDirectorFromFilms(Director director) {
        Integer directorId = director.getId();
        Set<Integer> films = director.getFilmsIds();
        Collection<Film> filmCollection = (Collection<Film>) filmsRepository.findAllById(films);
        for (Film film : filmCollection) {
            film.getDirectorsIds().remove(directorId);
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

    public void addActorToFilms(Actor actor) {
        Collection<Integer> films = actor.getFilmsIds();
        Collection<Film> filmCollection = (Collection<Film>) filmsRepository.findAllById(films);
        for (Film film : filmCollection) {
            film.getActorsIds().add(actor.getId());
            filmsRepository.save(film);
        }
    }

    public void addDirectorToFilm(Director director) {
        Collection<Integer> films = director.getFilmsIds();
        Collection<Film> filmCollection = (Collection<Film>) filmsRepository.findAllById(films);
        for (Film film : filmCollection) {
            film.getDirectorsIds().add(director.getId());
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

    public void updateFilmsByActor(Actor actor) {
        Collection<Integer> films = actor.getFilmsIds();
        Collection<Film> filmCollection = filmsRepository.findAll();
        Integer actorId = actor.getId();
        for (Film film : filmCollection) {
            if (films.contains(film.getId())) {
                film.getActorsIds().add(actorId);
            } else {
                film.getActorsIds().remove(actorId);
            }
            filmsRepository.save(film);
        }
    }

    public void updateFilmsByDirector(Director director) {
        Collection<Integer> films = director.getFilmsIds();
        Collection<Film> filmCollection = filmsRepository.findAll();
        Integer actorId = director.getId();
        for (Film film : filmCollection) {
            if (films.contains(film.getId())) {
                film.getDirectorsIds().add(actorId);
            } else {
                film.getDirectorsIds().remove(actorId);
            }
            filmsRepository.save(film);
        }
    }
}
