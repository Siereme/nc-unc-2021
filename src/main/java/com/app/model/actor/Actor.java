package com.app.model.actor;

import com.app.model.IEntity;
import com.app.model.film.Film;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/** Actor entity
 * @author Vasiliy, Sergey
 * @version 1.0
 * */
@SuppressWarnings("ALL")
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

    @SuppressWarnings("unused")
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

    public Actor(String name, String year) {
        this.name = name;
        this.year = year;
        this.films = new HashSet<>();
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


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hashCode(id) + Objects.hashCode(name) + Objects.hashCode(year);
        return result;
    }

    @Override
    public boolean equals(Object object){
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Actor actor = (Actor) object;
        if(getId() != actor.getId()) return false;
        if(!Objects.equals(getName(), actor.getName())) return false;
        if(!Objects.equals(getYear(), actor.getYear())) return false;
        List<Integer> filmIds = films.stream().map(Film::getId).collect(Collectors.toList());
        List<Integer> checkFilmIds = actor.getFilms().stream().map(Film::getId).collect(Collectors.toList());
        return filmIds.containsAll(checkFilmIds) && checkFilmIds.containsAll(filmIds);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append(" ");
        sb.append("Name: ").append(name).append(" ");
        sb.append("Year: ").append(year).append(" ");
        return new String(sb);
    }

}
