package app.viewFX.menu.directors.handle;

import app.model.director.Director;
import app.model.film.Film;
import client.CommunicationInterface;
import dto.request.Request;
import dto.request.imp.AddEntityRequest;
import dto.request.imp.EditEntityRequest;
import dto.response.Response;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class AddEditDirectorController extends HandleDirectorController{
    public AddEditDirectorController(){

    }

    @FXML
    private TextField nameField;

    @FXML
    private TextField ageField;

    @FXML
    private ListView<TextField> filmsListView;

    @FXML
    private Button handleButton;

    public void setDirector(Director director) {
        this.director = director;
    }

    private Director director;
    private boolean editMode;

    public void init() {
        System.out.println("initialize  " + director);
        editMode = director != null;
        System.out.println("editMode  " + editMode);
        if (editMode) {
            initDirector();
        } else {
            initEmpty();
        }
    }

    private void initEmpty() {
        for (int i = 0; i < 6; i++) {
            this.filmsListView.getItems().add(new TextField());
        }
    }

    private void initDirector() {
        String age = director.getYear();
        ageField.setText(age);
        String name = director.getName();
        nameField.setText(name);
        List<String> filmIds = director.getFilms();
        List<Film> filmList = getEntitiesByIds(filmIds, Film.class);
        List<TextField> textFieldList = new ArrayList<>();
        for (Film film : filmList) {
            String tittle = film.getTittle();
            textFieldList.add(new TextField(tittle));
        }
        while (textFieldList.size() < 6) {
            textFieldList.add(new TextField());
        }
        ObservableList<TextField> observableList = FXCollections.observableList(textFieldList);
        filmsListView.setItems(observableList);

    }

    protected void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private Director createDirector() {
        List<String> filmNames = filmsListView.getItems().stream().map(TextField::getText).filter(text -> text.length() > 0)
                .collect(Collectors.toCollection(LinkedList::new));
        List<String> filmIds = getEntitiesIdsByNames(filmNames, Film.class);
        String directorName = nameField.getText();
        String directorAge = ageField.getText();
        Director director = new Director(directorName, directorAge);
        director.setFilms(filmIds);
        return director;
    }

    public void handleDirector(ActionEvent actionEvent) {
        Director director = createDirector();
        if (editMode) {
            Request editRequest = new EditEntityRequest(director, Director.class);
            try {
                Response response = CommunicationInterface.getInstance().exchange(editRequest);
                System.out.println(response);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Request addRequest = new AddEntityRequest(director, Director.class);
            try {
                Response response = CommunicationInterface.getInstance().exchange(addRequest);
                System.out.println(response);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        closeStage(actionEvent);
    }
}
