package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Genre.Genre;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GenreRepository implements IRepository<Genre> {
    private final String filePath = "src/main/resources/Genres.json";
    private final List<Genre> genres = new ArrayList<>();

    public GenreRepository(Genre... genres) {
        this.genres.addAll(Arrays.asList(genres));
    }

    public GenreRepository() {
        init();
    }

    //todo serrialization
    private void init() {
/*        genres.add(new Genre("genre1"));
        genres.add(new Genre("genre2"));*/
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
        String file = new File(this.filePath).getAbsolutePath();
        objectMapper.writeValue(new FileWriter(file), this.genres);
    }

    public List<Genre> deserialize(ObjectMapper objectMapper) throws IOException {
        String file = new File(this.filePath).getAbsolutePath();
        return objectMapper.readValue(new FileReader(file), new TypeReference<List<Genre>>() {});
    }
}
