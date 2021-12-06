package controller;

import model.Genre.Genre;
import repository.GenreRepository;

import java.util.LinkedList;
import java.util.Objects;

public class GenreController {

    public GenreRepository getGenreRepository() {
        return genreRepository;
    }

    GenreRepository genreRepository = new GenreRepository();

    public GenreController() {
        genreRepository = new GenreRepository();
    }

/*    public static String tittlesToString(LinkedList<Genre> genres) {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Genre genre : genres) {
            sb.append(ind).append(". ").append(genre.getTittle()).append("\n");
            ind++;
        }
        return new String(sb);
    }*/

    Genre genreById(String id) {
        for (Genre genre : genreRepository.findAll()) {
            if (Objects.equals(genre.getId(), id)) {
                return genre;
            }
        }
        return null;
    }

    public String genreToString(Genre genre) {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ").append(genre.getId()).append("\n");
        sb.append("Tittle: ").append(genre.getTittle()).append("\n");
        return new String(sb);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Genre genre : genreRepository.findAll()) {
            sb.append(ind).append(". ").append(genreToString(genre)).append("\n");
            ++ind;
        }
        return new String(sb);
    }

    public String genresByIdToString(LinkedList<String> genres) {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (String s : genres) {
            sb.append(ind++).append(". ").append(genreToString(genreById(s))).append("\n");
        }
        return new String(sb);
    }

}
