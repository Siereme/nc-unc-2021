package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.film.Film;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/** Класс репозиторий фильмов
 * @see IRepository
 * @see Film
 * @author Vasiliy, Sergey
 * @version 1.0
 * */
public class FilmsRepository implements IRepository<Film> {
    /** Путь для сериализации\десериализации */
    private final String filePath = new File("src/main/resources/Films.json").getAbsolutePath();

    /** список хранимых фильмов */
    private final List<Film> films = new ArrayList<>();

    public FilmsRepository(Film... newFilms) {
        films.addAll(Arrays.asList(newFilms));
    }

    public FilmsRepository() {
        init();
    }

    public void init() {
        try {
            films.addAll(deserialize(new ObjectMapper()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Film> findAll() {
        return films;
    }

    public boolean deleteById(Integer id) {
        for (Film film : films) {
            if (film.getId().equals(id.toString())) {
                // предикат, надеюсь не ошибся в использовании.
                return films.removeIf(f -> Objects.equals(f.getId(), id.toString()));
            }
        }
        return false;
    }

    @Override
    public boolean findById(Integer Id) {
        // предикат, надеюсь не ошибся в использовании.
        return films.stream().allMatch(f -> Objects.equals(f.getId(), Id.toString()));
    }

    // пока не ясно что должен делать метод, добавлять в репозиторий новый фильм? или создавать новый...
    @Override
    public boolean create(Film entity) {
        return films.add(entity);
    }

    @Override
    public void clear() {
        films.clear();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Film film : films) {
            sb.append(ind).append(". ").append(film.getTittle()).append("\n");
            ++ind;
        }
        return new String(sb);
    }

    public void serialize(ObjectMapper objectMapper) throws IOException {
        objectMapper.writeValue(new FileWriter(this.filePath), this.films);
    }

    public List<Film> deserialize(ObjectMapper objectMapper) throws IOException {
        return objectMapper.readValue(new FileReader(this.filePath), new TypeReference<List<Film>>() {
        });
    }

    public int size() {
        return films.size();
    }


    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public void mergeFiles(String file1, String file2){
        List<Film> films = new LinkedList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            films.addAll(mapper.readValue(new File(file1), new TypeReference<List<Film>>() {}));
            films.addAll(mapper.readValue(new File(file2), new TypeReference<List<Film>>() {}));
            mapper.writeValue(new File(this.filePath),
                    films.stream()
                            .filter(distinctByKey(p -> p.getId()))
                            .collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
