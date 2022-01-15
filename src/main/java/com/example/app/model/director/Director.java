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

    private final String id;

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
        this.id = UUID.randomUUID().toString();
        this.name = "Unknown";
        this.year = "Unknown";
        this.films = new LinkedList<>();
    }

    public Director(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.year = "Unknown";
        this.films = new LinkedList<>();
    }


    public Director(String name, String year) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.year = year;
        this.films = new LinkedList<>();
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

}
