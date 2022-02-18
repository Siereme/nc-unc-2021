package com.app.model.genre;

import com.app.model.IEntity;
import com.app.model.film.Film;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/** Genre entity
 * @author Vasiliy, Sergey
 * */

@Entity
@Table(name = "genre")
@NamedQueries({
        @NamedQuery(name="Genre.findAllWithFilm",
        query = "select distinct g from Genre g left join fetch g.films")
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

//    @NotBlank(message = "Title cannot be empty")
    @Column(name = "tittle")
    private String tittle;

    @JsonBackReference
    @ManyToMany
    @JoinTable(name = "film_genre", joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id"))
    public Set<Film> films;

    public Genre() {
        tittle = "";
    }

    public Genre(int id, String tittle) {
        this.id = id;
        this.tittle = tittle;
    }

    public Genre(int id, String tittle, List<Film> films) {
        this.id = id;
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

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        Genre genre = (Genre) o;
//        return Objects.equals(tittle, genre.tittle);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(tittle);
//    }
//
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("ID: ").append(id).append(" ");
        sb.append("Tittle: ").append(tittle).append(" ");
        return new String(sb);
    }

}
