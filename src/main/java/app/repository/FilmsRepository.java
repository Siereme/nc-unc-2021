package app.repository;

import app.model.film.Film;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/** Film app.repository
 * @see AbstractRepository
 * @see Film
 * @author Vasiliy, Sergey
 * @version 1.0
 * */
public class FilmsRepository extends AbstractRepository<Film>  {
    /** Путь для сериализации\десериализации */
    public static final String FILM_FILE_PATH = new File("src/main/resources/database/Films.json").getAbsolutePath();


    public FilmsRepository(Film... newFilms) {
        super(FILM_FILE_PATH, Arrays.asList(newFilms));
        entities.addAll(Arrays.asList(newFilms));
    }

    public FilmsRepository(List<Film> newFilms) {
        super(FILM_FILE_PATH, newFilms);
    }

    public FilmsRepository() {
        super(FILM_FILE_PATH);
        init();
    }

    public void init() {
        try {
            entities.addAll(deserialize());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Film> findAll() {
        return entities;
    }

    public boolean deleteById(Integer id) {
        for (Film film : entities) {
            if (film.getId().equals(id.toString())) {
                // предикат, надеюсь не ошибся в использовании.
                return entities.removeIf(f -> Objects.equals(f.getId(), id.toString()));
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Film film : entities) {
            sb.append(ind).append(". ").append(film.getTittle()).append("\n");
            ++ind;
        }
        return new String(sb);
    }

    public int size() {
        return entities.size();
    }

}
