package model.Director;

import model.Film.Film;

import java.util.LinkedList;
import java.util.UUID;

import static controller.FilmController.tittlesToString;

public class Director {

    private String id;

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private String year;

    public void setFilms(LinkedList<Film> films) {
        this.films = films;
    }

    private LinkedList<Film> films;

    public Director(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.year = "Unknown";
        this.films = new LinkedList<Film>();
    }

    public Director(String name, String year) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.year = year;
        this.films = new LinkedList<Film>();
    }

    public Director(String name, LinkedList<Film> films) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.year = "Unknown";
        this.films = films;
    }

    public Director(String name, String year, LinkedList<Film> films) {
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

    public void setFilm(int index, Film film) {
        films.set(index, film);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ").append(id).append("\n");
        sb.append("Name: ").append(name).append("\n");
        if (!films.isEmpty()) {
            sb.append(tittlesToString(films)).append("\n");
        } else {
            sb.append("Films is empty\n");
        }
        return new String(sb);
    }
}
