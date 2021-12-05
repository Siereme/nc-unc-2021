package model.Film;

import model.Actor.Actor;
import model.Director.Director;
import model.Genre.Genre;

import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

import static controller.DirectorController.tittlesToString;
import static controller.GenreController.tittlesToString;
import static controller.ActorController.tittlesToString;

public class Film {

    private String id;
    private String tittle;
    private Date date;
    private LinkedList<Genre> genres;
    private LinkedList<Director> directors;
    private LinkedList<Actor> actors;

    public LinkedList<Actor> getActors() {
        return actors;
    }

    public void setActors(LinkedList<Actor> actors) {
        this.actors = actors;
    }

    public LinkedList<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(LinkedList<Director> directors) {
        this.directors = directors;
    }

    public LinkedList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(LinkedList<Genre> genres) {
        this.genres = genres;
    }

    public Film(String newTittle, Date newDate, LinkedList<Genre> newGenres, LinkedList<Director> newDirectors,
                LinkedList<Actor> newActors) {
        id = UUID.randomUUID().toString();
        tittle = newTittle;
        date = newDate;
        genres = newGenres;
        directors = newDirectors;
        actors = newActors;
    }

    public String getId() {
        return this.id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ").append(id).append("\n").append("Tittle: ").append(tittle).append("\n")
                .append("Date of release: ").append(date).append("\n");
        if (!genres.isEmpty()) {
            sb.append("Genres\n");
            sb.append(tittlesToString(genres));
        } else {
            sb.append("Genres is empty\n");
        }
        if (!directors.isEmpty()) {
            sb.append("Directors\n").append(tittlesToString(directors)).append("\n");
        } else {
            sb.append("Directors is empty\n");
        }
        if (!actors.isEmpty()) {
            sb.append("Actors:\n").append(tittlesToString(actors)).append("\n");
        } else {
            sb.append("Actors is empty\n");
        }
        return new String(sb);
    }

}
