package Films;

import Film.Film;
import Genre.Genre;

import java.util.LinkedList;

public class Films {

    LinkedList<Film> films;

    public Films() {
        films = new LinkedList<Film>();
    }

    public boolean addFilm(Film film) {
        return films.add(film);
    }

    public void clear() {
        films.clear();
    }

    public int size() {
        return films.size();
    }

    public Film get(int index) {
        return films.get(index);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Film film : films) {
            sb.append(ind++).append(") ").append(film.toString()).append("\n");
        }
        return new String(sb);
    }

    public Films getFilmsByGenre(Genre genre) {
        Films filmsByGenre = new Films();
        for (Film film : films) {
            if (film.isContainsGenre(genre)) {
                filmsByGenre.addFilm(film);
            }
        }
        return filmsByGenre;
    }

}
