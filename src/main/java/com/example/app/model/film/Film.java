package com.example.app.model.film;

import com.example.app.model.IEntity;

import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

/** Film entity
 * @author Vasiliy, Sergey
 * @version 1.0
 * */

public class Film implements IEntity {

    private final String id;

    private String tittle;

    private Date date;

    private final LinkedList<String> genres;

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

    public Film(){
        id = UUID.randomUUID().toString();
        tittle = "Unknown";
        date = new Date();
        genres = new LinkedList<>();
        directors = new LinkedList<>();
        actors = new LinkedList<>();
    }

    public Film(String newTittle){
        id = UUID.randomUUID().toString();
        tittle = newTittle;
        date = new Date();
        genres = new LinkedList<>();
        directors = new LinkedList<>();
        actors = new LinkedList<>();
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

    public Film(String id, String tittle, Date date) {
        this.id = id;
        this.tittle = tittle;
        this.date = date;
        genres = new LinkedList<>();
        directors = new LinkedList<>();
        actors = new LinkedList<>();
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
