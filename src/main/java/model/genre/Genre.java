package model.genre;

import model.IEntity;

import java.util.Objects;
import java.util.UUID;

/** Сущность жанр содержит первичный ключ - id, название жанра
 * @author Vasiliy, Sergey
 * */
public class Genre implements IEntity {

    /** Поле первичный ключ - id жанра */
    private String id;

    /** Поле название жанра */
    private String tittle;

    public Genre(){
        id = UUID.randomUUID().toString();
        tittle = "Unknown";
    }

    public Genre(String newGener) {
        id = UUID.randomUUID().toString();
        tittle = newGener;
    }

    public String getId() {
        return this.id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Genre genre = (Genre) o;
        return Objects.equals(tittle, genre.tittle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tittle);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("ID: ").append(id).append("\n");
        sb.append("Tittle: ").append(tittle).append("\n");
        return new String(sb);
    }
}
