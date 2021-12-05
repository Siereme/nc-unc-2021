package model.Director;

import java.util.LinkedList;
import java.util.UUID;

public class Director {

    private String id;

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private String year;
    private LinkedList<String> films;

    public void setFilms(LinkedList<String> films) {
        this.films = films;
    }

    public Director(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.year = "Unknown";
        this.films = new LinkedList<String>();
    }

    public Director(String name, String year) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.year = year;
        this.films = new LinkedList<String>();
    }

    public Director(String name, LinkedList<String> films) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.year = "Unknown";
        this.films = films;
    }

    public Director(String name, String year, LinkedList<String> films) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.year = year;
        this.films = films;
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

    public LinkedList<String> getFilms() {
        return this.films;
    }

}
