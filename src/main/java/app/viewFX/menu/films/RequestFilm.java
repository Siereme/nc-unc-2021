package app.viewFX.menu.films;

import app.model.film.Film;

import java.util.Date;
import java.util.LinkedList;

public class RequestFilm extends Film {
    private final String id;

    private String tittle;

    private Date date;

    private final LinkedList<String> genres;

    private LinkedList<String> directors;

    private LinkedList<String> actors;


    public RequestFilm(String id, String newTittle, Date newDate, LinkedList<String> newGenres, LinkedList<String> newDirectors,
                       LinkedList<String> newActors) {
        this.id = id;
        tittle = newTittle;
        date = newDate;
        genres = newGenres;
        directors = newDirectors;
        actors = newActors;
    }

    public RequestFilm(String newTittle, Date newDate, LinkedList<String> newGenres, LinkedList<String> newDirectors,
                       LinkedList<String> newActors) {
        this.id = "";
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

    public LinkedList<String> getActors() {
        return actors;
    }

    public void setActors(LinkedList<String> actors) {
        this.actors = actors;
    }

    public LinkedList<String> getDirectors() {
        return directors;
    }

    public void setDirectors(LinkedList<String> directors) {
        this.directors = directors;
    }

    public LinkedList<String> getGenres() {
        return genres;
    }

    public void setGenres(LinkedList<String> newGenres) {
        this.genres.addAll(newGenres);
    }
}
