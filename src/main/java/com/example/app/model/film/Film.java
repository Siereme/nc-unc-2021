package com.example.app.model.film;

import com.example.app.model.IEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/** Film entity
 * @author Vasiliy, Sergey
 * @version 1.0
 * */

public class Film implements IEntity {

    private int id;

    private String tittle;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private List<Integer> genres;

    private LinkedList<Integer> directors;

    private LinkedList<Integer> actors;

    public LinkedList<Integer> getActors() {
        return actors;
    }

    public void setActors(List<Integer> actors) {
        this.actors.addAll(actors);
    }

    public void setActor(int actorId) {
        this.actors.add(actorId);
    }

    public List<Integer> getDirectors() {
        return directors;
    }

    public void setDirector(int directorId) {
        this.directors.add(directorId);
    }

    public void setDirectors(List<Integer> directors) {
        this.directors.addAll(directors);
    }

    public List<Integer> getGenres() {
        return genres;
    }

    public void setGenre(int genreId) {
        this.genres.add(genreId);
    }

    public void setGenres(List<Integer> newGenres) {
        this.genres.addAll((Collection<? extends Integer>) newGenres);
    }

    public Film(){
        tittle = "";
        date = new Date();
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
        this.date = new Date();
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
        if(date != null){
            this.date = date;
        }
        else{
            this.date = new Date();
        }
    }

    public void removeGenres(List<Integer> ids){
        for(int id : ids){
            this.genres.removeIf(genreId -> genreId == id);
        }
    }
}
