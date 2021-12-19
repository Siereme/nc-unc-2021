package app.viewFX.menu.add.addFilm;

import app.viewFX.IView;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AddFilm implements Initializable, IView {
    private String title = "Add Film";
    private String fxmlFile = "addFilm.fxml";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public String getFxmlFile(){
        return this.fxmlFile;
    }
}
