package model.Film;

import model.Genre.Genre;
import model.Genres.Genres;

import java.util.Date;

public class Film {

    private String tittle;
    private Date date;
    private Genres genres;

    public Genres getGenres() {
        return genres;
    }

    public boolean isContainsGenre(Genre genreToFind) {
        for (Genre genre : genres.getGenres()) {
            if (genre.equals(genreToFind)) {
                return true;
            }
        }
        return false;
    }

    public void setGenres(Genres genres) {
        this.genres = genres;
    }

    public Film(String newTittle, Date newDate, Genres newGenres) {
        tittle = newTittle;
        date = newDate;
        genres = newGenres;
    }

    public Film(String newTittle, Date newDate, Genre... newGenres) {
        tittle = newTittle;
        date = newDate;
        genres = new Genres(newGenres);
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Tittle: ").append(tittle).append("\n").append("Date of release: ").append(date).append("\n")
                .append("Genres\n").append(genres.toString());
        return new String(sb);
    }

}
