package model.Actor;


import model.Film.Film;

import java.util.Formatter;
import java.util.LinkedList;
import java.util.UUID;

public class Actor {

    private String id;
    private String name;
    private String year;
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

    public String getId(){return this.id;}

    public String getName(){return this.name;}

    public String getYear(){return this.year;}

    public int getCountFilms(){return this.films.size();}

    public LinkedList<Film> getFilms(){return this.films;}

    public Film getFilm(int index){return this.films.get(index);}

    public void addFilm(Film film){this.films.add(film);}

    public void setFilm(int index, Film film){
        this.films.add(index, film);
        this.films.remove(index + 1);
    }

    public void deleteFilm(int index){this.films.remove(index);}

    @Override
    public String toString(){
        Formatter info = new Formatter();
        info.format("Name: %s \n Year: %s", getName(), getYear());
        return info.toString();
    }
}
