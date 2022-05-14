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
import java.util.stream.Collectors;

@Document
public class Film implements IEntity {

    @Id
    private int id;
    @NotBlank(message = "Name cannot be empty")
    private String tittle;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @JsonIgnoreProperties(value = "films", allowSetters = true)
    private Set<Integer> actorsIds;
    @JsonIgnoreProperties(value = "films", allowSetters = true)
    private Set<Integer> directorsIds;
    @JsonIgnoreProperties(value = "films", allowSetters = true)
    private Set<Integer> genresIds;

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

    public Set<Integer> getActorsIds() {
        return actorsIds;
    }

    public void setActorsIds(Set<Integer> actorsIds) {
        this.actorsIds = actorsIds;
    }

    public Set<Integer> getDirectorsIds() {
        return directorsIds;
    }

    public void setDirectorsIds(Set<Integer> directorsIds) {
        this.directorsIds = directorsIds;
    }

    public Set<Integer> getGenresIds() {
        return genresIds;
    }

    public void setGenresIds(Set<Integer> genresIds) {
        this.genresIds = genresIds;
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
