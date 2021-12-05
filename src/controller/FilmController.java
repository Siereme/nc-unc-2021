package controller;

import model.Actor.Actor;
import model.Director.Director;
import model.Film.Film;
import model.Genre.Genre;
import repository.FilmsRepository;

import java.util.LinkedList;
import java.util.List;

public class FilmController {

    private FilmsRepository repository = new FilmsRepository();

    public FilmController() {
        repository = new FilmsRepository();
    }

    public FilmController(FilmsRepository newRepository) {
        repository = newRepository;
    }

    public List<Film> getFilms() {
        return repository.findAll();
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public boolean isContainsGenre(Genre genreToFind, Film film) {
        for (Genre genre : film.getGenres()) {
            if (genre.equals(genreToFind)) {
                return true;
            }
        }
        return false;
    }

    public FilmsRepository getFilmsByGenre(Genre genre) {
        FilmsRepository filmsByGenre = new FilmsRepository();
        for (Film film : repository.findAll()) {
            if (isContainsGenre(genre, film)) {
                filmsByGenre.create(film);
            }
        }
        return filmsByGenre;
    }

    public void setGenres(int filmInd, LinkedList<Genre> newGenres) {
        repository.findAll().get(filmInd).setGenres(newGenres);
    }

    public void setDirectors(int filmInd, LinkedList<Director> newDirectors) {
        repository.findAll().get(filmInd).setDirectors(newDirectors);
    }

    public void setActors(int filmInd, LinkedList<Actor> newActors) {
        repository.findAll().get(filmInd).setActors(newActors);
    }

}