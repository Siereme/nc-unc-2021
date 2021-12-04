package model.Genre;

import java.util.Objects;
import java.util.UUID;

public class Genre {

    private String id;
    private String tittle;

    public Genre(String newGener) {
        id = UUID.randomUUID().toString();
        tittle = newGener;
    }

    public String getId(){return this.id;}

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
}
