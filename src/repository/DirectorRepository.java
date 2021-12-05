package repository;

import model.Director.Director;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DirectorRepository implements IRepository<Director> {
    private final List<Director> directors = new ArrayList<>();

    public DirectorRepository() {
        init();
    }

    //todo serrialization
    private void init() {
        directors.add(new Director("director1", "15"));
        directors.add(new Director("director2", "25"));
    }

    @Override
    public List<Director> findAll() {
        return directors;
    }

    @Override
    public boolean deleteById(Integer Id) {
        for (Director director : directors) {
            if (director.getId().equals(Id.toString())) {
                // предикат, надеюсь не ошибся в использовании.
                return directors.removeIf(d -> Objects.equals(d.getId(), Id.toString()));
            }
        }
        return false;
    }

    @Override
    public boolean findById(Integer Id) {
        return directors.stream().allMatch(d -> Objects.equals(d.getId(), Id.toString()));
    }

    @Override
    public boolean create(Director entity) {
        return directors.add(entity);
    }

    @Override
    public void clear() {
        directors.clear();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Director director : directors) {
            sb.append(ind).append(". ").append(director).append("\n");
            ++ind;
        }
        return new String(sb);
    }
}
