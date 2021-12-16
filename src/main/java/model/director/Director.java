package model.director;

import model.IEntity;

import java.util.UUID;

/** Director entity
 * @author Vasiliy, Sergey
 * @version 1.0
 * */
public class Director implements IEntity {

    private String id;

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public void setYear(String year) {
        this.year = year;
    }

    private String year;


    public Director(){
        this.id = UUID.randomUUID().toString();
        this.name = "Unknown";
        this.year = "Unknown";
    }

    public Director(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.year = "Unknown";
    }


    public Director(String name, String year) {
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
