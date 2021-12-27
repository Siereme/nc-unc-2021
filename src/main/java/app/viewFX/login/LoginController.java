package app.viewFX.login;

import app.model.user.IUser;
import client.CommunicationInterface;
import dto.request.imp.AuthorizationRequest;
import dto.response.imp.GetAuthorizationResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private Label status;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;

    public LoginController() throws IOException {
    }

    // Main main; // нужно хранить родителя...

    @FXML
    protected void login(ActionEvent event) throws IOException, ClassNotFoundException {
        AuthorizationRequest authorizationRequest =
                new AuthorizationRequest(loginField.getText(), passwordField.getText());

        GetAuthorizationResponse response =
                (GetAuthorizationResponse) CommunicationInterface.getInstance().exchange(authorizationRequest);

        IUser currentUser = response.getUser();

        if(currentUser == null){
            status.setText("Incorrect login or password");
            loginField.setText("");
            passwordField.setText("");
        }
        else{
         // TODO?   setCurrentUser(currentUser);
            closeStage(event);
        }
    }
    protected void closeStage(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
