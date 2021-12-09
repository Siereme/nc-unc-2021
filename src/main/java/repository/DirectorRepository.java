package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Director.Director;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DirectorRepository implements repository.IRepository<Director> {
    private final String filePath = new File("src/main/resources/Directors.json").getAbsolutePath();
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
        objectMapper.writeValue(new FileWriter(this.filePath), this.directors);
    }

    public List<Director> deserialize(ObjectMapper objectMapper) throws IOException {
        return objectMapper.readValue(new FileReader(this.filePath), new TypeReference<List<Director>>() {
        });
    }

    public int size() {
        return directors.size();
    }

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public void mergeFiles(String file1, String file2){
        List<Director> directors = new LinkedList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            directors.addAll(mapper.readValue(new File(file1), new TypeReference<List<Director>>() {}));
            directors.addAll(mapper.readValue(new File(file2), new TypeReference<List<Director>>() {}));
            mapper.writeValue(new File(this.filePath),
                    directors.stream()
                            .filter(distinctByKey(p -> p.getId()))
                            .collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
