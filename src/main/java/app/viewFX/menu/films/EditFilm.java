package app.viewFX.menu.films;

import app.controller.ActorController;
import app.controller.DirectorController;
import app.controller.GenreController;
import app.model.film.Film;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class EditFilm implements Initializable{
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

    public EditFilm(Film film) {
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
        LinkedList<String> genres = new LinkedList<>(genreList.getItems().stream().filter(x -> x.getText().length() > 0).map(x -> genreController.getEntityByName(x.getText()).getId()).toList());
        LinkedList<String> actors = new LinkedList<>(actorList.getItems().stream().filter(x -> x.getText().length() > 0).map(x -> actorController.getEntityByName(x.getText()).getId()).toList());
        LinkedList<String> directors = new LinkedList<>(directorList.getItems().stream().filter(x -> x.getText().length() > 0).map(x -> directorController.getEntityByName(x.getText()).getId()).toList());
        Film addFilm = new Film(titleField.getText(), new Date(), genres, directors, actors);
        System.out.println(addFilm.getTittle());
        System.out.println(addFilm.getDate());
        System.out.println(addFilm.getActors());
        System.out.println(addFilm.getDirectors());
        System.out.println(addFilm.getGenres());

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
