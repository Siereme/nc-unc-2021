package model.Actor;

import model.Film.Film;

import java.util.LinkedList;
import java.util.UUID;

import static controller.FilmController.tittlesToString;

public class Actor {

    private String id;

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public void setYear(String year) {
        this.year = year;
    }

    private String year;

    public void setFilms(LinkedList<Film> films) {
        this.films = films;
    }

    private LinkedList<Film> films;

    public Actor(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.year = "Unknown";
        this.films = new LinkedList<Film>();
    }

    public Actor(String name, String year) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.year = year;
        this.films = new LinkedList<Film>();
    }

    public Actor(String name, LinkedList<Film> films) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.year = "Unknown";
        this.films = films;
    }

    public Actor(String name, String year, LinkedList<Film> films) {
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

    public int getCountFilms() {
        return this.films.size();
    }

    public LinkedList<Film> getFilms() {
        return this.films;
    }

    public Film getFilm(int index) {
        return this.films.get(index);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ").append(getId()).append("\n");
        sb.append("Name: ").append(getName()).append("\n");
        if (!films.isEmpty()) {
            sb.append(tittlesToString(films)).append("\n");
        } else {
            sb.append("Films is empty\n");
        }
        return new String(sb);
    }
}
