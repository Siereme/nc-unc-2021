package com.example.app.model.genre;


import com.example.app.model.IEntity;

import java.util.Objects;
import java.util.UUID;

/** Genre entity
 * @author Vasiliy, Sergey
 * */
public class Genre implements IEntity {

    private int id;

    private String tittle;

    public Genre(){
        tittle = "";
    }

    public Genre(int id, String newGener) {
        this.id = id;
        tittle = newGener;
    }

    public Genre(int id, String newGener) {
        this.id = id;
        tittle = newGener;
    }

    public int getId() {
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
