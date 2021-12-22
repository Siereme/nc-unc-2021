package dto;

import app.controller.FilmController;
import app.model.film.Film;

import java.util.LinkedList;

public class GetFindByFilterResponse extends Response {

    public LinkedList<Film> getFilms() {
        return films;
    }

    private LinkedList<Film> films;

    public GetFindByFilterResponse(String name, LinkedList<String> actors, LinkedList<String> genres,
                                   LinkedList<String> directors) {
        super(name);
        FilmController filmController = new FilmController();
        films = filmController.filmsBy(actors, genres, directors);
    }

    public String toString() {
        if (films.isEmpty()) {
            return "not found";
        } else {
            FilmController filmController = new FilmController();
            return "films found!";
        }
    }

}
