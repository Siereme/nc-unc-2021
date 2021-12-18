package com.gmail.viktor.yuryev.unc2021.shared.model;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    protected String name;
    protected String id;

    public Entity() {

    }

    public Entity(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
