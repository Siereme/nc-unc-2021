package app.viewFX;

import app.viewFX.login.LoginController;
import app.viewFX.menu.AbstractController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("login.fxml"));
        Stage stageLogin = new Stage();
        stageLogin.setScene(new Scene(loader.load()));
        stageLogin.showAndWait();
        loader = new FXMLLoader(AbstractController.class.getResource("main-menu.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
