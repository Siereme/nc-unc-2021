package model.Film;

import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

public class Film {

    private String id;
    private String tittle;
    private Date date;
    private LinkedList<String> genres;
    private LinkedList<String> directors;
    private LinkedList<String> actors;

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
        System.out.println(newGenres);
        this.genres.addAll(newGenres);
        System.out.println(this.genres);
    }

    public Film(String newTittle, Date newDate, LinkedList<String> newGenres, LinkedList<String> newDirectors,
                LinkedList<String> newActors) {
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

}
