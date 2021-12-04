package repository;

import model.Film.Film;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

//TODO will be replaced by DB later
public class FilmsRepository implements IRepository<Film> {
    private final List<Film> films = new ArrayList<>();

    public FilmsRepository(Film... newFilms) {
        films.addAll(Arrays.asList(newFilms));
    }

    public FilmsRepository(){
        init();
    }

    //todo serrialization
    private void init() {

    }

    public List<Film> findAll() {
        return films;
    }

    public boolean deleteById(Integer id) {
        for (Film film : films) {
            if (film.getId().equals(id.toString())) {
                // предикат, надеюсь не ошибся в использовании.
                return films.removeIf(f -> Objects.equals(f.getId(), id.toString()));
            }
        }
        return false;
    }

    @Override
    public boolean findById(Integer Id) {
        // предикат, надеюсь не ошибся в использовании.
        return films.stream().allMatch(f -> Objects.equals(f.getId(), Id.toString()));
    }

    // пока не ясно что должен делать метод, добавлять в репозиторий новый фильм? или создавать новый...
    @Override
    public boolean create(Film entity) {
        return films.add(entity);
    }

    @Override
    public void clear() {
        films.clear();
    }

}
