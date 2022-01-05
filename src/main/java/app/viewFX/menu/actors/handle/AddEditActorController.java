package app.viewFX.menu.actors.handle;

import app.model.actor.Actor;
import app.model.film.Film;
import client.CommunicationInterface;
import dto.request.Request;
import dto.request.imp.AddEntityRequest;
import dto.request.imp.EditEntityRequest;
import dto.response.Response;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class AddEditActorController extends HandleActorController {
    public AddEditActorController() {

    }

    @FXML
    private TextField ageField;
    @FXML
    private TextField nameField;
    @FXML
    private ListView<TextField> filmsListView;
    @FXML
    private Button handleButton;

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    private Actor actor;
    private boolean editMode;

    public void init() {
        System.out.println("initialize  " + actor);
        editMode = actor != null;
        System.out.println("editMode  " + editMode);
        if (editMode) {
            initActor();
        } else {
            initEmpty();
        }
    }

    private void initEmpty() {
        for (int i = 0; i < 6; i++) {
            this.filmsListView.getItems().add(new TextField());
        }
    }

    private void initActor() {
        String age = actor.getYear();
        ageField.setText(age);
        String name = actor.getName();
        nameField.setText(name);
        List<String> filmIds = actor.getFilms();
        List<Film> filmList = getEntitiesByIds(filmIds, Film.class);
        List<TextField> textFieldList = new ArrayList<>();
        for (Film film : filmList) {
            String tittle = film.getTittle();
            textFieldList.add(new TextField(tittle));
        }
        while (textFieldList.size() < 6) {
            textFieldList.add(new TextField());
        }
        ObservableList<TextField> observableList = FXCollections.observableList(textFieldList);
        filmsListView.setItems(observableList);

    }

    protected void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private Actor createActor() {
        List<String> filmNames = filmsListView.getItems().stream().map(TextField::getText).filter(text -> text.length() > 0)
                .collect(Collectors.toCollection(LinkedList::new));
        List<String> filmIds = getEntitiesIdsByNames(filmNames, Film.class);
        String actorName = nameField.getText();
        String actorAge = ageField.getText();
        Actor actor = new Actor(actorName, actorAge);
        actor.setFilms(filmIds);
        return actor;
    }

    public void handleActor(ActionEvent actionEvent) {
        Actor actor = createActor();
        if (editMode) {
            Request editRequest = new EditEntityRequest(actor, Actor.class);
            try {
                Response response = CommunicationInterface.getInstance().exchange(editRequest);
                System.out.println(response);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Request addRequest = new AddEntityRequest(actor, Actor.class);
            try {
                Response response = CommunicationInterface.getInstance().exchange(addRequest);
                System.out.println(response);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        closeStage(actionEvent);
    }

}
