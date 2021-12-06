package model.Actor;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.LinkedList;
import java.util.UUID;


public class Actor {

    private String id;
    private String name;
    private String year;
    private LinkedList<String> filmsId;

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setFilms(LinkedList<String> filmsId) {
        this.filmsId = filmsId;
    }

    public Actor(){
        this.id = UUID.randomUUID().toString();
        this.name = "Unknown";
        this.year = "Unknown";
        this.filmsId = new LinkedList<String>();
    }

    public Actor(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.year = "Unknown";
        this.filmsId = new LinkedList<String>();
    }

    public Actor(String name, String year) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.year = year;
        this.filmsId = new LinkedList<String>();
    }

    public Actor(String name, LinkedList<String> films) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.year = "Unknown";
        this.filmsId = films;
    }

    public Actor(String name, String year, LinkedList<String> films) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.year = year;
        this.filmsId = films;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getYear() {
        return this.year;
    }

    @JsonIgnore
    public int getCountFilms() {
        return this.filmsId.size();
    }

    public LinkedList<String> getFilms() {
        return this.filmsId;
    }


}
