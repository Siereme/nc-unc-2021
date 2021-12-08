package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Actor.Actor;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ActorRepository implements IRepository<Actor> {
    private final String filePath = "src/main/resources/Actors.json";
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
        String file = new File(this.filePath).getAbsolutePath();
        objectMapper.writeValue(new FileWriter(file), this.actors);
    }

    public List<Actor> deserialize(ObjectMapper objectMapper) throws IOException {
        String file = new File(this.filePath).getAbsolutePath();
        return objectMapper.readValue(new FileReader(file), new TypeReference<List<Actor>>() {
        });
    }

    public int size() {
        return actors.size();
    }

}
