package app.viewFX.menu.genres;

import app.model.genre.Genre;
import app.viewFX.menu.AbstractController;
import app.viewFX.menu.genres.handle.AddEditGenreController;
import client.CommunicationInterface;
import dto.request.Request;
import dto.request.imp.GetAllEntitiesRequest;
import dto.request.imp.RemoveEntityRequest;

import dto.request.imp.SearchEntityRequest;
import dto.response.Response;
import dto.response.imp.GetEntitiesResponse;
import dto.response.imp.GetSearchEntityResponse;
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
import java.util.*;

public class GenresController extends AbstractController implements Initializable {
    public void setGenresList(List<Genre> genresList) {
        this.genresList = genresList;
    }

    private List<Genre> genresList;
    List<TableGenre> tableGenreList = new LinkedList<>();
    @FXML
    private TableView<TableGenre> genreTableView;
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
    private TextField genreNameTextField;

    public GenresController() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.genresList = getGenresListFromRepository();
        fillTableByGenresList(genresList);
        tableGenreList.forEach(genre -> {
            select.setCellValueFactory(new PropertyValueFactory<>("checked"));
            tittle.setCellValueFactory(new PropertyValueFactory<>("tittle"));
            tittle.setCellFactory(TextFieldTableCell.forTableColumn());
        });
        ObservableList<TableGenre> observable = FXCollections.observableArrayList(tableGenreList);
        genreTableView.setItems(observable);
        genreTableView.setFixedCellSize(100.0);
        genreTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void search(ActionEvent actionEvent) {
        List<Genre> genres = new LinkedList<>();
        String genreName = genreNameTextField.getText();
        Request searchRequest = new SearchEntityRequest(genreName, Genre.class);
        try {
            Response response = CommunicationInterface.getInstance().exchange(searchRequest);
            genres = (List<Genre>) ((GetSearchEntityResponse) response).getEntities();
            System.out.println(response);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.genresList = genres;
        this.tableGenreList = getTableGenreListFromGenreList(this.genresList);
        ObservableList<TableGenre> observable = FXCollections.observableArrayList(tableGenreList);
        genreTableView.setItems(observable);
        genreTableView.setFixedCellSize(100.0);
        genreTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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
        if (genreTableView.getSelectionModel().getSelectedIndices().size() > 0) {
            Genre editGenre = genresList.get(genreTableView.getSelectionModel().getSelectedIndex());
            showWindow(editGenre);
        }
        update();
    }

    public void remove(ActionEvent actionEvent) {
        if (!select.isVisible()) {
            select.setVisible(true);
            genreTableView.setEditable(true);
            removeButton.setStyle("-fx-background-color: #363636; -fx-text-fill: white");
        } else {
            select.setVisible(false);
            genreTableView.setEditable(false);
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
        genresList = getGenresListFromRepository();
        tableGenreList = getTableGenreListFromGenreList(genresList);
        genreTableView.getItems().clear();
        genreTableView.getItems().addAll(tableGenreList);
    }

    private List<TableGenre> getTableGenreListFromGenreList(List<Genre> genresList) {
        List<TableGenre> tableGenres = new ArrayList<>();
        for (Genre genre : genresList) {
            TableGenre tableGenre = new TableGenre(genre);
            tableGenres.add(tableGenre);
        }
        return tableGenres;
    }

}
