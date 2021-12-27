package app.viewFX.menu;

import app.model.IEntity;
import app.viewFX.Main;
import dto.request.imp.GetEntitiesByNamesRequest;
import dto.request.imp.GetEntityRequest;
import dto.response.imp.GetEntitiesByNamesResponse;
import dto.response.imp.GetEntityResponse;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Menu <T extends IEntity> extends Main {
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

    protected <T extends IEntity> LinkedList<String> getEntitiesIdsByNames(LinkedList<String> names, Class<T> entityType){
        LinkedList<String> entityIds = new LinkedList<>();
        if(names.size() > 0){
            LinkedList<T> entityList = getEntitiesByNames(names, entityType);
            if(entityList.size() > 0){
                LinkedList<String> strings = new LinkedList<>();
                for (T t : entityList) {
                    String id = t.getId();
                    strings.add(id);
                }
                entityIds.addAll(strings);
            }
        }
        return entityIds;
    }

    protected <T extends IEntity> LinkedList<T> getEntitiesByIds(List<String> ids, Class<T> entityType){
        LinkedList<T> entities = new LinkedList<>();
        for (String id : ids){
            GetEntityRequest entityRequest = new GetEntityRequest(id, entityType);
            try {
                GetEntityResponse<T> entityResponse = (GetEntityResponse<T>) communicationInterface.exchange(entityRequest);
                T entity = entityResponse.getEntity();
                entities.add(entity);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return entities;
    }
}