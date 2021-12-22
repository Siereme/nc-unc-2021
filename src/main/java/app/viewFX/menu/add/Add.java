package app.viewFX.menu.add;

import app.controller.comandsFX.add.AddUserCommands;
import app.viewFX.menu.View;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.*;

public class Add extends View implements Initializable {
    @FXML private ListView<String> menuList;
    @FXML private AnchorPane contentWrapper;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drawSubMenu(new AddUserCommands().commands, menuList, contentWrapper);
    }
}
