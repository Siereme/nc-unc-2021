package app.viewFX.menu.films;

import app.controller.imp.ActorController;
import app.controller.imp.DirectorController;
import app.controller.imp.FilmController;
import app.controller.imp.GenreController;
import app.model.actor.Actor;
import app.model.director.Director;
import app.model.film.Film;
import app.model.genre.Genre;
import dto.request.GetEntitiesByNamesRequest;
import dto.response.GetEntitiesByNamesResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class EditFilm implements Initializable{
    @FXML
    private TextField titleField;
    @FXML private TextField dateField;
    @FXML private ListView<TextField> genreList;
    @FXML private ListView<TextField> actorList;
    @FXML private ListView<TextField> directorList;
    private Film film;
    GenreController genreController = new GenreController();
    ActorController actorController = new ActorController();
    DirectorController directorController = new DirectorController();

    public EditFilm(Film film) {
        this.film = film;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleField.setText(film.getTittle());
        dateField.setText(film.getDate().toString());

        for(int i = 0; i < film.getGenres().size(); i++){
            genreList.getItems().add(new TextField(genreController.getEntityById(film.getGenres().get(i)).getTittle()));
        }
        while (genreList.getItems().size() < 5){
            genreList.getItems().add(new TextField());
        }
        for(int i = 0; i < film.getActors().size(); i++){
            actorList.getItems().add(new TextField(actorController.getEntityById(film.getActors().get(i)).getName()));
        }
        while (actorList.getItems().size() < 5){
            actorList.getItems().add(new TextField());
        }
        for(int i = 0; i < film.getDirectors().size(); i++){
            directorList.getItems().add(new TextField(directorController.getEntityById(film.getDirectors().get(i)).getName()));
        }
        while (directorList.getItems().size() < 5){
            directorList.getItems().add(new TextField());
        }
    }

    public void editFilm(ActionEvent event) {
        LinkedList<String> genres = new LinkedList<>(genreList.getItems().stream().filter(x -> x.getText().length() > 0).map(x -> x.getText()).toList());
        GenreController genreController = new GenreController();
        GetEntitiesByNamesRequest createRequestGenre = new GetEntitiesByNamesRequest(
                genres,
                genreController
        );
        List<Genre> getResponseGenre = (List<Genre>) new GetEntitiesByNamesResponse("response", createRequestGenre).getEntities();
        LinkedList<String> genreIds = new LinkedList<>();
        genreIds.addAll(getResponseGenre.stream().map(x -> x.getId()).toList());

        LinkedList<String> actors = new LinkedList<>(actorList.getItems().stream().filter(x -> x.getText().length() > 0).map(x -> x.getText()).toList());
        ActorController actorController = new ActorController();
        GetEntitiesByNamesRequest createRequestActor = new GetEntitiesByNamesRequest(
                actors,
                actorController
        );
        List<Actor> getResponseActor = (List<Actor>) new GetEntitiesByNamesResponse("response", createRequestActor).getEntities();
        LinkedList<String> actorIds = new LinkedList<>();
        actorIds.addAll(getResponseActor.stream().map(x -> x.getId()).toList());

        LinkedList<String> directors = new LinkedList<>(directorList.getItems().stream().filter(x -> x.getText().length() > 0).map(x -> x.getText()).toList());
        DirectorController directorController = new DirectorController();
        GetEntitiesByNamesRequest createRequestDirector = new GetEntitiesByNamesRequest(
                directors,
                directorController
        );
        List<Director> getResponseDirector = (List<Director>) new GetEntitiesByNamesResponse("request", createRequestDirector).getEntities();
        LinkedList<String> directorIds = new LinkedList<>();
        directorIds.addAll(getResponseDirector.stream().map(x -> x.getId()).toList());

        FilmController filmController = new FilmController();
        EditFilmRequest editFilmRequest = new EditFilmRequest(
                this.film.getId(),
                titleField.getText(),
                this.film.getDate(),
                genreIds,
                directorIds,
                actorIds
        );
        GetFilmEditResponse filmEditResponse = new GetFilmEditResponse("response", editFilmRequest);


        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
