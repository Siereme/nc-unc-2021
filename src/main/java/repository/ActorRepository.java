package repository;

import model.actor.Actor;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/** Класс репозиторий, хранящий всех актеров
 * @see IRepository
 * @see Actor
 * @author Vasiliy,Sergey
 * @version 1.0
 * */
public class ActorRepository extends AbstractRepository<Actor> {
    /** Поле путь файла, из которого будет десериализован/сериализован репозиторий */
    public static final String ACTOR_FILE_PATH = new File("src/main/resources/Actors.json").getAbsolutePath();


    public ActorRepository(Actor... actors) {
        super(ACTOR_FILE_PATH, Arrays.asList(actors));
        this.entities.addAll(Arrays.asList(actors));
    }

    public ActorRepository() {
        super(ACTOR_FILE_PATH);
        init();
    }

    public void init() {
        try {
            entities.addAll(deserialize());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Actor> findAll() {
        return entities;
    }

/*    @Override
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
    }*/

/*    @Override
    public boolean create(Actor entity) {
        return actors.add(entity);
    }

    @Override
    public void clear() {
        actors.clear();
    }*/


    @Override
    public String toString() {
        int ind = 0;
        StringBuffer sb = new StringBuffer();
        for (Actor actor : entities) {
            sb.append(ind).append(". ").append(actor.toString()).append("\n");
            ++ind;
        }
        return new String(sb);
    }


    public int size() {
        return entities.size();
    }
}
