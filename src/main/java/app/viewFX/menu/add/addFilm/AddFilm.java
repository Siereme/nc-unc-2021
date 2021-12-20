package app.viewFX.menu.add.addFilm;

import app.viewFX.IView;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AddFilm implements Initializable, IView {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public String getTitle() {
        String title = "Add Film";
        return title;
    }

    public String getFxmlFile(){
        String fxmlFile = "addFilm.fxml";
        return fxmlFile;
    }
}
