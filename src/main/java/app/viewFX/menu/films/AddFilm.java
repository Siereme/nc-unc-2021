package app.viewFX.menu.films;

import app.controller.imp.ActorController;
import app.controller.imp.DirectorController;
import app.controller.imp.GenreController;
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
import java.util.List;
import java.util.ResourceBundle;

public class AddFilm implements Initializable {
    @FXML private TextField titleField;
    @FXML private TextField dateField;
    @FXML private ListView<TextField> genreList;
    @FXML private ListView<TextField> actorList;
    @FXML private ListView<TextField> directorList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i = 0; i < 6; i++){
            genreList.getItems().add(new TextField());
            actorList.getItems().add(new TextField());
            directorList.getItems().add(new TextField());
        }
    }

    public void addFilm(ActionEvent event) {
        GenreController genreController = new GenreController();
        LinkedList<String> genres = new LinkedList<>(genreList.getItems().stream().filter(x -> x.getText().length() > 0).map(x -> genreController.getEntityByName(x.getText()).getId()).toList());
        ActorController actorController = new ActorController();
        LinkedList<String> actors = new LinkedList<>(actorList.getItems().stream().filter(x -> x.getText().length() > 0).map(x -> actorController.getEntityByName(x.getText()).getId()).toList());
        DirectorController directorController = new DirectorController();
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
