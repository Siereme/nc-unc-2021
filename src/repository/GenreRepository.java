package repository;

import model.Genre.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GenreRepository implements IRepository<Genre> {
    private final List<Genre> genres = new ArrayList<>();

    public GenreRepository() {
        init();
    }

    //todo serrialization
    private void init() {
        genres.add(new Genre("genre1"));
        genres.add(new Genre("genre2"));
    }

    @Override
    public List<Genre> findAll() {
        return genres;
    }

    @Override
    public boolean deleteById(Integer Id) {
        for (Genre genre : genres) {
            if (genre.getId().equals(Id.toString())) {
                // предикат, надеюсь не ошибся в использовании.
                return genres.removeIf(g -> Objects.equals(g.getId(), Id.toString()));
            }
        }
        return false;
    }

    @Override
    public boolean findById(Integer Id) {
        return genres.stream().allMatch(g -> Objects.equals(g.getId(), Id.toString()));
    }

    @Override
    public boolean create(Genre entity) {
        return genres.add(entity);
    }

    @Override
    public void clear() {
        genres.clear();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Genre genre : genres) {
            sb.append(ind).append(". ").append(genre).append("\n");
            ++ind;
        }
        return new String(sb);
    }
}
