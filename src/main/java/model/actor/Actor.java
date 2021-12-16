package model.actor;

import model.IEntity;

import java.util.UUID;

/** Actor entity
 * @author Vasiliy, Sergey
 * @version 1.0
 * */
public class Actor implements IEntity {

    private String id;

    private String name;

    private String year;

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public Actor(){
        this.id = UUID.randomUUID().toString();
        this.name = "Unknown";
        this.year = "Unknown";
    }

    public Actor(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.year = "Unknown";
    }

    public Actor(String name, String year) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.year = year;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getYear() {
        return this.year;
    }


}
