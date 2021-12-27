package app.repository.imp;

import app.repository.AbstractRepository;
import app.repository.IRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import app.model.genre.Genre;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/** Genre app.repository
 * @see IRepository
 * @see Genre
 * @author Vasiliy, Sergey
 * @version 1.0
 * */
public class GenreRepository extends AbstractRepository<Genre> {

    public static final String GENRE_FILE_PATH = new File("src/main/resources/database/Genres.json").getAbsolutePath();

    public GenreRepository(Genre... genres) {
        super(GENRE_FILE_PATH, Arrays.asList(genres));
        this.entities.addAll(Arrays.asList(genres));
    }

    public GenreRepository() {
        super(GENRE_FILE_PATH);
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
    public List<Genre> findAll() {
        return entities;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Genre genre : entities) {
            sb.append(ind).append(". ").append(genre).append("\n");
            ++ind;
        }
        return new String(sb);
    }

    public int size() {
        return entities.size();
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public void mergeFiles(String file1, String file2) {
        List<Genre> genres = new LinkedList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            genres.addAll(mapper.readValue(new File(file1), new TypeReference<List<Genre>>() {
            }));
            genres.addAll(mapper.readValue(new File(file2), new TypeReference<List<Genre>>() {
            }));
            mapper.writeValue(new File(this.filePath),
                    genres.stream().filter(distinctByKey(Genre::getId)).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
