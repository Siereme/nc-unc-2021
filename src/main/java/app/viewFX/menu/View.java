package app.viewFX.menu;

import app.controller.comandsFX.add.AddUserCommands;
import app.viewFX.IView;
import app.viewFX.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class View {
    private boolean isAdmin;

    public View() {
    }

    public View(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    protected void drawSubMenu(Map<Class<? extends IView>, List<Boolean>> commands, ListView menuList, AnchorPane wrapper){
        List<IView> viewList =
                commands.entrySet().stream().filter(e -> e.getValue().contains(Main.IS_ADMIN)).map(x -> {
                    try {
                        return x.getKey().getConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | NullPointerException e) {
                        e.printStackTrace();
                        return null;
                    }
                }).filter(Objects::nonNull).collect(Collectors.toList());

        List<String> commandNameList = viewList.stream().map(IView::getTitle).collect(Collectors.toCollection(LinkedList::new));

        ObservableList<String> items = FXCollections.observableArrayList(commandNameList);

        menuList.setItems(items);

        SelectionModel<String> selection = menuList.getSelectionModel();
        selection.selectedItemProperty().addListener(handleSelection(viewList, wrapper));
    }

    private ChangeListener<? super String> handleSelection(List<IView> viewList, AnchorPane wrapper) {
        return new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                List<IView> view = viewList.stream().filter(x -> Objects.equals(x.getTitle(), newValue)).findFirst().stream().collect(Collectors.toList());

                if(view.size() > 0){
                    FXMLLoader loader = new FXMLLoader(view.get(0).getClass().getResource(view.get(0).getFxmlFile()));
                    try {
                        wrapper.getChildren().add((Pane) loader.load());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}
