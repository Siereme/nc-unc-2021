package com.app.model.genre;

import com.app.model.IEntity;
import com.app.model.film.Film;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Document
public class Genre implements IEntity {

    @Id
    private int id;

    public Set<Integer> getFilmsIds() {
        return filmsIds;
    }

    public void setFilmsIds(Set<Integer> filmsIds) {
        this.filmsIds = filmsIds;
    }

    @NotBlank(message = "Title cannot be empty")
    private String tittle;

    private Set<Integer> filmsIds;

    public Genre() {
        tittle = "";
    }

    public Genre(String newGener) {
        tittle = newGener;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    // TODO
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Genre genre = (Genre) object;
        if(getId() != genre.getId()) return false;
        if(!Objects.equals(getTittle(), genre.getTittle())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hashCode(id) + Objects.hashCode(tittle);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tittle: ").append(tittle).append(" ");
        return new String(sb);
    }

}
