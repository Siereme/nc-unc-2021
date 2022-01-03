package app.viewFX.menu;

import app.model.IEntity;
import client.CommunicationInterface;
import dto.request.imp.GetEntitiesByNamesRequest;
import dto.request.imp.GetEntityRequest;
import dto.response.imp.GetEntitiesResponse;
import dto.response.imp.GetEntityResponse;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class AbstractController<T extends IEntity> {
    private Stage stage;

    public AbstractController() {
    }
    public void init() {}


    protected List<T> getEntitiesByNames(List<String> names, Class<T> entityType) {
        GetEntitiesByNamesRequest entitiesRequest = new GetEntitiesByNamesRequest(names, entityType);
        LinkedList<T> entities = new LinkedList<>();
        try {
            final CommunicationInterface instance = CommunicationInterface.getInstance();
            GetEntitiesResponse entitiesResponse = (GetEntitiesResponse) instance.exchange(entitiesRequest);
            List<T> entityList = (List<T>) entitiesResponse.getEntities();
            entities.addAll(entityList);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return entities;
    }

    protected List<String> getEntitiesIdsByNames(List<String> names, Class<T> entityType) {
        List<String> entityIds = new LinkedList<>();
        if (names.size() > 0) {
            List<T> entityList = getEntitiesByNames(names, entityType);
            List<String> strings = new LinkedList<>();
            for (T t : entityList) {
                String id = t.getId();
                strings.add(id);
            }
            entityIds.addAll(strings);
        }
        return entityIds;
    }

    protected List<T> getEntitiesByIds(List<String> ids, Class<T> entityType) {
        LinkedList<T> entities = new LinkedList<>();
        for (String id : ids) {
            GetEntityRequest entityRequest = new GetEntityRequest(id, entityType);
            try {
                GetEntityResponse<T> entityResponse = (GetEntityResponse<T>) CommunicationInterface.getInstance().exchange(entityRequest);
                T entity = entityResponse.getEntity();
                entities.add(entity);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return entities;
    }

    protected AbstractController initializeWindowController(Stage ownerStage, String path, String tittle) {
        Stage stage1 = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource(path));
            Parent parent = loader.load();
            AbstractController controller = loader.getController();
            stage1.setScene(new Scene(parent));
            stage1.initModality(Modality.APPLICATION_MODAL);
            controller.setStage(stage1);
            stage1.initOwner(ownerStage);
            stage1.setTitle(tittle);
            return controller;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Stage getStage() {
        return this.stage;
    }

    protected void setStage(Stage stage) {
        this.stage = stage;
    }

}