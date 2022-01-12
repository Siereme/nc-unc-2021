package app.viewFX.menu.genres.handle;

import app.model.actor.Actor;
import app.model.director.Director;
import app.model.genre.Genre;
import client.CommunicationInterface;
import dto.request.Request;
import dto.request.imp.AddEntityRequest;
import dto.request.imp.EditEntityRequest;
import dto.request.imp.RemoveEntityRequest;
import dto.request.imp.RequestFilm;
import dto.response.Response;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddEditGenreController extends HandleGenreController {
    public AddEditGenreController() throws IOException {

    }

    @FXML
    private TextField tittle;

    @FXML
    private Button handleButton;

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    private Genre genre;
    // private String oldId;

    private boolean editMode;

    public void init() {
        System.out.println("initialize  " + genre);
        editMode = genre != null;
        System.out.println("editMode  " + editMode);
        if (editMode) {
            initGenre();
        } else {
            initEmpty();
        }
    }

    private void initGenre() {
        this.tittle.setText(genre.getTittle());
        // oldId = genre.getId();
    }

    private void initEmpty() {
        this.tittle.setText(" ");
    }

    protected void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private Genre createGenre() {
        String genreTittle = this.tittle.getText();
        return new Genre(genreTittle);
    }

    public void handleGenre(ActionEvent event) {
        Genre genre = createGenre();
        if (editMode) {
            String removeEntityId = this.genre.getId();
            Request removeRequest = new RemoveEntityRequest(removeEntityId, this.genre.getClass());
            try {
                Response response = CommunicationInterface.getInstance().exchange(removeRequest);
                System.out.println(response);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        Request addRequest = new AddEntityRequest(genre, Genre.class);
        try {
            Response response =
                    CommunicationInterface.getInstance().exchange(addRequest);
            System.out.println(response);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        closeStage(event);
    }

}
