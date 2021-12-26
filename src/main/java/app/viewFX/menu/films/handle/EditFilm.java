package app.viewFX.menu.films.handle;

import app.model.actor.Actor;
import app.model.director.Director;
import app.model.film.Film;
import app.model.genre.Genre;
import app.viewFX.menu.films.RequestFilm;
import dto.request.AddEntityRequest;
import dto.request.EditEntityRequest;
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
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
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
        for(Genre genre : genreList){
            genres.getItems().add(new TextField(genre.getTittle()));
        }
        List<Actor> actorList = getEntitiesByIds(film.getActors(), Actor.class);
        for(Actor actor : actorList){
            actors.getItems().add(new TextField(actor.getName()));
        }
        List<Director> directorList = getEntitiesByIds(film.getDirectors(), Director.class);
        for(Director director : directorList){
            directors.getItems().add(new TextField(director.getName()));
        }
        while (genres.getItems().size() < 6){
            genres.getItems().add(new TextField());
        }
        while (actors.getItems().size() < 6){
            actors.getItems().add(new TextField());
        }
        while (directors.getItems().size() < 6){
            directors.getItems().add(new TextField());
        }

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
        LinkedList<Genre> genreList = getEntitiesByNames(genreNames, Genre.class);
        LinkedList<String> genreIds = genreList.stream().map(Genre::getId).collect(Collectors.toCollection(LinkedList::new));


        LinkedList<String> actorNames = actors.getItems().stream().map(TextField::getText).filter(text -> text.length() > 0).collect(Collectors.toCollection(LinkedList::new));
        LinkedList<Actor> actorList = getEntitiesByNames(actorNames, Actor.class);
        LinkedList<String> actorIds = actorList.stream().map(Actor::getId).collect(Collectors.toCollection(LinkedList::new));

        LinkedList<String> directorNames = directors.getItems().stream().map(TextField::getText).filter(text -> text.length() > 0).collect(Collectors.toCollection(LinkedList::new));
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
            EditEntityRequest editRequest = new EditEntityRequest(film, Film.class);
            communicationInterface.exchange(editRequest);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        closeStage(event);
    }
}
