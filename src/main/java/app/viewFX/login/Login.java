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

public class Login extends Main{
    @FXML private Label status;
    @FXML private TextField loginField;
    @FXML private TextField passwordField;

    @FXML
    protected void login(ActionEvent event){
        UserController userController = new UserController();
        IUser currentUser = userController.getEntityByLogin(loginField.getText(), passwordField.getText());
        if(currentUser == null){
            status.setText("Incorrect login or password");
            loginField.setText("");
            passwordField.setText("");
        }
        else{
            Main.IS_ADMIN = currentUser.isAdmin();
            closeCurrentStage(event);
            loadStage(Menu.class, "main-menu.fxml");
        }
    }
}
