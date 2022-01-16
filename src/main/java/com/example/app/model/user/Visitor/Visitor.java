package com.example.app.model.user.Visitor;

import com.example.app.model.IEntity;
import com.example.app.model.user.IUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.UUID;

/** Visitor entity
 * privileges are less than those of the admin
 * @author Sergey
 * @version 1.0
 * */
@JsonTypeName("visitor")
public class Visitor implements IUser, IEntity {
    /** Поле первичный ключ - id пользователя */
    private int id;

    /** Поле имя пользователя */
    private String name;

    /** Поле пароль пользователя */
    private String password;

    /** Поле роль пользователя */
    @JsonProperty("isAdmin")
    private final Boolean isAdmin = false;

    public Visitor() {
        this.name = "";
        this.password = "";
    }

    public Visitor(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public int getId(){
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    @JsonProperty("isAdmin")
    public Boolean isAdmin(){
        return this.isAdmin;
    }
}
