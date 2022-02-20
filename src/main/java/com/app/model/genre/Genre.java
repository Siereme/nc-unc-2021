package com.app.model.genre;

import com.app.model.IEntity;
import com.app.model.actor.Actor;
import com.app.model.film.Film;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/** Genre entity
 * @author Vasiliy, Sergey
 * */

@Entity
@Table(name = "genre")
@NamedQueries({
        @NamedQuery(name="Genre.findAllWithFilm",
        query = "select distinct g from Genre g left join fetch g.films"),
        @NamedQuery(name="Genre.findAllWithFilmByIds",
                query = "select distinct g from Genre g left join fetch g.films where g.id in :ids"),
        @NamedQuery(name = "Genre.findById",
                query = "SELECT distinct g FROM Genre g "
                        + "left join fetch g.films f "
                        + "where g.id = :id"
        )
})

public class Genre implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private int id;

    public Set<Film> getFilms() {
        return films;
    }

    public void setFilms(Set<Film> films) {
        this.films = films;
    }

    @NotBlank(message = "Title cannot be empty")
    @Column(name = "tittle")
    private String tittle;

    @JsonIgnoreProperties(value = "genres", allowSetters = true)
    @ManyToMany
    @JoinTable(name = "film_genre", joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id"))
    private Set<Film> films;

    public Genre() {
        tittle = "";
    }

    public Genre(String newGener) {
        tittle = newGener;
    }

    public Genre(String tittle, List<Film> filmIds) {
        this.tittle = tittle;
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Genre genre = (Genre) object;
        return getId() == genre.getId();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hashCode(id);
        return result;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("ID: ").append(id).append(" ");
        sb.append("Tittle: ").append(tittle).append(" ");
        return new String(sb);
    }

}
