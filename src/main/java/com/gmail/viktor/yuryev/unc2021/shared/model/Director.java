package com.gmail.viktor.yuryev.unc2021.shared.model;

import java.io.Serializable;

public class Director extends Entity implements Serializable {
    @Override
    public String toString() {
        return "Director{" + "name='" + name + '\'' + ", id='" + id + '\'' + '}';
    }

    public Director(String name, String id) {
        super(name, id);
    }
}
