package com.app.model.actor;

import com.app.config.converter.imp.IdToFilmConverter;
import com.app.model.IEntity;
import com.app.model.film.Film;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;
import java.util.Set;

/** Actor entity
 * @author Vasiliy, Sergey
 * @version 1.0
 * */
@Entity
@Table(name = "actor")
@NamedQueries({
        @NamedQuery(name="Actor.findAllWithFilm",
                query = "select distinct a from Actor a left join fetch a.films"),
        @NamedQuery(name="Actor.findAllWithFilmByIds",
                        query = "select distinct a from Actor a left join fetch a.films where a.id in :ids"),
        @NamedQuery(name = "Actor.findById",
                query = "SELECT distinct a FROM Actor a "
                        + "left join fetch a.films f "
                        + "where a.id = :id"
        )
    }
)
public class Actor implements IEntity {

    public void setId(int id) {
        this.id = id;
    }

    public void setFilms(Set<Film> films) {
        this.films = films;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    private String year;

    @NotEmpty(message = "Film list cannot be empty")
    @Convert(converter = IdToFilmConverter.class)
    @JsonIgnoreProperties(value = "actors", allowSetters = true)
    @ManyToMany
    @JoinTable(name = "film_actor",
    joinColumns = @JoinColumn(name = "actor_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id"))
    private Set<Film> films;


    public void setName(String name) {
        this.name = name;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Actor() {
        this.name = "";
        this.year = "";
    }

    public Actor(String name) {
        this.name = name;
        this.year = "";
    }

//    public Actor(int actor_id, String name) {
//        this.id = actor_id;
//        this.name = name;
//    }

    public Actor(String name, String year) {
        this.name = name;
        this.year = year;
    }

//    public Actor(int actor_id, String name, String year) {
//        this.id = actor_id;
//        this.name = name;
//        this.year = year;
//    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getYear() {
        return this.year;
    }

    public Set<Film> getFilms(){
        return films;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hashCode(id);
        return result;
    }

    @Override
    public boolean equals(Object object){
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Actor actor = (Actor) object;
        return getId() == actor.getId();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("ID: ").append(id).append(" ");
        sb.append("Name: ").append(name).append(" ");
        return new String(sb);
    }

}
