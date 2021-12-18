package com.gmail.viktor.yuryev.unc2021.shared.dto;

import com.gmail.viktor.yuryev.unc2021.shared.model.Entity;

public class GetEntityRequest<T extends Entity> extends Request {
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
