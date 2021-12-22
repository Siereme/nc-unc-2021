package app.viewFX;

import app.viewFX.login.Login;
import client.CommunicationInterface;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
/*    private String name;
    private String password;*/
    protected final CommunicationInterface communicationInterface = new CommunicationInterface();
    public static boolean IS_ADMIN;

    public Main() throws IOException {
    }

    @Override
    public void start(Stage stage) {
        loadStage(stage, Login.class, "login.fxml");
    }

    protected void loadStage(Stage stage, Class<? extends Main> viewCLass, String viewPath) {
        try {
            FXMLLoader loader = new FXMLLoader(viewCLass.getResource(viewPath));
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void loadStage(Class<? extends Main> viewCLass, String viewPath) {
        loadStage(new Stage(), viewCLass, viewPath);
    }

    protected void closeCurrentStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public static void main(String[] args) {
        launch();
    }
/*
    // возьмем значения этих полей и в клиенте сформируем запрос
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }*/

    // как бы запустим окно
    public void launchMenu() {
        launch();
    }

}
