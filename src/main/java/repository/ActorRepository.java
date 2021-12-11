package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import model.Actor.Actor;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/** Класс репозиторий, хранящий всех актеров
 * @see IRepository
 * @see Actor
 * @author Vasiliy,Sergey
 * @version 1.0
 * */
public class ActorRepository implements IRepository<Actor> {
    /** Поле путь файла, из которого будет десериализован/сериализован репозиторий */
    private final String filePath = new File("src/main/resources/Actors.json").getAbsolutePath();

    /** Поле - список актеров, которые будут храниться в репозитории */
    private final List<Actor> actors = new ArrayList<>();

    public ActorRepository(Actor... actors) {
        this.actors.addAll(Arrays.asList(actors));
    }

    public ActorRepository() {
        init();
    }

    public void init() {
        try {
            actors.addAll(deserialize(new ObjectMapper()));
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

    public void serialize(ObjectMapper objectMapper) throws IOException {
        objectMapper.writeValue(new FileWriter(this.filePath), this.actors);
    }

    public List<Actor> deserialize(ObjectMapper objectMapper) throws IOException {
        try{
            return objectMapper.readValue(new FileReader(this.filePath), new TypeReference<List<Actor>>() {});
        }catch (MismatchedInputException e){
            System.out.println("File is empty");
            return this.actors;
        }
    }

    public int size() {
        return actors.size();
    }

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public void mergeFiles(String file1, String file2){
        List<Actor> actors = new LinkedList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            actors.addAll(mapper.readValue(new File(file1), new TypeReference<List<Actor>>() {}));
            actors.addAll(mapper.readValue(new File(file2), new TypeReference<List<Actor>>() {}));
            mapper.writeValue(new File(this.filePath),
                    actors.stream()
                            .filter(distinctByKey(p -> p.getId()))
                            .collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
