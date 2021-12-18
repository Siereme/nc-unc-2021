package com.gmail.viktor.yuryev.unc2021.shared.model;

import java.io.Serializable;
import java.util.List;

public class Film extends Entity implements Serializable {
    private List<Director> directors;

    public Film(String name, String id) {
        super(name, id);
    }

    @Override
    public String toString() {
        return "Film{" + "name='" + name + '\'' + ", id='" + id + '\'' + ", directors=" + directors + '}';
    }

}
