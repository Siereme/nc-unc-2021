package model.Film;

import repository.ActorRepository;
import repository.DirectorRepository;
import repository.GenreRepository;

import java.util.Date;
import java.util.UUID;

public class Film {

    private String id;
    private String tittle;
    private Date date;
    private GenreRepository genres;
    private DirectorRepository directors;
    private ActorRepository actors;

    public ActorRepository getActors() {
        return actors;
    }

    public void setActors(ActorRepository actors) {
        this.actors = actors;
    }

    public DirectorRepository getDirectors() {
        return directors;
    }

    public void setDirectors(DirectorRepository directors) {
        this.directors = directors;
    }

    public GenreRepository getGenres() {
        return genres;
    }

    public void setGenres(GenreRepository genres) {
        this.genres = genres;
    }

    public
    Film(String newTittle, Date newDate, GenreRepository newGenres, DirectorRepository directorRepository,
                ActorRepository actorRepository) {
        id = UUID.randomUUID().toString();
        tittle = newTittle;
        date = newDate;
        genres = newGenres;
        directors = directorRepository;
        actors = actorRepository;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ").append(id).append("\n").append("Tittle: ").append(tittle).append("\n")
                .append("Date of release: ").append(date).append("\n").append("Genres\n").append(genres.toString())
                .append("Directors\n").append(directors.toString()).append("\n").append("Actors:\n")
                .append(actors.toString());
        return new String(sb);
    }

}
