package repository;

import model.actor.Actor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/** Класс репозиторий, хранящий всех актеров
 * @see IRepository
 * @see Actor
 * @author Vasiliy,Sergey
 * @version 1.0
 * */
public class ActorRepository extends AbstractRepository<Actor> implements IRepository<Actor> {
    /** Поле путь файла, из которого будет десериализован/сериализован репозиторий */
    public static final String ACTOR_FILE_PATH = new File("src/main/resources/Actors.json").getAbsolutePath();

    /** Поле - список актеров, которые будут храниться в репозитории */
    private final List<Actor> actors = new ArrayList<>();

    public ActorRepository(Actor... actors) {
        super(ACTOR_FILE_PATH, Arrays.asList(actors));
        this.actors.addAll(Arrays.asList(actors));
    }

    public ActorRepository() {
        super(ACTOR_FILE_PATH);
        init();
    }

    public void init() {
        try {
            actors.addAll(deserialize());
        } catch (IOException e) {
            e.printStackTrace();
        }
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


    public int size() {
        return actors.size();
    }
}
