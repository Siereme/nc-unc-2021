package com.gmail.viktor.yuryev.mvc.console.model;

import java.io.File;
import java.util.List;

public class Film extends Entity {
    private String filmName;
    private List<Director> directors;

    public Film(String filmName, List<Director> directors) {
        this.filmName = filmName;
        this.directors = directors;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    public Integer getId() {
        return null;
    }
}
