package app.viewFX.menu.remove.removeDirector;

import app.controller.imp.DirectorController;
import app.viewFX.IView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RemoveDirector implements IView, Initializable {
    private String title = "Remove director";
    private String fxmlFile = "remove-director.fxml";

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
        DirectorController controller = new DirectorController();
        List<String> directorList = controller.getEntities().stream().map(x -> controller.getEntityById(x).getName()).collect(Collectors.toList());


        ObservableList<String> observableList = FXCollections.observableArrayList(directorList);
        this.entityList.getItems().removeAll();
        this.entityList.setItems(observableList);
        this.entityList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void removeEntity(ActionEvent event) {
        DirectorController controller = new DirectorController();
        String removeEntity = controller.getEntityByName(entityList.getSelectionModel().getSelectedItem()).getId();

        List<String> removeEntityNames = this.entityList.getSelectionModel().getSelectedItems();
        this.entityList.getItems().removeIf(x -> removeEntityNames.stream().anyMatch(s -> Objects.equals(x, s)));
    }
}
