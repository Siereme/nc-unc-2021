package com.app.model.film;

import com.app.model.IEntity;
import com.app.model.actor.Actor;
import com.app.model.director.Director;
import com.app.model.genre.Genre;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

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
        )
})
public class Film implements IEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private int id;

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

//    @JsonManagedReference
    @NotEmpty(message = "Actor list cannot be empty")
    @ManyToMany
    @JoinTable(name = "film_actor",
    joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    public Set<Actor> actors;

//    @JsonManagedReference
    @NotEmpty(message = "Director list cannot be empty")
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "film_director",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id"))
    public Set<Director> directors;

//    @JsonManagedReference
    @NotEmpty(message = "Genre list cannot be empty")
    @ManyToMany
    @JoinTable(name = "film_genre",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    public Set<Genre> genres;

    public Film(){

    }

    public Film(int film_id, String tittle, Date date){
        this.id = film_id;
        this.tittle = tittle;
        this.date = date;
    }


    @Override
    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("ID: ").append(id).append(" ");
        sb.append("Tittle: ").append(tittle).append(" ");
        return new String(sb);
    }
}
