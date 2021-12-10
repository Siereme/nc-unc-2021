package model.User.Visitor;

import com.fasterxml.jackson.annotation.JsonTypeName;
import model.User.IUser;

import java.util.UUID;

/** Класс пользователь(привилегий меньше чем у админа)
 * @author Sergey
 * @version 1.0
 * */
@JsonTypeName("visitor")
public class Visitor implements IUser {
    /** Поле первичный ключ - id пользователя */
    private String id;

    /** Поле имя пользователя */
    private String name;

    /** Поле пароль пользователя */
    private String password;

    /** Поле роль пользователя */
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
