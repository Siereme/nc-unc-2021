package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Genre.Genre;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/** Репозиторий жанров
 * @see IRepository
 * @see Genre
 * @author Vasiliy,Sergey
 * @version 1.0
 * */
public class GenreRepository implements IRepository<Genre> {
    /** Путь для сериализации\десериализации */
    private final String filePath = new File("src/main/resources/Genres.json").getAbsolutePath();

    /** Список хранимых жанров */
    private final List<Genre> genres = new ArrayList<>();

    public GenreRepository(Genre... genres) {
        this.genres.addAll(Arrays.asList(genres));
    }

    public GenreRepository() {
        init();
    }

    public void init() {
        try {
            genres.addAll(deserialize(new ObjectMapper()));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void serialize(ObjectMapper objectMapper) throws IOException {
        objectMapper.writeValue(new FileWriter(this.filePath), this.genres);
    }

    public List<Genre> deserialize(ObjectMapper objectMapper) throws IOException {
        return objectMapper.readValue(new FileReader(this.filePath), new TypeReference<List<Genre>>() {
        });
    }

    public int size() {
        return genres.size();
    }

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public void mergeFiles(String file1, String file2){
        List<Genre> genres = new LinkedList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            genres.addAll(mapper.readValue(new File(file1), new TypeReference<List<Genre>>() {}));
            genres.addAll(mapper.readValue(new File(file2), new TypeReference<List<Genre>>() {}));
            mapper.writeValue(new File(this.filePath),
                    genres.stream()
                            .filter(distinctByKey(p -> p.getId()))
                            .collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
