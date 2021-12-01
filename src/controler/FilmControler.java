package controler;

import model.Film.Film;
import model.Films.Films;
import model.Genre.Genre;

public class FilmControler {
    private Films films;

    public Films getFilms() {
        return films;
    }

    public FilmControler(Films films) {
        this.films = films;
    }

    public Films getFilmsByGenre(Genre genre) {
        Films filmsByGenre = new Films();
        for (Film film : films.getFilms()) {
            if (film.isContainsGenre(genre)) {
                filmsByGenre.addFilm(film);
            }
        }
        return filmsByGenre;
    }


}
