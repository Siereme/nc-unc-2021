package model.Genres;

import model.Genre.Genre;

import java.util.Collections;
import java.util.LinkedList;

public class Genres implements Cloneable {

    public LinkedList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(LinkedList<Genre> genres) {
        this.genres = genres;
    }

    LinkedList<Genre> genres;

    @Override
    public Object clone() {
        Genres copy = new Genres();
        copy.genres.addAll(this.genres);
        return copy;
    }

    public Genres() {
        genres = new LinkedList<>();
    }

    public Genres(String... tittles) {
        genres = new LinkedList<>();
        for (String s : tittles) {
            genres.add(new Genre(s));
        }

    }

    public Genres(Genre... newGenres) {
        genres = new LinkedList<>();
        Collections.addAll(genres, newGenres);
    }

    public Genre get(int index) {
        return genres.get(index);
    }

    // странная реализация
    public Genre remove(int index) {
        return genres.remove(index);
    }

    public boolean addGenre(Genre genre) {
        return genres.add(genre);
    }

    public void clear() {
        genres.clear();
    }

    public int size() {
        return genres.size();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Genre genre : genres) {
            sb.append(ind++).append(") ").append(genre.toString()).append("\n");
        }
        return new String(sb);
    }

}
