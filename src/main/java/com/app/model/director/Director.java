package com.app.model.director;

import com.app.model.IEntity;

import com.app.model.film.Film;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Document
public class Director implements IEntity {

    @Id
    private int id;
    @NotBlank(message = "Name cannot be empty")
    private String year;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    private Set<Integer> filmsIds;

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getYear() {
        return this.year;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Integer> getFilmsIds() {
        return filmsIds;
    }

    public void setFilmsIds(Set<Integer> filmsIds) {
        this.filmsIds = filmsIds;
    }

    public Director(){
        name = "";
        year = "";
        filmsIds = new HashSet<>();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hashCode(id) + Objects.hashCode(name) + Objects.hashCode(year);
        return result;
    }

    // TODO
    @Override
    public boolean equals(Object object){
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Director director = (Director) object;
        if(getId() != director.getId()) return false;
        if(!Objects.equals(getName(), director.getName())) return false;
        if(!Objects.equals(getYear(), director.getYear())) return false;
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append(" ");
        sb.append("Year: ").append(year).append(" ");
        return new String(sb);
    }
}
