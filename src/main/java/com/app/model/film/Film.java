package com.app.model.film;

import com.app.model.IEntity;
import com.app.model.actor.Actor;
import com.app.model.director.Director;
import com.app.model.genre.Genre;
import com.fasterxml.jackson.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

@Document
public class Film implements IEntity {

    @Transient
    public static final String SEQUENCE_NAME = "films_sequence";

    @Id
    private int id;
    @NotBlank(message = "Name cannot be empty")
    private String tittle;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @JsonIgnoreProperties(value = "films", allowSetters = true)
    private Set<Integer> actors = new HashSet<>();
    @JsonIgnoreProperties(value = "films", allowSetters = true)
    private Set<Integer> directors = new HashSet<>();
    @JsonIgnoreProperties(value = "films", allowSetters = true)
    private Set<Integer> genres = new HashSet<>();

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Film(){

    }

    public Film(String tittle, LocalDate date){
        this.tittle = tittle;
        this.date = date;
    }


    @Override
    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hashCode(id) + Objects.hashCode(tittle) + Objects.hashCode(date);
        return result;
    }

    public Set<Integer> getActors() {
        return actors;
    }

    public void setActors(Set<Integer> actors) {
        this.actors = actors;
    }

    public Set<Integer> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<Integer> directors) {
        this.directors = directors;
    }

    public Set<Integer> getGenres() {
        return genres;
    }

    public void setGenres(Set<Integer> genres) {
        this.genres = genres;
    }

    // TODO
    @Override
    public boolean equals(Object object){
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Film film = (Film) object;
        if(getId() != film.getId()) return false;
        if(getDate().compareTo(film.getDate()) != 0) return false;
        if(!Objects.equals(getTittle(), film.getTittle())) return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tittle: ").append(tittle).append(" ");
        sb.append("Date: ").append(date).append(" ");
        return new String(sb);
    }
}
