package app.viewFX.menu.genres;

import app.model.genre.Genre;
import javafx.scene.control.CheckBox;

public class TableGenre {
    private String id;
    private String tittle;
    private CheckBox checked = new CheckBox();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CheckBox getChecked() {
        return checked;
    }

    public void setChecked(CheckBox checked) {
        this.checked = checked;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    TableGenre(Genre genre) {
        id = genre.getId();
        tittle = genre.getTittle();
    }

}
