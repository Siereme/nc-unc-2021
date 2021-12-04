package controller;

import model.Film.Film;
import model.Genre.Genre;
import repository.FilmsRepository;

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
        for (Genre genre : film.getGenres().findAll()) {
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


}
