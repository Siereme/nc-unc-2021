package com.app.model.genre;

import com.app.model.IEntity;
import com.app.model.actor.Actor;
import com.app.model.film.Film;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    @ManyToMany
    @JoinTable(name = "film_genre",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id"))
    private Set<Film> films;

    public Genre() {
        tittle = "";
    }

    public Genre(String newGener) {
        tittle = newGener;
    }

//    public Genre(String tittle, List<Film> filmIds) {
//        this.tittle = tittle;
//        this.films = new HashSet<>();
//    }

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
        if(getId() != genre.getId()) return false;
        if(!Objects.equals(getTittle(), genre.getTittle())) return false;
        List<Integer> filmIds = films.stream().map(Film::getId).collect(Collectors.toList());
        List<Integer> checkFilmIds = genre.getFilms().stream().map(Film::getId).collect(Collectors.toList());
        return filmIds.containsAll(checkFilmIds) && checkFilmIds.containsAll(filmIds);
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
        StringBuffer sb = new StringBuffer();
        sb.append("ID: ").append(id).append(" ");
        sb.append("Tittle: ").append(tittle).append(" ");
        return new String(sb);
    }

}
