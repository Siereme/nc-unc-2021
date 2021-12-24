package dto.request;


import app.model.IEntity;

import java.io.Serializable;

public class Request implements Serializable {
    protected Class<? extends IEntity> entityType;

    public void setEntityType(Class<? extends IEntity> entityType) {
        this.entityType = entityType;
    }

    public Class<? extends IEntity> getEntityType() {
        return entityType;
    }
}
