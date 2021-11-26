package Films;

import Film.Film;

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
        for (int i = 0; i < films.size(); ++i) {
            sb.append(i).append(") ").append(films.get(i).getTittle()).append("\n");
        }
        return new String(sb);
    }

/*    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        int count = 0;
        for (Film f : films) {
            sb.append("Film ").append(count++).append(f.toString()).append("\n");
        }
        return new String(sb);
    }*/

}
