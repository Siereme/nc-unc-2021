package repository;

import model.director.Director;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/** Репозиторий режиссеров, хранит список режиссеров
 * @see IRepository
 * @see Director
 * @author Vasiliy,Sergey
 * @version 1.0
 * */
public class DirectorRepository extends AbstractRepository<Director> implements repository.IRepository<Director> {
    /** Путь для сериализации\десериализации */
    public static final String DIRECTOR_FILE_PATH = new File("src/main/resources/Directors.json").getAbsolutePath();

    /** Список хранимых режиссеров */
    private final List<Director> directors = new ArrayList<>();

    public DirectorRepository(Director... directors) {
        super(DIRECTOR_FILE_PATH, Arrays.asList(directors));
        this.directors.addAll(Arrays.asList(directors));
    }

    public DirectorRepository() {
        super(DIRECTOR_FILE_PATH);
        init();
    }

    public void init() {
        try {
            directors.addAll(deserialize());
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

    public int size() {
        return directors.size();
    }

}
