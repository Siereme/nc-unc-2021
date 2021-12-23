package dto.controller;

import app.controller.IEntityController;
import app.model.IEntity;

import java.util.LinkedList;

public class GetEntityController<T extends IEntity> {
    IEntityController<T> entityController;

    public T getEntity(String id) {
        return entityController.getEntityById(id);
    }

    public LinkedList<T> getEntitiesByNames(LinkedList<String> names) {
        return entityController.getEntitiesByNames(names);
    }

}
