package app.viewFX.menu.films;

import app.model.actor.Actor;
import app.model.director.Director;
import app.model.film.Film;
import app.model.genre.Genre;
import app.viewFX.menu.AbstractController;
import app.viewFX.menu.films.handle.AddEditFilmController;
import client.CommunicationInterface;
import dto.request.imp.FindByFilterRequest;
import dto.request.imp.RemoveEntityRequest;
import dto.response.imp.GetFindByFilterResponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class FilmsController extends AbstractController implements Initializable {
    private List<Film> filmList;
    final List<TableFilm> tableFilmList = new LinkedList<>();
    @FXML
    private TableView<TableFilm> filmTable;
    @FXML
    private TableColumn<TableFilm, Boolean> select;
    @FXML
    private TableColumn<TableFilm, String> tittle;
    @FXML
    private TableColumn<TableFilm, Date> date;
    @FXML
    private TableColumn<TableFilm, ListView<String>> genres;
    @FXML
    private TableColumn<TableFilm, ListView<String>> actors;
    @FXML
    private TableColumn<TableFilm, ListView<String>> directors;
    @FXML
    private TextField actor;
    @FXML
    private TextField director;
    @FXML
    private TextField genre;
    @FXML
    private Button removeButton;
    @FXML
    private Button editButton;

    public FilmsController() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getFilms();
        tableFilmList.forEach(film -> {
            select.setCellValueFactory(new PropertyValueFactory<>("checked"));
            tittle.setCellValueFactory(new PropertyValueFactory<>("tittle"));
            tittle.setCellFactory(TextFieldTableCell.forTableColumn());
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            genres.setCellValueFactory(new PropertyValueFactory<>("genres"));
            actors.setCellValueFactory(new PropertyValueFactory<>("actors"));
            directors.setCellValueFactory(new PropertyValueFactory<>("directors"));
        });
        ObservableList<TableFilm> observable = FXCollections.observableArrayList(tableFilmList);
        filmTable.setItems(observable);
        filmTable.setFixedCellSize(100.0);
        filmTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void search() {
        getFilms();
        filmTable.getItems().clear();
        filmTable.getItems().addAll(tableFilmList);
    }

    public void add() throws IOException {
        showWindow(null);
        search();
    }

    public void edit() throws IOException {
        if (filmTable.getSelectionModel().getSelectedIndices().size() > 0) {
            Film editFilm = filmList.get(filmTable.getSelectionModel().getSelectedIndex());
            showWindow(editFilm);
        }
    }

    private void showWindow(Film editFilm) throws IOException {
        System.out.println("showWindow " + editFilm);

        final AddEditFilmController addFiLm =
                (AddEditFilmController) initializeWindowController(getStage(), "/app/viewFX/menu/films/handle/handle-film.fxml", "Add film");
        addFiLm.setFilm(editFilm);
        addFiLm.init();
        addFiLm.getStage().show();
        addFiLm.getStage().requestFocus();
    }

    public void remove() {
        if (!select.isVisible()) {
            select.setVisible(true);
            filmTable.setEditable(true);
            removeButton.setStyle("-fx-background-color: #363636; -fx-text-fill: white");
        } else {
            select.setVisible(false);
            filmTable.setEditable(false);
            removeButton.setStyle("");

            List<String> removeFilmIds = new ArrayList<>();
            for (TableFilm film : tableFilmList) {
                if (film.getChecked().isSelected()) {
                    String film1Id = film.getId();
                    removeFilmIds.add(film1Id);
                }
            }
            if (removeFilmIds.size() > 0) {
                for (String id : removeFilmIds) {
                    RemoveEntityRequest removeRequest = new RemoveEntityRequest(id, Film.class);
                    try {
                        CommunicationInterface.getInstance().exchange(removeRequest);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            search();
        }
    }

    private void getFilms() {
        FindByFilterRequest findByFilterRequest = new FindByFilterRequest(new LinkedList<>(Collections.singleton(actor.getText())),
                new LinkedList<>(Collections.singleton(genre.getText())), new LinkedList<>(Collections.singleton(director.getText())),
                Film.class);
        try {
            GetFindByFilterResponse getFindByFilterResponse =
                    (GetFindByFilterResponse) CommunicationInterface.getInstance().exchange(findByFilterRequest);
            filmList = getFindByFilterResponse.getFilms();
            tableFilmList.clear();
            List<TableFilm> tableFilms = new ArrayList<>();
            for (Film film : filmList) {
                List<Genre> genreList = getEntitiesByIds(film.getGenres(), Genre.class);
                List<Actor> actorList = getEntitiesByIds(film.getActors(), Actor.class);
                List<Director> directorList = getEntitiesByIds(film.getDirectors(), Director.class);
                TableFilm tableFilm = new TableFilm(film, genreList, actorList, directorList);
                tableFilms.add(tableFilm);
            }
            tableFilmList.addAll(tableFilms);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
