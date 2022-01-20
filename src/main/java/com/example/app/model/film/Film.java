package com.example.app.model.film;

import com.example.app.model.IEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

    public void setActors(LinkedList<Integer> actors) {
        this.actors = actors;
    }

    public LinkedList<Integer> getDirectors() {
        return directors;
    }

    public void setDirectors(LinkedList<Integer> directors) {
        this.directors = directors;
    }

    public List<Integer> getGenres() {
        return genres;
    }

    public void setGenres(LinkedList<Integer> newGenres) {
        System.out.println(newGenres);
        this.genres.addAll(newGenres);
        System.out.println(this.genres);
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

    public void setDate(String date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.date = dateFormat.parse(date);
    }

    public void removeGenres(List<Integer> ids){
        for(int id : ids){
            this.genres.removeIf(genreId -> genreId == id);
        }
    }
}
