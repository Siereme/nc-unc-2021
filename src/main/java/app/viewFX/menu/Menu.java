package app.viewFX.menu;

import app.model.IEntity;
import app.model.genre.Genre;
import app.viewFX.Main;
import client.CommunicationInterface;
import dto.request.GetEntitiesByNamesRequest;
import dto.request.GetEntityRequest;
import dto.response.GetEntitiesByNamesResponse;
import dto.response.GetEntityResponse;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Menu extends Main {
    public Menu() throws IOException {
    }


    protected <T extends IEntity> LinkedList<T> getEntitiesByNames(LinkedList<String> names, Class<T> entityType){
        GetEntitiesByNamesRequest entitiesRequest = new GetEntitiesByNamesRequest(names, entityType);
        LinkedList<T> entities = new LinkedList<>();
        try {
            GetEntitiesByNamesResponse entitiesResponse = (GetEntitiesByNamesResponse) communicationInterface.exchange(entitiesRequest);
            LinkedList<T> entityList = (LinkedList<T>) entitiesResponse.getEntities();
            entities.addAll(entityList);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return entities;
    }

    protected <T extends IEntity> LinkedList<T> getEntitiesByIds(List<String> ids, Class<T> entityType){
        LinkedList<T> entities = new LinkedList<>();
        for (String id : ids){
            GetEntityRequest entityRequest = new GetEntityRequest(id, entityType);
            try {
                GetEntityResponse entityResponse = (GetEntityResponse) communicationInterface.exchange(entityRequest);
                T entity = (T) entityResponse.getEntity();
                entities.add(entity);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return entities;
    }
}