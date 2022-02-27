package com.app.model.film;

import com.app.model.IEntity;
import com.app.model.actor.Actor;
import com.app.model.director.Director;
import com.app.model.genre.Genre;
import com.fasterxml.jackson.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;
import java.util.stream.Collectors;

/** Film entity
 * @author Vasiliy, Sergey
 * @version 1.0
 * */

@Entity
@Table(name = "film")
@NamedQueries({
        @NamedQuery(name = "Film.findAllWithAll",
        query = "SELECT distinct f from Film f "
                + "left join fetch f.actors a "
                + "left join fetch f.directors d "
                + "left join fetch f.genres g"
        ),
        @NamedQuery(name = "Film.findById",
        query = "SELECT distinct f FROM Film f "
                + "left join fetch f.actors a "
                + "left join fetch f.directors d "
                + "left join fetch f.genres g "
                + "where f.id = :id"
        ),
        @NamedQuery(name = "Film.findAllWithAllByIds",
                query = "SELECT distinct f from Film f "
                        + "left join fetch f.actors a "
                        + "left join fetch f.directors d "
                        + "left join fetch f.genres g "
                        + "where f.id in :ids"
        )
})
public class Film implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private int id;

    @NotNull(message = "Tittle cannot be empty")
    @Column(name = "tittle")
    private String tittle;

    @NotNull(message = "Date cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date")
    private Date date;

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public Set<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<Director> directors) {
        this.directors = directors;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    @JsonIgnoreProperties(value = "films", allowSetters = true)
    @ManyToMany
    @JoinTable(name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Actor> actors;

    @JsonIgnoreProperties(value = "films", allowSetters = true)
    @ManyToMany
    @JoinTable(name = "film_director",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id"))
    private Set<Director> directors;

    @JsonIgnoreProperties(value = "films", allowSetters = true)
    @ManyToMany
    @JoinTable(name = "film_genre",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres;

    public Film(){

    }

    public Film(String tittle, Date date){
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

    @Override
    public boolean equals(Object object){
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Film film = (Film) object;
        if(getId() != film.getId()) return false;
        if(getDate().compareTo(film.getDate()) != 0) return false;
        if(!Objects.equals(getTittle(), film.getTittle())) return false;
        List<Integer> genreIds = genres.stream().map(Genre::getId).collect(Collectors.toList());
        List<Integer> checkGenreIds = film.getGenres().stream().map(Genre::getId).collect(Collectors.toList());
        if(!genreIds.containsAll(checkGenreIds) || !checkGenreIds.containsAll(genreIds)) return false;
        List<Integer> actorIds = actors.stream().map(Actor::getId).collect(Collectors.toList());
        List<Integer> checkActorIds = film.getActors().stream().map(Actor::getId).collect(Collectors.toList());
        if(!actorIds.containsAll(checkActorIds) || !checkActorIds.containsAll(actorIds)) return false;
        List<Integer> directorIds = directors.stream().map(Director::getId).collect(Collectors.toList());
        List<Integer> checkDirectorIds = film.getDirectors().stream().map(Director::getId).collect(Collectors.toList());
        return directorIds.containsAll(checkDirectorIds) && checkDirectorIds.containsAll(directorIds);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("ID: ").append(id).append(" ");
        sb.append("Tittle: ").append(tittle).append(" ");
        sb.append("Date: ").append(date).append(" ");
        return new String(sb);
    }
}
