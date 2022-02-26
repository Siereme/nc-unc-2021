package com.app.model.director;

import com.app.model.IEntity;

import com.app.model.film.Film;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/** Director entity
 * @author Vasiliy, Sergey
 * @version 1.0
 * */

@Entity
@Table(name = "director")
@NamedQueries({
        @NamedQuery(name="Director.findAllWithFilm",
                query = "select distinct d from Director d left join fetch d.films"),
        @NamedQuery(name="Director.findAllWithFilmByIds",
                query = "select distinct d from Director d left join fetch d.films where d.id in :ids"),
        @NamedQuery(name = "Director.findById",
                query = "SELECT distinct d FROM Director d "
                        + "left join fetch d.films f "
                        + "where d.id = :id"
        )
})

public class Director implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "director_id")
    private int id;

    public void setName(String name) {
        this.name = name;
    }

    @NotBlank(message = "Name cannot be empty")
    @Column(name = "name")
    private String name;

    public void setYear(String year) {
        this.year = year;
    }

    @NotBlank(message = "Year cannot be empty")
    @Column(name = "year")
    private String year;

    @JsonIgnoreProperties(value = "directors", allowSetters = true)
    @ManyToMany
    @JoinTable(name = "film_director",
    joinColumns = @JoinColumn(name = "director_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id"))
    private Set<Film> films;

    public Director() {
        this.name = "";
        this.year = "";
        this.films = new HashSet<>();
    }

    public Director(String name) {
        this.name = name;
        this.year = "";
        this.films = new HashSet<>();
    }

//    public Director(int id, String name) {
//        this.id = id;
//        this.name = name;
//        this.year = "";
//        this.films = new HashSet<>();
//    }

    public Director(String name, String year) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.films = new HashSet<>();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFilms(Set<Film> films) {
        this.films = films;
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

    public Set<Film> getFilms() {
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
        Director director = (Director) object;
        if(getId() != director.getId()) return false;
        if(!Objects.equals(getName(), director.getName())) return false;
        return Objects.equals(getYear(), director.getYear());
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("ID: ").append(id).append(" ");
        sb.append("Name: ").append(name).append(" ");
        return new String(sb);
    }
}
