package model.user.Admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import model.user.IUser;

import java.util.UUID;

/** Administrator entity
 * @author Sergey
 * @version 1.0
 * */
@JsonTypeName("admin")
public class Admin implements IUser {
    private String id;

    private String name;

    private String password;

    @JsonProperty("isAdmin")
    private final Boolean isAdmin = true;

    public Admin() {
        this.id = UUID.randomUUID().toString();
        this.name = "Unknown";
        this.password = "None";
    }

    public Admin(String name, String password) {
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
    @JsonProperty("isAdmin")
    public Boolean isAdmin(){
        return this.isAdmin;
    }

}
