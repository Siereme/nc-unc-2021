package app.viewFX.menu.directors;

import app.model.actor.Actor;
import app.model.director.Director;
import app.model.film.Film;
import app.viewFX.menu.AbstractController;
import app.viewFX.menu.actors.TableActor;
import app.viewFX.menu.actors.handle.AddEditActorController;
import app.viewFX.menu.directors.handle.AddEditDirectorController;
import app.viewFX.menu.films.TableFilm;
import client.CommunicationInterface;
import dto.request.Request;
import dto.request.imp.GetAllEntitiesRequest;
import dto.request.imp.GetEntityRequest;
import dto.request.imp.RemoveEntityRequest;
import dto.request.imp.SearchEntityRequest;
import dto.response.Response;
import dto.response.imp.GetEntitiesResponse;
import dto.response.imp.GetEntityResponse;
import dto.response.imp.GetSearchEntityResponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class DirectorController extends AbstractController implements Initializable {

    public DirectorController() {

    }

    private List<Director> directorList;
    List<TableDirector> tableDirectorList;

    @FXML
    private TableView<TableDirector> directorTableView;
    @FXML
    private TableColumn<TableFilm, Boolean> select;
    @FXML
    private TableColumn<TableFilm, String> name;
    @FXML
    private TableColumn<TableFilm, String> age;
    @FXML
    private TableColumn<TableFilm, ListView<String>> films;
    @FXML
    private Button addButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button editButton;
    @FXML
    private TextField directorNameTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.directorList = getDirectorListFromRepository();
        this.tableDirectorList = getTableDirectorListFromDirectorList(this.directorList);
        tableDirectorList.forEach(genre -> {
            select.setCellValueFactory(new PropertyValueFactory<>("checked"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            age.setCellValueFactory(new PropertyValueFactory<>("age"));
            films.setCellValueFactory(new PropertyValueFactory<>("films"));
        });
        ObservableList<TableDirector> observable = FXCollections.observableArrayList(tableDirectorList);
        directorTableView.setItems(observable);
        directorTableView.setFixedCellSize(100.0);
        directorTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private List<Director> getDirectorListFromRepository() {
        GetAllEntitiesRequest getAllEntitiesRequest = new GetAllEntitiesRequest(Director.class);
        try {
            GetEntitiesResponse getEntitiesResponse = (GetEntitiesResponse) CommunicationInterface.getInstance().exchange(getAllEntitiesRequest);
            directorList = (List<Director>) getEntitiesResponse.getEntities();
            return directorList;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<TableDirector> getTableDirectorListFromDirectorList(List<Director> directorList){
        List<TableDirector> tableDirectors = new ArrayList<>();
        for (Director director : directorList) {
            List<Film> filmList = getFilmListToDirector(director);
            TableDirector tableDirector = new TableDirector(director, filmList);
            tableDirectors.add(tableDirector);
        }
        return tableDirectors;
    }

    private static List<Film> getFilmListToDirector(Director director){
        List<String> filmIds = director.getFilms();
        List<Film> directorFilms = new LinkedList<>();
        for (String filmId : filmIds) {
            Request getFilmByIdRequest = new GetEntityRequest(filmId, Film.class);
            try {
                Response getFilmResponse = CommunicationInterface.getInstance().exchange(getFilmByIdRequest);
                Film film = ((GetEntityResponse<Film>) getFilmResponse).getEntity();
                directorFilms.add(film);
            }
            catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }
        return directorFilms;
    }

    public void search(ActionEvent actionEvent) {
        List<Director> directorList = new LinkedList<>();
        String directorName = directorNameTextField.getText();
        Request searchRequest = new SearchEntityRequest(directorName, Director.class);
        try {
            Response response = CommunicationInterface.getInstance().exchange(searchRequest);
            directorList = (List<Director>) ((GetSearchEntityResponse) response).getEntities();
            System.out.println(response);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.directorList = directorList;
        this.tableDirectorList = getTableDirectorListFromDirectorList(this.directorList);
        ObservableList<TableDirector> observable = FXCollections.observableArrayList(tableDirectorList);
        directorTableView.setItems(observable);
        directorTableView.setFixedCellSize(100.0);
        directorTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void add(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        showWindow(null);
        update();
    }

    public void remove(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (!select.isVisible()) {
            select.setVisible(true);
            directorTableView.setEditable(true);
            removeButton.setStyle("-fx-background-color: #363636; -fx-text-fill: white");
        } else {
            select.setVisible(false);
            directorTableView.setEditable(false);
            removeButton.setStyle("");

            List<String> removeDirectorIds = new ArrayList<>();
            for (TableDirector director : tableDirectorList) {
                if (director.getChecked().isSelected()) {
                    String directorId = director.getId();
                    removeDirectorIds.add(directorId);
                }
            }
            if (removeDirectorIds.size() > 0) {
                for (String id : removeDirectorIds) {
                    RemoveEntityRequest removeRequest = new RemoveEntityRequest(id, Director.class);
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

    private void update() throws IOException, ClassNotFoundException {
        directorList = getDirectorListFromRepository();
        tableDirectorList = getTableDirectorListFromDirectorList(directorList);
        directorTableView.getItems().clear();
        directorTableView.getItems().addAll(tableDirectorList);
    }

    public void edit(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        // FIXME
        if (directorTableView.getSelectionModel().getSelectedIndices().size() > 0) {
            Director director = directorList.get(directorTableView.getSelectionModel().getSelectedIndex());
            showWindow(director);
        }
        update();
    }

    private void showWindow(Director director) throws IOException {
        System.out.println("showWindow " + director);
        String nameOfOperation;
        if (director != null) nameOfOperation = "edit actor";
        else nameOfOperation = "add actor";
        final AddEditDirectorController addEditDirectorController =
                (AddEditDirectorController) initializeWindowController(getStage(), "/app/viewFX/menu/directors/handle/handle-director.fxml", nameOfOperation);
        addEditDirectorController.setDirector(director);
        addEditDirectorController.init();
        addEditDirectorController.getStage().show();
        addEditDirectorController.getStage().requestFocus();
    }

}
