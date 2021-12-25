package dto.response;

import app.model.film.Film;
import dto.controller.ClientServerFilmController;
import dto.request.FindByFilterRequest;

import java.util.LinkedList;

/** request send list of films to client */
public class GetFindByFilterResponse extends Response {

    public LinkedList<Film> getFilms() {
        return films;
    }

    private final LinkedList<Film> films;

    public GetFindByFilterResponse(String name, LinkedList<Film> newFilms) {
        super(name);
        films = newFilms;
    }

    public String toString() {
        if (films.isEmpty()) {
            return "not found";
        } else {
            return "films found!";
        }
    }

}
