package dto.request;

import java.util.Date;
import java.util.LinkedList;

public class EditFilmRequest extends Request {
    public String getFilmId() {
        return filmId;
    }

    private final String filmId;

    public String getFilmName() {
        return filmName;
    }

    public Date getFilmDate() {
        return filmDate;
    }

    public LinkedList<String> getFilmGenres() {
        return filmGenres;
    }

    public LinkedList<String> getFilmDirectors() {
        return filmDirectors;
    }

    public LinkedList<String> getFilmActors() {
        return filmActors;
    }

    private final String filmName;
    private final Date filmDate;
    private final LinkedList<String> filmGenres;
    private final LinkedList<String> filmDirectors;
    private final LinkedList<String> filmActors;

    public EditFilmRequest(String filmId, String filmName, Date filmDate, LinkedList<String> filmGenres,
                           LinkedList<String> filmDirectors, LinkedList<String> filmActors) {
        this.filmId = filmId;
        this.filmName = filmName;
        this.filmDate = filmDate;
        this.filmGenres = filmGenres;
        this.filmDirectors = filmDirectors;
        this.filmActors = filmActors;
    }

    public String toString() {
        return "CreateEditFilmRequest: request " + "filmId:" + filmId + filmName;
    }

}