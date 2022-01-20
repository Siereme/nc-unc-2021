package com.example.app.model.user.Admin;

import com.example.app.model.user.IUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/** Administrator entity
 * @author Sergey
 * @version 1.0
 * */
@JsonTypeName("admin")
public class Admin  {
    private int id;

    private String name;

    private String password;

    @JsonProperty("isAdmin")
    private final Boolean isAdmin = true;

    public Admin() {
        this.name = "";
        this.password = "";
    }

    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public int getId(){
        return this.id;
    }

    public String getUsername() {
        return this.name;
    }

    public void setUsername(String username) {
        this.name = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("isAdmin")
    public Boolean isAdmin(){
        return this.isAdmin;
    }

}
