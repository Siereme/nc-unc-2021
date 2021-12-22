package app.viewFX.menu.remove;

import app.controller.comandsFX.remove.RemoveUserCommands;
import app.viewFX.menu.View;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Remove extends View implements Initializable {
    @FXML
    private ListView<String> menuList;
    @FXML private AnchorPane contentWrapper;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drawSubMenu(new RemoveUserCommands().commands, menuList, contentWrapper);
    }
}
