package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Director.Director;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DirectorRepository implements repository.IRepository<Director> {
    private final String filePath = "src/main/resources/Directors.json";
    private final List<Director> directors = new ArrayList<>();

    public DirectorRepository(Director... directors) {
        this.directors.addAll(Arrays.asList(directors));
    }

    public DirectorRepository() {
        init();
    }

    public void init() {
        try {
            directors.addAll(deserialize(new ObjectMapper()));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void serialize(ObjectMapper objectMapper) throws IOException {
        String file = new File(this.filePath).getAbsolutePath();
        objectMapper.writeValue(new FileWriter(file), this.directors);
    }

    public List<Director> deserialize(ObjectMapper objectMapper) throws IOException {
        String file = new File(this.filePath).getAbsolutePath();
        return objectMapper.readValue(new FileReader(file), new TypeReference<List<Director>>() {
        });
    }

    public int size() {
        return directors.size();
    }


}
