package app.viewFX.menu.films.handle;

import app.model.IEntity;
import app.model.actor.Actor;
import app.model.director.Director;
import app.model.film.Film;
import app.model.genre.Genre;
import app.viewFX.menu.films.RequestFilm;
import dto.request.AddEntityRequest;
import dto.request.EditEntityRequest;
import dto.request.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class EditFilm extends HandleFilm implements Initializable {
    private String id;
    @FXML
    private TextField titleField;
    @FXML private TextField dateField;
    @FXML private ListView<TextField> genres;
    @FXML private ListView<TextField> actors;
    @FXML private ListView<TextField> directors;
    @FXML private Button handleButton;
    private Class<? extends Request> request = EditEntityRequest.class;
    private Film film;
    public EditFilm(Film film) throws IOException {
        this.film = film;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.id = film.getId();
        this.titleField.setText(film.getTittle());
        this.dateField.setText(film.getDate().toString());

        List<Genre> genreList = getEntitiesByIds(film.getGenres(), Genre.class);
        List<TextField> genreFields = new ArrayList<>();
        for (Genre genre : genreList) {
            String tittle = genre.getTittle();
            genreFields.add(new TextField(tittle));
        }
        while (genreFields.size() < 6){
            genreFields.add(new TextField());
        }
        List<Actor> actorList = getEntitiesByIds(film.getActors(), Actor.class);
        List<TextField> actorFields = new ArrayList<>();
        for (Actor actor : actorList) {
            String tittle = actor.getName();
            actorFields.add(new TextField(tittle));
        }
        while (actorFields.size() < 6){
            actorFields.add(new TextField());
        }
        List<Director> directorList = getEntitiesByIds(film.getDirectors(), Director.class);
        List<TextField> directorFields = new ArrayList<>();
        for (Director director : directorList) {
            String tittle = director.getName();
            directorFields.add(new TextField(tittle));
        }
        while (directorFields.size() < 6){
            directorFields.add(new TextField());
        }

        ObservableList<TextField> observableList = FXCollections.observableList(genreFields);
        genres.setItems(observableList);
        observableList = FXCollections.observableList(actorFields);
        actors.setItems(observableList);
        observableList = FXCollections.observableList(directorFields);
        directors.setItems(observableList);

        handleButton.setText("Edit");
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
                id,
                titleField.getText(),
                new Date(),
                genreIds,
                directorIds,
                actorIds
        );

        try {
            EditEntityRequest editRequest = new EditEntityRequest(film, Film.class);
            communicationInterface.exchange(editRequest);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        closeStage(event);
    }
}
