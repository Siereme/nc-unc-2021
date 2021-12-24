package dto.controller;

import app.controller.IEntityController;
import app.model.IEntity;

import java.util.LinkedList;

public class EntityController<T extends IEntity> {
    IEntityController<T> entityController;

    public EntityController(IEntityController<T> entityController) {
        this.entityController = entityController;
    }

    public void setEntityController(IEntityController<T> entityController1) {
        entityController = entityController1;
    }

    public T getEntity(String id) {
        return entityController.getEntityById(id);
    }

    public LinkedList<T> getEntitiesByNames(LinkedList<String> names) {
        return entityController.getEntitiesByNames(names);
    }

    public boolean removeEntity(String id){
        return entityController.remove(id);
    }

}
