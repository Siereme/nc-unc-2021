package app.viewFX.menu.films.handle;

import app.model.film.Film;
import app.viewFX.menu.Menu;
import dto.request.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;


public class HandleFilm extends Menu {
    private final Class<? extends Request> request;
    private String id;
    @FXML private TextField titleField;
    @FXML private TextField dateField;
    @FXML private ListView<TextField> genres;
    @FXML private ListView<TextField> actors;
    @FXML private ListView<TextField> directors;
    @FXML private Button handleButton;
    private Film film;

    public HandleFilm() throws IOException {
        request = null;
    }
    public HandleFilm(Class<? extends Request> request) throws IOException {
        this.request = request;
    }

    public void handle(ActionEvent event){
//        LinkedList<String> genreNames = genres.getItems().stream().map(TextInputControl::getText).filter(text -> text.length() > 0).collect(Collectors.toCollection(LinkedList::new));
//        LinkedList<Genre> genreList = getEntitiesByNames(genreNames, Genre.class);
//        LinkedList<String> genreIds = genreList.stream().map(Genre::getId).collect(Collectors.toCollection(LinkedList::new));
//
//
//        LinkedList<String> actorNames = actors.getItems().stream().map(TextInputControl::getText).filter(text -> text.length() > 0).collect(Collectors.toCollection(LinkedList::new));
//        LinkedList<Actor> actorList = getEntitiesByNames(actorNames, Actor.class);
//        LinkedList<String> actorIds = actorList.stream().map(Actor::getId).collect(Collectors.toCollection(LinkedList::new));
//
//        LinkedList<String> directorNames = directors.getItems().stream().map(TextInputControl::getText).filter(text -> text.length() > 0).collect(Collectors.toCollection(LinkedList::new));
//        LinkedList<Director> directorList = getEntitiesByNames(directorNames, Director.class);
//        LinkedList<String> directorIds = directorList.stream().map(Director::getId).collect(Collectors.toCollection(LinkedList::new));
//
//        EntityFilm film = new EntityFilm(
//                this.id,
//                titleField.getText(),
//                new Date(),
//                genreIds,
//                directorIds,
//                actorIds
//        );
//
//        try {
//            Request handleRequest = request.getDeclaredConstructor(film.getClass(), Film.class).newInstance(film, Film.class);
//        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//
//        closeStage(event);
    }
}
