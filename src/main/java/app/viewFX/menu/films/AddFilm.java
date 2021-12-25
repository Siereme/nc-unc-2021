package app.viewFX.menu.films;

import app.controller.imp.ActorController;
import app.controller.imp.DirectorController;
import app.controller.imp.GenreController;
import app.model.actor.Actor;
import app.model.director.Director;
import app.model.genre.Genre;
import dto.request.AddEntityRequest;
import dto.request.EditEntityRequest;
import dto.request.GetEntitiesByNamesRequest;
import dto.response.GetEntitiesByNamesResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddFilm extends Films {
    @FXML private TextField titleField;
    @FXML private TextField dateField;
    @FXML private ListView<TextField> genreList = new ListView<>();
    @FXML private ListView<TextField> actorList = new ListView<>();
    @FXML private ListView<TextField> directorList = new ListView<>();

    public AddFilm() throws IOException {
        init();
    }

    public void init() {
        for(int i = 0; i < 6; i++){
            genreList.getItems().add(new TextField());
            actorList.getItems().add(new TextField());
            directorList.getItems().add(new TextField());
        }
    }

    public void addFilm(ActionEvent event) {
        LinkedList<String> genres = genreList.getItems().stream().map(TextInputControl::getText).filter(text -> text.length() > 0).collect(Collectors.toCollection(LinkedList::new));
        LinkedList<Genre> genreList = getEntitiesByNames(genres, Genre.class);
        LinkedList<String> genreIds = genreList.stream().map(Genre::getId).collect(Collectors.toCollection(LinkedList::new));


        LinkedList<String> actors = actorList.getItems().stream().map(TextInputControl::getText).filter(text -> text.length() > 0).collect(Collectors.toCollection(LinkedList::new));
        LinkedList<Actor> actorList = getEntitiesByNames(actors, Actor.class);
        LinkedList<String> actorIds = actorList.stream().map(Actor::getId).collect(Collectors.toCollection(LinkedList::new));

        LinkedList<String> directors = directorList.getItems().stream().map(TextInputControl::getText).filter(text -> text.length() > 0).collect(Collectors.toCollection(LinkedList::new));
        LinkedList<Director> directorList = getEntitiesByNames(directors, Director.class);
        LinkedList<String> directorIds = directorList.stream().map(Director::getId).collect(Collectors.toCollection(LinkedList::new));


        EntityFilm film = new EntityFilm(
                titleField.getText(),
                new Date(),
                genreIds,
                directorIds,
                actorIds
        );

        AddEntityRequest addEntityRequest = new AddEntityRequest(film);
        try {
            communicationInterface.exchange(addEntityRequest);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        closeStage(event);
    }
}
