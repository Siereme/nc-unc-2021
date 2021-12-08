package model.User.Visitor;

import com.fasterxml.jackson.annotation.JsonTypeName;
import model.User.IUser;

import java.util.UUID;

@JsonTypeName("visitor")
public class Visitor implements IUser {
    private String id;
    private String name;
    private String password;
    private final Boolean role = false;

    public Visitor() {
        this.id = UUID.randomUUID().toString();
        this.name = "Unknown";
        this.password = "None";
    }

    public Visitor(String name, String password) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.password = password;
    }

    @Override
    public String getId(){
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
    public Boolean getRole(){
        return this.role;
    }
}
