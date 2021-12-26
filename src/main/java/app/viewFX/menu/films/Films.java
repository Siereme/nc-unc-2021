package app.viewFX.menu.films;

import app.controller.imp.FilmController;
import app.model.actor.Actor;
import app.model.director.Director;
import app.model.film.Film;
import app.model.genre.Genre;
import app.viewFX.menu.Menu;
import app.viewFX.menu.films.handle.AddFilm;
import app.viewFX.menu.films.handle.EditFilm;
import app.viewFX.menu.films.handle.HandleFilm;
import dto.request.EditEntityRequest;
import dto.request.FindByFilterRequest;
import dto.response.GetFindByFilterResponse;
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
    List<Film> filmList;
    List<TableFilm> films = new LinkedList<>();
    @FXML private TableView<TableFilm> filmTable;
    @FXML private TableColumn<TableFilm, Boolean> select;
    @FXML private TableColumn<TableFilm, String> title;
    @FXML private TableColumn<TableFilm, Date> date;
    @FXML private TableColumn<TableFilm, ListView<String>> genres;
    @FXML private TableColumn<TableFilm, ListView<String>> actors;
    @FXML private TableColumn<TableFilm, ListView<String>> directors;
    @FXML private TextField actor;
    @FXML private TextField director;
    @FXML private TextField genre;
    @FXML private Button removeButton;
    @FXML private Button editButton;

    public Films() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getFilms();

        films.forEach(film -> {
            select.setCellValueFactory(new PropertyValueFactory<>("checked"));
            title.setCellValueFactory(new PropertyValueFactory<>("title"));
            title.setCellFactory(TextFieldTableCell.forTableColumn());
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            genres.setCellValueFactory(new PropertyValueFactory<>("genres"));
            actors.setCellValueFactory(new PropertyValueFactory<>("actors"));
            directors.setCellValueFactory(new PropertyValueFactory<>("directors"));
        });
        ObservableList<TableFilm> observable = FXCollections.observableArrayList(films);
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
        HandleFilm handleFilmController = new AddFilm();
        handle(handleFilmController);
    }

    public void edit(ActionEvent event) throws IOException {
        if(filmTable.getSelectionModel().getSelectedIndices().size() > 0){
            Film editFilm = filmList.get(filmTable.getSelectionModel().getSelectedIndex());
            HandleFilm handleFilmController = new EditFilm(editFilm);
            handle(handleFilmController);
        }
    }

    private void handle(HandleFilm handleFilm) throws IOException {
        FXMLLoader loader = new FXMLLoader(handleFilm.getClass().getResource("handle-film.fxml"));
        loader.setController(handleFilm);
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.showAndWait();
        search();
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
            List<String> removeFilmIds = films.stream().filter(film -> film.getChecked().isSelected()).map(TableFilm::getId).toList();

            search();
        }
    }


    private void getFilms() {
        FindByFilterRequest findByFilterRequest =
                new FindByFilterRequest(
                        new LinkedList<String>(Collections.singleton(actor.getText())),
                        new LinkedList<String>(Collections.singleton(genre.getText())),
                        new LinkedList<String>(Collections.singleton(director.getText())),
                        Film.class
                );
        try {
            GetFindByFilterResponse getFindByFilterResponse = (GetFindByFilterResponse) communicationInterface.exchange(findByFilterRequest);
            filmList = getFindByFilterResponse.getFilms();
            films.clear();
            List<TableFilm> tableFilms = new ArrayList<>();
            for (Film film : filmList) {
                LinkedList<Genre> genreList = getEntitiesByIds(film.getGenres(), Genre.class);
                LinkedList<Actor> actorList = getEntitiesByIds(film.getActors(), Actor.class);
                LinkedList<Director> directorList = getEntitiesByIds(film.getDirectors(), Director.class);
                TableFilm tableFilm = new TableFilm(film, genreList, actorList, directorList);
                tableFilms.add(tableFilm);
            }
            films.addAll(tableFilms);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
