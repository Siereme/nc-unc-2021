package app.viewFX.menu.add;

import app.controller.comandsFX.add.AddUserCommands;
import app.viewFX.IView;
import app.viewFX.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Add implements Initializable {
    private AddUserCommands commands;
    private List<IView> viewList;
    private List<String> commandNameList;
    @FXML private ListView<String> menuList;
    @FXML private AnchorPane contentWrapper;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        commands = new AddUserCommands();
        viewList =
                commands.commands.entrySet().stream().filter(e -> e.getValue().contains(Main.IS_ADMIN)).map(x -> {
                    try {
                        return x.getKey().getConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | NullPointerException e) {
                        e.printStackTrace();
                        return null;
                    }
                }).filter(Objects::nonNull).collect(Collectors.toList());

        commandNameList = new LinkedList<String>(viewList.stream().map(x -> x.getTitle()).collect(Collectors.toList()));

        ObservableList<String> items = FXCollections.observableArrayList(commandNameList);

        menuList.setItems(items);

        SelectionModel<String> selection = menuList.getSelectionModel();
        selection.selectedItemProperty().addListener(handleSelection());
    }

    private ChangeListener<? super String> handleSelection() {
        AnchorPane content = this.contentWrapper;
        List<IView> listView = this.viewList;
        return new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                List<IView> view = listView.stream().filter(x -> Objects.equals(x.getTitle(), newValue)).findFirst().stream().collect(Collectors.toList());
                Optional<IView> view1 = listView.stream().filter(x -> Objects.equals(x.getTitle(), newValue)).findFirst();

                if(view.size() > 0){
                    FXMLLoader loader = new FXMLLoader(view.get(0).getClass().getResource(view.get(0).getFxmlFile()));
                    try {
                        content.getChildren().add((Pane) loader.load());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}
