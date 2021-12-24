package app.viewFX.menu.films;

import app.controller.ActorController;
import app.controller.DirectorController;
import app.controller.GenreController;
import app.model.actor.Actor;
import app.model.director.Director;
import app.model.genre.Genre;
import dto.request.AddFilmRequest;
import dto.request.GetEntitiesByNamesRequest;
import dto.response.GetAddFilmResponse;
import dto.response.GetEntitiesByNamesResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class AddFilm implements Initializable {
    @FXML private TextField titleField;
    @FXML private TextField dateField;
    @FXML private ListView<TextField> genreList;
    @FXML private ListView<TextField> actorList;
    @FXML private ListView<TextField> directorList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i = 0; i < 6; i++){
            genreList.getItems().add(new TextField());
            actorList.getItems().add(new TextField());
            directorList.getItems().add(new TextField());
        }
    }

    public void addFilm(ActionEvent event) {
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

        AddFilmRequest addFilmRequest = new AddFilmRequest(
                titleField.getText(),
                new Date(),
                genreIds,
                directorIds,
                actorIds
        );
        GetAddFilmResponse addFilmResponse = new GetAddFilmResponse("response", addFilmRequest);

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
