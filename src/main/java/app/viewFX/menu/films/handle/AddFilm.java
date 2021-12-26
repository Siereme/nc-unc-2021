package app.viewFX.menu.films.handle;

import app.model.actor.Actor;
import app.model.director.Director;
import app.model.film.Film;
import app.model.genre.Genre;
import app.viewFX.menu.films.RequestFilm;
import dto.request.AddEntityRequest;
import dto.request.Request;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.stream.Collectors;

public class AddFilm extends HandleFilm implements Initializable {
    @FXML private TextField titleField;
    @FXML private TextField dateField;
    @FXML private ListView<TextField> genres;
    @FXML private ListView<TextField> actors;
    @FXML private ListView<TextField> directors;
    @FXML private Button handleButton;
    private final Class<? extends Request> request = AddEntityRequest.class;

    public AddFilm() throws IOException {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i = 0; i < 6; i++){
            this.genres.getItems().add(new TextField());
            this.actors.getItems().add(new TextField());
            this.directors.getItems().add(new TextField());
        }
        handleButton.setText("Add");
        handleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleFilm(event);
            }
        });
    }

    public void handleFilm(ActionEvent event){
        LinkedList<String> genreNames = genres.getItems().stream().map(TextInputControl::getText).filter(text -> text.length() > 0).collect(Collectors.toCollection(LinkedList::new));
        LinkedList<Genre> genreList = getEntitiesByNames(genreNames, Genre.class);
        LinkedList<String> genreIds = genreList.stream().map(Genre::getId).collect(Collectors.toCollection(LinkedList::new));


        LinkedList<String> actorNames = actors.getItems().stream().map(TextInputControl::getText).filter(text -> text.length() > 0).collect(Collectors.toCollection(LinkedList::new));
        LinkedList<Actor> actorList = getEntitiesByNames(actorNames, Actor.class);
        LinkedList<String> actorIds = actorList.stream().map(Actor::getId).collect(Collectors.toCollection(LinkedList::new));

        LinkedList<String> directorNames = directors.getItems().stream().map(TextInputControl::getText).filter(text -> text.length() > 0).collect(Collectors.toCollection(LinkedList::new));
        LinkedList<Director> directorList = getEntitiesByNames(directorNames, Director.class);
        LinkedList<String> directorIds = directorList.stream().map(Director::getId).collect(Collectors.toCollection(LinkedList::new));

        RequestFilm film = new RequestFilm(
                titleField.getText(),
                new Date(),
                genreIds,
                directorIds,
                actorIds
        );

        try {
            AddEntityRequest addRequest = new AddEntityRequest(film, Film.class);
            communicationInterface.exchange(addRequest);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        closeStage(event);
    }
}
