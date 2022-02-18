package com.app.model.actor;

import com.app.model.IEntity;
import com.app.model.film.Film;

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
import java.util.Set;

/** Actor entity
 * @author Vasiliy, Sergey
 * @version 1.0
 * */
@Entity
@Table(name = "actor")
@NamedQueries({
        @NamedQuery(name="Actor.findAllWithFilm",
                query = "select distinct a from Actor a left join fetch a.films")
})
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

    public Actor(int actor_id, String name) {
        this.id = actor_id;
        this.name = name;
    }

    public Actor(String name, String year) {
        this.name = name;
        this.year = year;
    }

    public Actor(int actor_id, String name, String year) {
        this.id = actor_id;
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

    public Set<Film> getFilms(){
        return films;
    }

}
