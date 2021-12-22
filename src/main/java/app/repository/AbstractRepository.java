package app.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import app.model.IEntity;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/** Abstract class of app.repository
 * @author Sergey
 * @version 1.0
 * */
public abstract class AbstractRepository<T extends IEntity> {
    protected final String filePath;
    protected final List<T> entities;
    private final ObjectMapper mapper = new ObjectMapper();

    public AbstractRepository(String filePath) {
        this.filePath = filePath;
        this.entities = new LinkedList<>();
    }

    public AbstractRepository(String filePath, List<T> entities) {
        this.filePath = filePath;
        this.entities = entities;
    }

    public abstract List<T> findAll();

    public void serialize() throws IOException {
        mapper.writerFor(new TypeReference<List<T>>() {
        }).writeValue(new File(this.filePath), this.entities);
    }

    public List<T> deserialize() throws IOException {
        try {
            return mapper.readerFor(new TypeReference<List<T>>() {
            }).readValue(new FileReader(this.filePath));
        } catch (MismatchedInputException e) {
            System.out.println("File is empty");
            e.printStackTrace();
            return new LinkedList<>();
        }
    }

    public List<T> deserialize(String file) throws IOException {
        try {
            return mapper.readerFor(new TypeReference<List<T>>() {
            }).readValue(new FileReader(file));
        } catch (MismatchedInputException e) {
            System.out.println("File is empty");
            e.printStackTrace();
            return new LinkedList<>();
        }
    }

    public Boolean isContains(String id) {
        return this.entities.stream().anyMatch(x -> Objects.equals(x.getId(), id));
    }

    public void mergeFiles(String file) {
        List<T> setEntities = new LinkedList<>();
        try {
            List<T> entities = new LinkedList<>(deserialize(file));
            for (T entity : entities) {
                if (!isContains(entity.getId())) {
                    setEntities.add(entity);
                }
            }
            mapper.writerFor(new TypeReference<List<T>>() {
            }).writeValue(new File(this.filePath), entities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
