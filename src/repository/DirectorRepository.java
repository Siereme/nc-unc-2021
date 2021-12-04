package repository;

import model.Director.Director;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DirectorRepository implements IRepository<Director> {
    private final List<Director> directors = new ArrayList<>();

    public DirectorRepository(){
        init();
    }

    //todo serrialization
    private void init() {

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
}
