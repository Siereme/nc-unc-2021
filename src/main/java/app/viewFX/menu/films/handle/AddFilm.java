package app.viewFX.menu.films.handle;

import app.model.actor.Actor;
import app.model.director.Director;
import app.model.film.Film;
import app.model.genre.Genre;
import app.viewFX.menu.films.RequestFilm;
import dto.request.imp.AddEntityRequest;
import dto.request.Request;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.ResourceBundle;
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
        LinkedList<String> genreNames = genres.getItems().stream().map(TextField::getText).filter(text -> text.length() > 0).collect(Collectors.toCollection(LinkedList::new));
        LinkedList<String> genreIds = getEntitiesIdsByNames(genreNames, Genre.class);

        LinkedList<String> actorNames = actors.getItems().stream().map(TextField::getText).filter(text -> text.length() > 0).collect(Collectors.toCollection(LinkedList::new));
        LinkedList<String> actorIds = getEntitiesIdsByNames(actorNames, Actor.class);

        LinkedList<String> directorNames = directors.getItems().stream().map(TextField::getText).filter(text -> text.length() > 0).collect(Collectors.toCollection(LinkedList::new));
        LinkedList<String> directorIds = getEntitiesIdsByNames(directorNames, Director.class);

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
