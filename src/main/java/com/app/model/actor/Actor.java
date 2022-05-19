package com.app.model.actor;

import com.app.model.IEntity;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Document
public class Actor implements IEntity {

    @Transient
    public static final String SEQUENCE_NAME = "actors_sequence";

    @Id
    private int id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Name cannot be empty")
    private String year;
    private Set<Integer> filmsIds;

    public Set<Integer> getFilmsIds() {
        return filmsIds;
    }

    public void setFilmsIds(Set<Integer> filmsIds) {
        this.filmsIds = filmsIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Actor(){
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
        Actor actor = (Actor) object;
        if(getId() != actor.getId()) return false;
        if(!Objects.equals(getName(), actor.getName())) return false;
        if(!Objects.equals(getYear(), actor.getYear())) return false;
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
