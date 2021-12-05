package repository;

import model.Actor.Actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ActorRepository implements IRepository<Actor> {
    private final List<Actor> actors = new ArrayList<>();

    public ActorRepository() {
        init();
    }

    //todo serrialization
    private void init() {
        actors.add(new Actor("actor1", "10"));
        actors.add(new Actor("actor2", "20"));
    }

    @Override
    public List<Actor> findAll() {
        return actors;
    }

    @Override
    public boolean deleteById(Integer Id) {
        for (Actor actor : actors) {
            if (actor.getId().equals(Id.toString())) {
                // предикат, надеюсь не ошибся в использовании.
                return actors.removeIf(a -> Objects.equals(a.getId(), Id.toString()));
            }
        }
        return false;
    }

    @Override
    public boolean findById(Integer Id) {
        return actors.stream().allMatch(a -> Objects.equals(a.getId(), Id.toString()));
    }

    @Override
    public boolean create(Actor entity) {
        return actors.add(entity);
    }

    @Override
    public void clear() {
        actors.clear();
    }

    @Override
    public String toString() {
        int ind = 0;
        StringBuffer sb = new StringBuffer();
        for (Actor actor : actors) {
            sb.append(ind).append(". ").append(actor.toString()).append("\n");
            ++ind;
        }
        return new String(sb);
    }
}
