package app.viewFX.menu.films.handle;

import app.model.actor.Actor;
import app.model.director.Director;
import app.model.film.Film;
import app.model.genre.Genre;
import dto.request.Request;
import dto.request.imp.EditEntityRequest;
import dto.request.imp.RequestFilm;
import client.CommunicationInterface;
import dto.request.imp.AddEntityRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddEditFilmController extends HandleFilmController {
    @FXML
    private TextField titleField;
    @FXML
    private TextField dateField;
    @FXML
    private ListView<TextField> genres;
    @FXML
    private ListView<TextField> actors;
    @FXML
    private ListView<TextField> directors;
    @FXML
    private Button handleButton;

    private Film film;
    private String id;
    private boolean editMode;

    public AddEditFilmController() throws IOException {
    }


    public void init() {
        System.out.println("initialize  " + film);
        editMode = film != null;
        System.out.println("editMode  " + editMode);
        if (editMode) {
            initFilm();
        } else {
            initEmpty();
        }
    }

    private void initEmpty() {
        for (int i = 0; i < 6; i++) {
            this.genres.getItems().add(new TextField());
            this.actors.getItems().add(new TextField());
            this.directors.getItems().add(new TextField());
        }
    }

    private void initFilm() {
        this.id = film.getId();
        this.titleField.setText(film.getTittle());
        this.dateField.setText(film.getDate().toString());

        List<Genre> genreList = getEntitiesByIds(film.getGenres(), Genre.class);
        List<TextField> genreFields = new ArrayList<>();
        for (Genre genre : genreList) {
            String tittle = genre.getTittle();
            genreFields.add(new TextField(tittle));
        }
        while (genreFields.size() < 6) {
            genreFields.add(new TextField());
        }
        List<Actor> actorList = getEntitiesByIds(film.getActors(), Actor.class);
        List<TextField> actorFields = new ArrayList<>();
        for (Actor actor : actorList) {
            String tittle = actor.getName();
            actorFields.add(new TextField(tittle));
        }
        while (actorFields.size() < 6) {
            actorFields.add(new TextField());
        }
        List<Director> directorList = getEntitiesByIds(film.getDirectors(), Director.class);
        List<TextField> directorFields = new ArrayList<>();
        for (Director director : directorList) {
            String tittle = director.getName();
            directorFields.add(new TextField(tittle));
        }
        while (directorFields.size() < 6) {
            directorFields.add(new TextField());
        }

        ObservableList<TextField> observableList = FXCollections.observableList(genreFields);
        genres.setItems(observableList);
        observableList = FXCollections.observableList(actorFields);
        actors.setItems(observableList);
        observableList = FXCollections.observableList(directorFields);
        directors.setItems(observableList);
    }

    public void handleFilm(ActionEvent event) {
        RequestFilm film = gatherParameters();
        requestData(film);
        closeStage(event);
    }

    private void requestData(RequestFilm film) {
        try {
            Request addRequest = editMode ? new EditEntityRequest(film, Film.class) : new AddEntityRequest(film, Film.class);
            CommunicationInterface.getInstance().exchange(addRequest);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private RequestFilm gatherParameters() {
        List<String> genreNames = genres.getItems().stream().map(TextField::getText).filter(text -> text.length() > 0)
                .collect(Collectors.toCollection(LinkedList::new));
        List<String> genreIds = getEntitiesIdsByNames(genreNames, Genre.class);

        List<String> actorNames = actors.getItems().stream().map(TextField::getText).filter(text -> text.length() > 0)
                .collect(Collectors.toCollection(LinkedList::new));
        List<String> actorIds = getEntitiesIdsByNames(actorNames, Actor.class);

        List<String> directorNames = directors.getItems().stream().map(TextField::getText).filter(text -> text.length() > 0)
                .collect(Collectors.toCollection(LinkedList::new));
        List<String> directorIds = getEntitiesIdsByNames(directorNames, Director.class);

        return new RequestFilm(editMode ? film.getId() : "", titleField.getText(), new Date(), genreIds, directorIds, actorIds);
    }

    protected void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setFilm(Film film) {
        this.film = film;
    }
}
