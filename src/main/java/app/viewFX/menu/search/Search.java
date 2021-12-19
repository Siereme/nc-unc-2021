package app.viewFX.menu.search;

import app.controller.ActorController;
import app.controller.DirectorController;
import app.controller.FilmController;
import app.controller.GenreController;
import app.controller.commands.find.FindUserCommands;
import app.model.actor.Actor;
import app.model.director.Director;
import app.model.film.Film;
import app.model.genre.Genre;
import app.repository.FilmsRepository;
import app.viewFX.IView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class Search {
    FilmController filmController;
    @FXML private ListView<String> filmList;
    @FXML private TextField actor;
    @FXML private TextField director;
    @FXML private TextField genre;

    public void search(ActionEvent event) {
        filmController = new FilmController();
        Actor actorSearch = null;
        if(actor.getText().length() > 0){
            actorSearch =  new ActorController().getEntityByName(actor.getText());
        }
        Director directorSearch = null;
        if(director.getText().length() > 0){
            directorSearch = new DirectorController().getEntityByName(director.getText());
        }
        Genre genreSearch = null;
        if(genre.getText().length() > 0){
            genreSearch = new GenreController().getEntityByName(genre.getText());
        }

        List<String> listIds = new LinkedList<>();
        if(actorSearch != null) {
            listIds.addAll(filmController.getFilmsByActor(actorSearch));
            filmController = new FilmController(new FilmsRepository(listIds.stream().map(x -> filmController.getEntityById(x)).toList()));
        }
        if(directorSearch != null) {
            listIds.clear();
            listIds.addAll(filmController.getFilmsByDirector(directorSearch));
            filmController = new FilmController(new FilmsRepository(listIds.stream().map(x -> filmController.getEntityById(x)).toList()));
        }
        if(genreSearch != null) {
            listIds.clear();
            listIds.addAll(filmController.getFilmsByGenre(genreSearch));
            filmController = new FilmController(new FilmsRepository(listIds.stream().map(x -> filmController.getEntityById(x)).toList()));
        }
        List<String> resultList = filmController.getEntities().stream().map(x -> filmController.entityToString(filmController.getEntityById(x))).toList();
        ObservableList<String> observableList = FXCollections.observableArrayList(resultList);
        this.filmList.getItems().removeAll();
        this.filmList.setItems(observableList);
    }
}
