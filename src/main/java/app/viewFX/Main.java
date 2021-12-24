package app.viewFX;

import app.model.user.IUser;
import app.viewFX.login.Login;
import app.viewFX.menu.Menu;
import client.CommunicationInterface;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    protected CommunicationInterface communicationInterface = new CommunicationInterface();
    public static IUser CURRENT_USER;

    public Main() throws IOException {
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Login.class.getResource("login.fxml"));
//        Stage stageLogin = new Stage();
//        stageLogin.setScene(new Scene(loader.load()));
//        stageLogin.showAndWait();


        loader = new FXMLLoader(Menu.class.getResource("main-menu.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
