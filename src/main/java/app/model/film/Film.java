package app.model.film;

import app.model.IEntity;

import java.util.*;

/** Film entity
 * @author Vasiliy, Sergey
 * @version 1.0
 * */
public class Film implements IEntity {

    private final String id;

    private String tittle;

    private Date date;

    private final List<String> genres;

    private List<String> directors;

    private List<String> actors;


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


    public Film(String newTittle, Date newDate, List<String> newGenres, List<String> newDirectors,
                List<String> newActors) {
        id = UUID.randomUUID().toString();
        tittle = newTittle;
        date = newDate;
        genres = newGenres;
        directors = newDirectors;
        actors = newActors;
    }
    public Film(String newId, String newTittle, Date newDate, List<String> newGenres, List<String> newDirectors,
                List<String> newActors) {
        id = newId;
        tittle = newTittle;
        date = newDate;
        genres = newGenres;
        directors = newDirectors;
        actors = newActors;
    }

    public Film(Film film) {
        id = UUID.randomUUID().toString();
        tittle = film.getTittle();
        date = film.getDate();
        genres = film.getGenres();
        directors = film.getDirectors();
        actors = film.getActors();
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

    public List<String> getActors() {
        return actors;
    }

    public void actorsClear(){ this.actors.clear(); }

    public void setActors(List<String> actors) {
        for(String id : actors){
            boolean isContains = getActors().stream().anyMatch(actor -> Objects.equals(actor, id));
            if(!isContains) getActors().add(id);
        }
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void directorsClear(){ this.directors.clear(); }

    public void setDirectors(List<String> directors) {
        for(String id : directors){
            boolean isContains = getDirectors().stream().anyMatch(director -> Objects.equals(director, id));
            if(!isContains) getDirectors().add(id);
        }
    }

    public List<String> getGenres() {
        return genres;
    }

    public void genresClear(){ this.genres.clear(); }

    public void setGenres(List<String> genres) {
        for(String id : genres){
            boolean isContains = getGenres().stream().anyMatch(genre -> Objects.equals(genre, id));
            if(!isContains) getGenres().add(id);
        }
    }
}
