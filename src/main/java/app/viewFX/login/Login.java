package app.viewFX.login;

import app.controller.UserController;
import app.model.user.IUser;
import app.viewFX.Main;
import app.viewFX.menu.Menu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Login{
    @FXML private Label status;
    @FXML private TextField loginField;
    @FXML private TextField passwordField;
    private UserController userController;

    @FXML
    protected void login(ActionEvent event){
        userController = new UserController();
        IUser currentUser = userController.getEntityByLogin(loginField.getText(), passwordField.getText());
        if(currentUser == null){
            status.setText("Incorrect login or password");
            loginField.setText("");
            passwordField.setText("");
        }
        else{
            Main.IS_ADMIN = currentUser.isAdmin();
            loadStage("main-menu.fxml");
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }

    private void loadStage(String fxml) {
        try {
            System.out.println(Menu.class.getResource(fxml));
            Parent root = FXMLLoader.load(Menu.class.getResource(fxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
