package com.example.app.model.actor;

import com.example.app.model.IEntity;
import com.example.app.model.IParticipatesFilm;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/** Actor entity
 * @author Vasiliy, Sergey
 * @version 1.0
 * */
public class Actor implements IEntity, IParticipatesFilm {

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Year cannot be empty")
    private String year;

    public List<Integer> getFilms() {
        return films;
    }

    public void setFilms(List<Integer> films) {
        this.films = films;
    }

    @NotEmpty(message = "Film list cannot be empty")
    private List<Integer> films;

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public Actor(){
        this.name = "";
        this.year = "";
        this.films = new LinkedList<>();
    }

    public Actor(String name) {
        this.name = name;
        this.year = "";
        this.films = new LinkedList<>();
    }

    public Actor(int id, String name) {
        this.id = id;
        this.name = name;
        this.films = new LinkedList<>();
    }

    public Actor(String name, String year) {
        this.name = name;
        this.year = year;
        this.films = new LinkedList<>();
    }

    public Actor(int id, String name, String year) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.films = new LinkedList<>();
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getYear() {
        return this.year;
    }


}
