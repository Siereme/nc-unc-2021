package dto.request.imp;

import app.model.IEntity;
import app.model.film.Film;
import dto.request.Request;

public class AddEntityRequest extends Request {
    public IEntity getEntity() {
        return entity;
    }

    private final IEntity entity;

    public AddEntityRequest(IEntity entity, Class<? extends IEntity> entityType) {
        setEntityType(entityType);
        this.entity = entity;
    }
}
