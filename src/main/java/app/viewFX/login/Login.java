package app.viewFX.login;

import app.controller.UserController;
import app.model.user.IUser;
import app.repository.UserRepository;
import app.viewFX.Main;
import app.viewFX.menu.Menu;
import dto.request.CreateAuthorizationRequest;
import dto.response.GetAuthorizationResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Login extends Main {
    @FXML
    private Label status;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;

    public Login() throws IOException {
    }
    // Main main; // нужно хранить родителя...

    @FXML
    protected void login(ActionEvent event) throws IOException, ClassNotFoundException {
        CreateAuthorizationRequest authorizationRequest =
                new CreateAuthorizationRequest("request", loginField.getText(), passwordField.getText());

        GetAuthorizationResponse response =
                (GetAuthorizationResponse) communicationInterface.exchange(authorizationRequest);

        IUser currentUser = response.getUser();

//        IUser currentUser = new UserController().getEntityByLogin(loginField.getText(), passwordField.getText());

        if(currentUser == null){
            status.setText("Incorrect login or password");
            loginField.setText("");
            passwordField.setText("");
        }
        else{
            Main.CURRENT_USER = currentUser;
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
//            closeCurrentStage(event);
//            loadStage(Menu.class, "main-menu.fxml");
        }
        /*main.setName(loginField.getText());
        main.setPassword(passwordField.getText());*/
    }
}
