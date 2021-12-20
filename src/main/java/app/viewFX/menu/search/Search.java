package app.viewFX.menu.search;

import app.controller.ActorController;
import app.controller.DirectorController;
import app.controller.FilmController;
import app.controller.GenreController;
import app.model.actor.Actor;
import app.model.director.Director;
import app.model.genre.Genre;
import app.repository.FilmsRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.LinkedList;
import java.util.List;

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
