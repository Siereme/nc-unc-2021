package Genre;

import java.util.Objects;

public class Genre {

    String tittle;

    public Genre(String newGener) {
        tittle = newGener;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    // нужна ли эта гениальная реализация...
    @Override
    public String toString() {
        return tittle;
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
