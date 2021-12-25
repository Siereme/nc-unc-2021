package app.viewFX.login;

import app.model.user.IUser;
import app.viewFX.Main;
import dto.request.AuthorizationRequest;
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
            Main.CURRENT_USER = currentUser;
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }
}
