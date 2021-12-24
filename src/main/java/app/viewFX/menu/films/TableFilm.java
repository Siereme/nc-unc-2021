package app.viewFX.menu.films;

import app.controller.ActorController;
import app.controller.DirectorController;
import app.controller.GenreController;
import app.model.actor.Actor;
import app.model.director.Director;
import app.model.film.Film;
import app.model.genre.Genre;
import dto.request.CreateGetEntityRequest;
import dto.response.GetEntityResponse;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;

import java.util.Date;

public class TableFilm extends Films{
    private CheckBox checked = new CheckBox();
    private String title;
    private Date date;
    private ListView<String> genres = new ListView<>();
    private ListView<String> actors = new ListView<>();
    private ListView<String> directors = new ListView<>();

    public TableFilm(Film film) {
        this.title = film.getTittle();
        this.date = film.getDate();
//        for (String id : film.getGenres()){
//            CreateGetEntityRequest createGetEntityRequest = new CreateGetEntityRequest(id);
//            GetEntityResponse getEntityResponse = new GetEntityResponse("response", createGetEntityRequest);
//            Genre genre = (Genre) getEntityResponse.getEntity();
//            this.genres.getItems().add("Title: " + genre.getTittle());
//        }
//        for (String id : film.getActors()){
//            CreateGetEntityRequest createGetEntityRequest = new CreateGetEntityRequest(id);
//            GetEntityResponse getEntityResponse = new GetEntityResponse("response", createGetEntityRequest);
//            Actor actor = (Actor) getEntityResponse.getEntity();
//            this.genres.getItems().add("Name: " + actor.getName() + "\n" + "Year: " + actor.getYear());
//        }
//        for (String id : film.getActors()){
//            CreateGetEntityRequest createGetEntityRequest = new CreateGetEntityRequest(id);
//            GetEntityResponse getEntityResponse = new GetEntityResponse("response", createGetEntityRequest);
//            Director director = (Director) getEntityResponse.getEntity();
//            this.genres.getItems().add("Name: " + director.getName() + "\n" + "Year: " + director.getYear());
//        }
        GenreController genreController = new GenreController();
        this.genres.getItems().addAll(film.getGenres().stream().map(x ->
                "Title: " + genreController.getEntityById(x).getTittle()
        ).toList());
        ActorController actorController = new ActorController();
        this.actors.getItems().addAll(film.getActors().stream().map(x ->
                "Name: " + actorController.getEntityById(x).getName() + "\n" + "Year: " + actorController.getEntityById(x).getYear()
        ).toList());
        DirectorController directorController = new DirectorController();
        this.directors.getItems().addAll(film.getDirectors().stream().map(x ->
                "Name: " + directorController.getEntityById(x).getName() + "\n" + "Year: " + directorController.getEntityById(x).getYear()
        ).toList());
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
