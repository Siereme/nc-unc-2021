package app.viewFX.login;

import app.model.user.IUser;
import app.viewFX.Main;
import dto.request.imp.AuthorizationRequest;
import dto.response.imp.GetAuthorizationResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
        AuthorizationRequest authorizationRequest =
                new AuthorizationRequest(loginField.getText(), passwordField.getText());

        GetAuthorizationResponse response =
                (GetAuthorizationResponse) communicationInterface.exchange(authorizationRequest);

        IUser currentUser = response.getUser();

        if(currentUser == null){
            status.setText("Incorrect login or password");
            loginField.setText("");
            passwordField.setText("");
        }
        else{
            setCurrentUser(currentUser);
            closeStage(event);
        }
    }
}
