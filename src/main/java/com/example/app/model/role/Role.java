package com.example.app.model.role;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    @Override
    public String getAuthority() {
        return getName();
    }
}
