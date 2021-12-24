package app.viewFX.menu.films;

import app.controller.ActorController;
import app.controller.DirectorController;
import app.controller.FilmController;
import app.controller.GenreController;
import app.model.actor.Actor;
import app.model.director.Director;
import app.model.film.Film;
import app.model.genre.Genre;
import app.repository.FilmsRepository;
import app.viewFX.Main;
import dto.request.CreateFindByFilterRequest;
import dto.request.CreateGetEntitiesByNamesRequest;
import dto.request.CreateRemoveEntityRequest;
import dto.response.GetFindByFilterResponse;
import dto.response.GetRemoveEntityResponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Films extends Menu implements Initializable {
    FilmController filmController;
    List<Film> filmList;
    List<TableFilm> films = new LinkedList<>();
    @FXML private TableView<TableFilm> filmTable;
    @FXML private TableColumn<TableFilm, Boolean> select;
    @FXML private TableColumn<TableFilm, String> title;
    @FXML private TableColumn<TableFilm, Date> date;
    @FXML private TableColumn<TableFilm, ListView<String>> genres;
    @FXML private TableColumn<TableFilm, ListView<String>> actors;
    @FXML private TableColumn<TableFilm, ListView<String>> directors;
    private ObservableList<TableFilm> observable;
    @FXML private TextField actor;
    @FXML private TextField director;
    @FXML private TextField genre;
    @FXML private Button removeButton;
    @FXML private Button editButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getFilms();

        films.stream().forEach(film -> {
            select.setCellValueFactory(new PropertyValueFactory<>("checked"));
            title.setCellValueFactory(new PropertyValueFactory<>("title"));
            title.setCellFactory(TextFieldTableCell.forTableColumn());
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            genres.setCellValueFactory(new PropertyValueFactory<>("genres"));
            actors.setCellValueFactory(new PropertyValueFactory<>("actors"));
            directors.setCellValueFactory(new PropertyValueFactory<>("directors"));
        });
        observable = FXCollections.observableArrayList(films);
        filmTable.setItems(observable);
        filmTable.setFixedCellSize(100.0);
        filmTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void search() {
        getFilms();
        filmTable.getItems().clear();
        filmTable.getItems().addAll(films);
    }

    public void add(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(AddFilm.class.getResource("add-film.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.showAndWait();
        search();
    }

    public void edit(ActionEvent event) throws IOException {
        if(filmTable.getSelectionModel().getSelectedIndices().size() > 0){
            FXMLLoader loader = new FXMLLoader(EditFilm.class.getResource("edit-film.fxml"));
            Film editFilm = filmList.get(filmTable.getSelectionModel().getSelectedIndex());
            EditFilm editFilmController = new EditFilm(editFilm);
            loader.setController(editFilmController);
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.showAndWait();
            search();
        }
    }

    public void remove(ActionEvent event) {
        if(!select.isVisible()){
            select.setVisible(true);
            filmTable.setEditable(true);
            removeButton.setStyle("-fx-background-color: #363636; -fx-text-fill: white");
        }
        else {
            select.setVisible(false);
            filmTable.setEditable(false);
            removeButton.setStyle("");

            FilmController filmController = new FilmController();
            List<String> removeFilmIds = films.stream().filter(film -> film.getChecked().isSelected()).map(x -> x.getId()).toList();
            for(String id : removeFilmIds){
                CreateRemoveEntityRequest removeEntityRequest = new CreateRemoveEntityRequest(id, filmController);
                new GetRemoveEntityResponse<Film>("response", removeEntityRequest);
            }
            search();
        }
    }


    private void getFilms() {
        CreateFindByFilterRequest createFindByFilterRequest =
                new CreateFindByFilterRequest(
                        new LinkedList<String>(Collections.singleton(actor.getText())),
                        new LinkedList<String>(Collections.singleton(genre.getText())),
                        new LinkedList<String>(Collections.singleton(director.getText()))
                );
        GetFindByFilterResponse getFindByFilterResponse = new GetFindByFilterResponse("response", createFindByFilterRequest);
        filmList = getFindByFilterResponse.getFilms();
        films.clear();
        films.addAll(filmList.stream().map(x -> new TableFilm(x)).toList());
    }
}
