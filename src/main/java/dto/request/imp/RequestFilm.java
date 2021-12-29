package dto.request.imp;

import app.model.film.Film;

import java.util.Date;
import java.util.List;

public class RequestFilm extends Film {
    public RequestFilm(String id, String newTittle, Date newDate, List<String> newGenres, List<String> newDirectors,
                       List<String> newActors) {
        super(id, newTittle, new Date(), newGenres, newDirectors, newActors);
    }
}
