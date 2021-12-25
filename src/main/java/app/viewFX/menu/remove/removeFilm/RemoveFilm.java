package app.viewFX.menu.remove.removeFilm;

import app.controller.imp.FilmController;
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

public class RemoveFilm implements IView, Initializable {
    private String title = "Remove film";
    private String fxmlFile = "remove-film.fxml";

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
        FilmController controller = new FilmController();
        List<String> filmList = controller.getEntities().stream().map(x -> controller.getEntityById(x).getTittle()).collect(Collectors.toList());

        ObservableList<String> observableList = FXCollections.observableArrayList(filmList);
        this.entityList.getItems().removeAll();
        this.entityList.setItems(observableList);
    }

    public void removeEntity(ActionEvent event) {
        FilmController controller = new FilmController();
        String removeEntity = controller.getEntityByName(entityList.getSelectionModel().getSelectedItem()).getId();

        String removeEntityName = this.entityList.getSelectionModel().getSelectedItem();
        this.entityList.getItems().removeIf(x -> Objects.equals(x, removeEntityName));
    }
}
