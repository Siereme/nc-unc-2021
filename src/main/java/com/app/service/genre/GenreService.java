package com.app.service.genre;

import com.app.annotation.AddEntityHandler;
import com.app.model.film.Film;
import com.app.model.genre.Genre;
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
        Criteria regex = Criteria.where("tittle").regex(query);
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

    public void removeFilmFromGenre(Film film) {
        Integer filmId = film.getId();
        Collection<Integer> genresIds = film.getGenresIds();
        Collection<Genre> genreCollection = (Collection<Genre>) genresRepository.findAllById(genresIds);
        for (Genre genre : genreCollection) {
            genre.getFilmsIds().remove(filmId);
            genresRepository.save(genre);
        }
    }

    public void updateGenresByFilm(Film film) {
        Integer filmId = film.getId();
        Collection<Integer> genresIds = film.getGenresIds();
        Collection<Genre> genreCollection = genresRepository.findAll();
        for (Genre genre : genreCollection) {
            if (genresIds.contains(genre.getId())) {
                genre.getFilmsIds().add(filmId);
            } else {
                genre.getFilmsIds().remove(filmId);
            }
            genresRepository.save(genre);
        }

    }

    @AddEntityHandler
    public void insert(Genre genre){
        genresRepository.insert(genre);
    }

}
