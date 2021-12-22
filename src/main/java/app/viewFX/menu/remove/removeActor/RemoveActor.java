package app.viewFX.menu.remove.removeActor;

import app.controller.ActorController;
import app.model.actor.Actor;
import app.viewFX.IView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RemoveActor implements IView, Initializable {
    private String title = "Remove actor";
    private String fxmlFile = "remove-actor.fxml";

    @FXML
    private ListView<String> entityList;



    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getFxmlFile() {
        return this.fxmlFile;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ActorController controller = new ActorController();
        List<String> actorList = controller.getEntities().stream().map(x -> controller.getEntityById(x).getName()).collect(Collectors.toList());

        ObservableList<String> observableList = FXCollections.observableArrayList(actorList);
        this.entityList.getItems().removeAll();
        this.entityList.setItems(observableList);
    }

    public void removeEntity() {
        ActorController controller = new ActorController();
        String removeEntity = controller.getEntityByName(entityList.getSelectionModel().getSelectedItem()).getId();

        String removeEntityName = this.entityList.getSelectionModel().getSelectedItem();
        this.entityList.getItems().removeIf(x -> Objects.equals(x, removeEntityName));
    }
}
