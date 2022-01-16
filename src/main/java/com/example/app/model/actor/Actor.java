package com.example.app.model.actor;

import com.example.app.model.IEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/** Actor entity
 * @author Vasiliy, Sergey
 * @version 1.0
 * */
public class Actor implements IEntity {

    private int id;

    private String name;

    private String year;

    @JsonProperty("films")
    private List<String> films;

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public Actor(){
        this.name = "";
        this.year = "";
        this.films = new LinkedList<String>();
    }

    public Actor(String name) {
        this.name = name;
        this.year = "";
        this.films = new LinkedList<String>();
    }

    public Actor(String name, String year) {
        this.name = name;
        this.year = year;
        this.films = new LinkedList<String>();
    }

    public Actor(int id, String name, String year) {
        this.id = id;
        this.name = name;
        this.year = year;
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
