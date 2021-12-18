package com.gmail.viktor.yuryev.unc2021.shared.dto;

import com.gmail.viktor.yuryev.unc2021.shared.model.Entity;

public class CreateEntityRequest<T extends Entity> extends Request {
    public CreateEntityRequest(String name) {
        super(name);
    }

    public CreateEntityRequest(String name, T entity) {
        super(name);
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "CreateEntityRequest: request " + entity.toString();
    }

    T entity;
}
