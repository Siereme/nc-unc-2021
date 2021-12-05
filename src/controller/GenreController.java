package controller;

import model.Genre.Genre;
import repository.GenreRepository;

import java.util.LinkedList;

public class GenreController {

    public GenreRepository getGenreRepository() {
        return genreRepository;
    }

    GenreRepository genreRepository = new GenreRepository();

    public GenreController() {
        genreRepository = new GenreRepository();
    }

    public static String tittlesToString(LinkedList<Genre> genres) {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Genre genre : genres) {
            sb.append(ind).append(". ").append(genre.getTittle()).append("\n");
            ind++;
        }
        return new String(sb);
    }

}
