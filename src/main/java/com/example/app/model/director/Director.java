package com.example.app.model.director;

import com.example.app.model.IEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/** Director entity
 * @author Vasiliy, Sergey
 * @version 1.0
 * */
public class Director implements IEntity {

    private int id;

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public void setYear(String year) {
        this.year = year;
    }

    private String year;

    @JsonProperty("films")
    private List<String> films;


    public Director(){
        this.name = "";
        this.year = "";
        this.films = new LinkedList<>();
    }

    public Director(String name) {
        this.name = name;
        this.year = "";
        this.films = new LinkedList<>();
    }


    public Director(int id, String name, String year) {
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
