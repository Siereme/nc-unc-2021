package com.gmail.viktor.yuryev.unc2021.shared.dto;

import com.gmail.viktor.yuryev.unc2021.shared.model.Entity;

public class GetEntityResponse<T extends Entity> extends Response {
    public GetEntityResponse(String name) {
        super(name);
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public GetEntityResponse(String name, T entity) {
        super(name);
        this.entity = entity;
    }

    T entity;

    @Override
    public String toString() {
        return "GetEntityResponse: responce " + entity.toString();
    }

}
