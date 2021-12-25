package dto.request;

import app.model.IEntity;
import app.model.film.Film;

public class EditEntityRequest extends Request{
    public EditEntityRequest(IEntity entity) {
        setEntityType(Film.class);
        this.entityType = entity.getClass();
        this.entity = entity;
    }

    private final IEntity entity;

    public IEntity getEntity() {
        return entity;
    }
}
