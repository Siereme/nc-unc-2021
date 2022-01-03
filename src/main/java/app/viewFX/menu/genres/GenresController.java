package app.viewFX.menu.genres;

import app.model.actor.Actor;
import app.model.director.Director;
import app.model.film.Film;
import app.model.genre.Genre;
import app.viewFX.menu.AbstractController;
import app.viewFX.menu.films.TableFilm;
import client.CommunicationInterface;
import dto.request.imp.GetAllEntitiesRequest;
import dto.response.imp.GetEntitiesResponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class GenresController extends AbstractController implements Initializable {
    public void setGenresList(List<Genre> genresList) {
        this.genresList = genresList;
    }
    private List<Genre> genresList;
    final List<TableGenre> tableGenreList = new LinkedList<>();
    @FXML
    private TableView<TableGenre> genreTable;
    @FXML
    private TableColumn<TableGenre, String> tittle;
    @FXML
    private TableColumn<TableGenre, Boolean> select;
    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchTextField;

    public GenresController() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Genre> newGenreList = getGenresFromRepository();
        setGenresList(newGenreList);
        fillTableByGenresList(genresList);
        tableGenreList.forEach(genre -> {
            select.setCellValueFactory(new PropertyValueFactory<>("checked"));
            tittle.setCellValueFactory(new PropertyValueFactory<>("tittle"));
            tittle.setCellFactory(TextFieldTableCell.forTableColumn());
        });
        ObservableList<TableGenre> observable = FXCollections.observableArrayList(tableGenreList);
        genreTable.setItems(observable);
        genreTable.setFixedCellSize(100.0);
        genreTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void search(ActionEvent actionEvent) {
        String genre = searchTextField.getText();
        // TODO send request, get response
        System.out.println("not implemented");
    }

    public void add(ActionEvent actionEvent) {
        System.out.println("not implemented");
    }

    public void edit(ActionEvent actionEvent) {
        System.out.println("not implemented");
    }

    public void remove(ActionEvent actionEvent) {
        System.out.println("not implemented");
    }

    // bad
    private List<Genre> getGenresFromRepository() {
        GetAllEntitiesRequest getAllEntitiesRequest = new GetAllEntitiesRequest(Genre.class);
        try {
            GetEntitiesResponse getEntitiesResponse = (GetEntitiesResponse) CommunicationInterface.getInstance().exchange(getAllEntitiesRequest);
            genresList = (List<Genre>) getEntitiesResponse.getEntities();
            return genresList;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void fillTableByGenresList(List<Genre> genresList) {
        List<TableGenre> tableGenres = new ArrayList<>();
        for (Genre genre : genresList) {
            TableGenre tableGenre = new TableGenre(genre);
            tableGenres.add(tableGenre);
        }
        tableGenreList.clear();
        tableGenreList.addAll(tableGenres);
    }


}
