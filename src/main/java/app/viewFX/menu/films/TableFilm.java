package app.viewFX.menu.films;

import app.controller.imp.ActorController;
import app.controller.imp.DirectorController;
import app.controller.imp.GenreController;
import app.model.actor.Actor;
import app.model.director.Director;
import app.model.film.Film;
import app.model.genre.Genre;
import dto.request.GetEntityRequest;
import dto.response.GetEntityResponse;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TableFilm{
    private String id;
    private CheckBox checked = new CheckBox();
    private String title;
    private Date date;
    private ListView<String> genres = new ListView<>();
    private ListView<String> actors = new ListView<>();
    private ListView<String> directors = new ListView<>();

    public TableFilm(Film film, List<Genre> genreList, List<Actor> actorList, List<Director> directorList) throws IOException {
        init(film, genreList, actorList, directorList);
    }

    private void init(Film film, List<Genre> genreList, List<Actor> actorList, List<Director> directorList) {
        this.id = film.getId();
        this.title = film.getTittle();
        this.date = film.getDate();
        genreList.forEach(genre -> this.genres.getItems().add("Title: " + genre.getTittle()));
        actorList.forEach(actor -> this.actors.getItems().add("Name: " + actor.getName() + "\n" + "Year: " + actor.getYear()));
        directorList.forEach(director -> this.directors.getItems().add("Name: " + director.getName() + "\n" + "Year: " + director.getYear()));
    }


    public String getId() {
        return this.id;
    }

    public CheckBox getChecked() {
        return checked;
    }

    public void setChecked(CheckBox checked) {
        this.checked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ListView<String> getGenres() {
        return genres;
    }

    public void setGenres(ListView<String> genres) {
        this.genres = genres;
    }

    public ListView<String> getActors() {
        return actors;
    }

    public void setActors(ListView<String> actors) {
        this.actors = actors;
    }

    public ListView<String> getDirectors() {
        return directors;
    }

    public void setDirectors(ListView<String> directors) {
        this.directors = directors;
    }
}
