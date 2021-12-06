package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Film.Film;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

//TODO will be replaced by DB later
public class FilmsRepository implements IRepository<Film> {
    private final String filePath = "src/main/resources/Films.json";
    private final List<Film> films = new ArrayList<>();

    public FilmsRepository(Film... newFilms) {
        films.addAll(Arrays.asList(newFilms));
    }

    public FilmsRepository() {
        init();
    }

    //todo serrialization
    private void init() {
        films.add(new Film("film1", new Date(), new LinkedList<>(), new LinkedList<>(), new LinkedList<>()));
        films.add(new Film("film2", new Date(), new LinkedList<>(), new LinkedList<>(), new LinkedList<>()));
        try {
            serialize(new ObjectMapper());
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
        String file = new File(this.filePath).getAbsolutePath();
        objectMapper.writeValue(new FileWriter(file), this.films);
    }

    public List<Film> deserialize(ObjectMapper objectMapper) throws IOException {
        String file = new File(this.filePath).getAbsolutePath();
        return objectMapper.readValue(new FileReader(file), new TypeReference<List<Film>>() {});
    }
}
