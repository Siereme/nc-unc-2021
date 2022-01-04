package app.viewFX.menu.actors;

import app.model.actor.Actor;
import app.model.film.Film;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;

import java.util.List;

public class TableActor {
    public String getId() {
        return id;
    }

    private final String id;

    public CheckBox getChecked() {
        return checked;
    }

    public void setChecked(CheckBox checked) {
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public ListView<String> getFilms() {
        return films;
    }

    public void setFilms(ListView<String> films) {
        this.films = films;
    }

    private CheckBox checked = new CheckBox();
    private String name;
    private String age;
    private ListView<String> films = new ListView<>();

    public TableActor(Actor actor, List<Film> filmList) {
        id = actor.getId();
        name = actor.getName();
        age = actor.getYear();
        filmList.forEach(film -> this.films.getItems().add("Film Tittle : " + film.getTittle()));
    }

}
