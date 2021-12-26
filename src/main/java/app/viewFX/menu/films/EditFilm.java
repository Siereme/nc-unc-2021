package app.viewFX.menu.films;

import app.controller.imp.ActorController;
import app.controller.imp.DirectorController;
import app.controller.imp.GenreController;
import app.model.actor.Actor;
import app.model.director.Director;
import app.model.film.Film;
import app.model.genre.Genre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class EditFilm extends Films implements Initializable{
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

    public EditFilm(Film film) throws IOException {
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

        LinkedList<String> genres = genreList.getItems().stream().map(TextInputControl::getText).filter(text -> text.length() > 0).collect(Collectors.toCollection(LinkedList::new));
        LinkedList<Genre> genreList = getEntitiesByNames(genres, Genre.class);
        LinkedList<String> genreIds = genreList.stream().map(Genre::getId).collect(Collectors.toCollection(LinkedList::new));


        LinkedList<String> actors = actorList.getItems().stream().map(TextInputControl::getText).filter(text -> text.length() > 0).collect(Collectors.toCollection(LinkedList::new));
        LinkedList<Actor> actorList = getEntitiesByNames(actors, Actor.class);
        LinkedList<String> actorIds = actorList.stream().map(Actor::getId).collect(Collectors.toCollection(LinkedList::new));

        LinkedList<String> directors = directorList.getItems().stream().map(TextInputControl::getText).filter(text -> text.length() > 0).collect(Collectors.toCollection(LinkedList::new));
        LinkedList<Director> directorList = getEntitiesByNames(directors, Director.class);
        LinkedList<String> directorIds = directorList.stream().map(Director::getId).collect(Collectors.toCollection(LinkedList::new));

        RequestFilm film = new RequestFilm(
                this.film.getId(),
                titleField.getText(),
                this.film.getDate(),
                genreIds,
                directorIds,
                actorIds
        );

//        EditEntityRequest editEntityRequest = new EditEntityRequest(film);
//        try {
//            communicationInterface.exchange(editEntityRequest);
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        closeStage(event);
    }
}
