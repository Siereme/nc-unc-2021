package app.viewFX.menu.actors;

import app.model.actor.Actor;
import app.model.film.Film;
import app.model.genre.Genre;
import app.viewFX.menu.AbstractController;
import app.viewFX.menu.actors.handle.AddEditActorController;
import app.viewFX.menu.films.TableFilm;
import app.viewFX.menu.genres.TableGenre;
import app.viewFX.menu.genres.handle.AddEditGenreController;
import client.CommunicationInterface;
import dto.request.Request;
import dto.request.imp.GetAllEntitiesRequest;
import dto.request.imp.GetEntityRequest;
import dto.request.imp.RemoveEntityRequest;
import dto.response.Response;
import dto.response.imp.GetEntitiesResponse;
import dto.response.imp.GetEntityResponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ActorsController extends AbstractController implements Initializable {


    public ActorsController() {

    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }

    private List<Actor> actorList;
    List<TableActor> tableActorList;

    @FXML
    private TableView<TableActor> actorTableView;
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
    private TextField actorName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.actorList = getActorListFromRepository();
        try {
            this.tableActorList = getTableActorListFromActorList(this.actorList);
            tableActorList.forEach(genre -> {
                select.setCellValueFactory(new PropertyValueFactory<>("checked"));
                name.setCellValueFactory(new PropertyValueFactory<>("name"));
                age.setCellValueFactory(new PropertyValueFactory<>("age"));
                films.setCellValueFactory(new PropertyValueFactory<>("films"));
            });
            ObservableList<TableActor> observable = FXCollections.observableArrayList(tableActorList);
            actorTableView.setItems(observable);
            actorTableView.setFixedCellSize(100.0);
            actorTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private List<Actor> getActorListFromRepository() {
        GetAllEntitiesRequest getAllEntitiesRequest = new GetAllEntitiesRequest(Actor.class);
        try {
            GetEntitiesResponse getEntitiesResponse = (GetEntitiesResponse) CommunicationInterface.getInstance().exchange(getAllEntitiesRequest);
            actorList = (List<Actor>) getEntitiesResponse.getEntities();
            return actorList;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<TableActor> getTableActorListFromActorList(List<Actor> actorList) throws IOException, ClassNotFoundException {
        List<TableActor> tableActors = new ArrayList<>();
        for (Actor actor : actorList) {
            List<String> filmIds = actor.getFilms();
            List<Film> filmList = getEntitiesByIds(filmIds, Film.class);
            TableActor tableActor = new TableActor(actor, filmList);
            tableActors.add(tableActor);
        }
        return tableActors;
    }

    public void search(ActionEvent actionEvent) {
        // TODO

    }

    public void add(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        showWindow(null);
        update();
    }

    public void remove(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (!select.isVisible()) {
            select.setVisible(true);
            actorTableView.setEditable(true);
            removeButton.setStyle("-fx-background-color: #363636; -fx-text-fill: white");
        } else {
            select.setVisible(false);
            actorTableView.setEditable(false);
            removeButton.setStyle("");

            List<String> removeActorIds = new ArrayList<>();
            for (TableActor actor : tableActorList) {
                if (actor.getChecked().isSelected()) {
                    String actorId = actor.getId();
                    removeActorIds.add(actorId);
                }
            }
            if (removeActorIds.size() > 0) {
                for (String id : removeActorIds) {
                    RemoveEntityRequest removeRequest = new RemoveEntityRequest(id, Actor.class);
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

    public void edit(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (actorTableView.getSelectionModel().getSelectedIndices().size() > 0) {
            Actor editActor = actorList.get(actorTableView.getSelectionModel().getSelectedIndex());
            showWindow(editActor);
        }
        update();
    }

    private void update() throws IOException, ClassNotFoundException {
        actorList = getActorListFromRepository();
        tableActorList = getTableActorListFromActorList(actorList);
        actorTableView.getItems().clear();
        actorTableView.getItems().addAll(tableActorList);
    }

    private void showWindow(Actor actor) throws IOException {
        System.out.println("showWindow " + actor);
        String nameOfOperation;
        if (actor != null) nameOfOperation = "edit actor";
        else nameOfOperation = "add actor";
        final AddEditActorController addEditActorController =
                (AddEditActorController) initializeWindowController(getStage(), "/app/viewFX/menu/actors/handle/handle-actor.fxml", nameOfOperation);
        addEditActorController.setActor(actor);
        addEditActorController.init();
        addEditActorController.getStage().show();
        addEditActorController.getStage().requestFocus();
    }

}
