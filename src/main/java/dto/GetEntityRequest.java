package dto;

import model.IEntity;

public class GetEntityRequest<T extends IEntity> extends Request {
    public GetEntityRequest(String name) {
        super(name);
    }

    public GetEntityRequest(String name, String id) {
        super(name);
        this.id = id;
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GetEntityRequest: id " + id;
    }
}
