package com.example.app.model.film;

import com.example.app.model.IEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.*;

/** Film entity
 * @author Vasiliy, Sergey
 * @version 1.0
 * */

public class Film implements IEntity {

    @NotNull
    private int id;

    @NotBlank(message = "Tittle cannot be empty")
    private String tittle;

    @NotNull(message = "Date cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @NotEmpty(message = "Genre list cannot be empty")
    private List<Integer> genres;

    @NotEmpty(message = "Director list cannot be empty")
    private List<Integer> directors;

    @NotEmpty(message = "Actor list cannot be empty")
    private List<Integer> actors;

    public List<Integer> getActors() {
        return actors;
    }

    public void setActors(List<Integer> actors) {
        this.actors = actors;
    }

    public void addActor(int actorId) {
        this.actors.add(actorId);
    }

    public List<Integer> getDirectors() {
        return directors;
    }

    public void addDirector(int directorId) {
        this.directors.add(directorId);
    }

    public void setDirectors(List<Integer> directors) {
        this.directors = directors;
    }

    public List<Integer> getGenres() {
        return genres;
    }

    public void addGenre(int genreId) {
        this.genres.add(genreId);
    }

    public void setGenres(List<Integer> newGenres) {
        this.genres = newGenres;
    }

    public Film(){
        tittle = "";
        date = null;
        genres = new LinkedList<>();
        directors = new LinkedList<>();
        actors = new LinkedList<>();
    }

    public Film(String newTittle){
        tittle = newTittle;
        date = new Date();
        genres = new LinkedList<>();
        directors = new LinkedList<>();
        actors = new LinkedList<>();
    }



    public Film(String newTittle, Date newDate, List<Integer> newGenres, LinkedList<Integer> newDirectors,
                LinkedList<Integer> newActors) {
        tittle = newTittle;
        date = newDate;
        genres = newGenres;
        directors = newDirectors;
        actors = newActors;
    }

    public Film(int id, String tittle, Date date) {
        this.id = id;
        this.tittle = tittle;
        this.date = date;
        genres = new LinkedList<>();
        directors = new LinkedList<>();
        actors = new LinkedList<>();
    }

    public Film(int id, String tittle) {
        this.id = id;
        this.tittle = tittle;
        this.date = null;
        genres = new LinkedList<>();
        directors = new LinkedList<>();
        actors = new LinkedList<>();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.date = new Date();
    }
}
