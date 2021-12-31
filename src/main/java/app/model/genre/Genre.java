package app.model.genre;

import app.model.IEntity;
import app.model.actor.Actor;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/** Genre entity
 * @author Vasiliy, Sergey
 * */
public class Genre implements IEntity {

    private final String id;

    private String tittle;

    @JsonProperty("films")
    private List<String> films;

    public Genre(){
        id = UUID.randomUUID().toString();
        tittle = "Unknown";
        this.films = new LinkedList<>();
    }

    public Genre(String newGener) {
        id = UUID.randomUUID().toString();
        tittle = newGener;
        this.films = new LinkedList<>();
    }

    public Genre(Genre genre) {
        this.id = UUID.randomUUID().toString();
        this.tittle = genre.getTittle();
        this.films = genre.getFilms();
    }

    public String getId() {
        return this.id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public List<String> getFilms() { return this.films; }

    public boolean setFilm(String filmId){
        for(String id : this.films){
            if(Objects.equals(id, filmId)) return false;
        }
        this.films.add(filmId);
        return true;
    }

    public void setFilms(List<String> filmId){
        for(String id : filmId){
            setFilm(id);
        }
    }

    public boolean deleteFilm(String filmId){
        for(String id : this.films){
            if(Objects.equals(id, filmId)){
                this.films.remove(id);
                return true;
            };
        }
        return false;
    }

    public void clearFilms(){ this.films.clear(); }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Genre genre = (Genre) o;
        return Objects.equals(tittle, genre.tittle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tittle);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("ID: ").append(id).append("\n");
        sb.append("Tittle: ").append(tittle).append("\n");
        return new String(sb);
    }
}
