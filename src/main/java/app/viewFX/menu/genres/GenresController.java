package app.viewFX.menu.genres;

import app.model.film.Film;
import app.model.genre.Genre;
import app.viewFX.menu.AbstractController;
import app.viewFX.menu.films.handle.AddEditFilmController;
import app.viewFX.menu.genres.handle.AddEditGenreController;
import client.CommunicationInterface;
import dto.request.imp.GetAllEntitiesRequest;
import dto.request.imp.RemoveEntityRequest;
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
        List<Genre> newGenreList = getGenresListFromRepository();
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

    private void showWindow(Genre genre) throws IOException {
        System.out.println("showWindow " + genre);
        String nameOfOperation;
        if (genre != null) nameOfOperation = "edit genre";
        else nameOfOperation = "add genre";
        final AddEditGenreController addEditGenreController =
                (AddEditGenreController) initializeWindowController(getStage(), "/app/viewFX/menu/genres/handle/handle-genre1.fxml", nameOfOperation);
        addEditGenreController.setGenre(genre);
        addEditGenreController.init();
        addEditGenreController.getStage().show();
        addEditGenreController.getStage().requestFocus();
    }


    public void add(ActionEvent actionEvent) throws IOException {
        showWindow(null);
        update();
    }

    public void edit(ActionEvent actionEvent) throws IOException {
        if (genreTable.getSelectionModel().getSelectedIndices().size() > 0) {
            Genre editGenre = genresList.get(genreTable.getSelectionModel().getSelectedIndex());
            showWindow(editGenre);
        }
    }

    public void remove(ActionEvent actionEvent) {
        if (!select.isVisible()) {
            select.setVisible(true);
            genreTable.setEditable(true);
            removeButton.setStyle("-fx-background-color: #363636; -fx-text-fill: white");
        } else {
            select.setVisible(false);
            genreTable.setEditable(false);
            removeButton.setStyle("");

            List<String> removeGenreIds = new ArrayList<>();
            for (TableGenre genre : tableGenreList) {
                if (genre.getChecked().isSelected()) {
                    String genreId = genre.getId();
                    removeGenreIds.add(genreId);
                }
            }
            if (removeGenreIds.size() > 0) {
                for (String id : removeGenreIds) {
                    RemoveEntityRequest removeRequest = new RemoveEntityRequest(id, Genre.class);
                    try {
                        CommunicationInterface.getInstance().exchange(removeRequest);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            update();
        }
    }

    // bad
    private List<Genre> getGenresListFromRepository() {
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

    private void update() {
        List<Genre> newGenreList = getGenresListFromRepository();
        setGenresList(newGenreList);
        fillTableByGenresList(genresList);
        genreTable.getItems().clear();
        genreTable.getItems().addAll(tableGenreList);
    }


}
