package repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.user.IUser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/** Класс репозиторий пользователей
 * @see IRepository
 * @author Sergey
 * @version 1.0
 * */
public class UserRepository extends AbstractRepository<IUser> implements IRepository<IUser> {
    /** Путь для сериализации\десериализации */
    public static final String USERS_FILE_PATH = new File("src/main/resources/Users.json").getAbsolutePath();


    public UserRepository(List<IUser> users){
        super(USERS_FILE_PATH, users);
    }

    public UserRepository() {
        super(USERS_FILE_PATH);
        init();
    }
    private void init() {
        try {
            this.entities.addAll(deserialize());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<IUser> findAll() {
        return this.entities;
    }

    @Override
    public boolean deleteById(Integer id) {
        for (IUser user : entities) {
            if (user.getId().equals(id.toString())) {
                return entities.removeIf(f -> Objects.equals(f.getId(), id.toString()));
            }
        }
        return false;
    }

    @Override
    public boolean findById(Integer Id) {
        return entities.stream().allMatch(f -> Objects.equals(f.getId(), Id.toString()));
    }

    @Override
    public boolean create(IUser user) {
        return entities.add(user);
    }

    @Override
    public void clear() {
        entities.clear();
    }

    @Override
    public void serialize(ObjectMapper objectMapper) throws IOException {
        objectMapper.writeValue(new FileWriter(this.filePath), this.entities);
    }

    @Override
    public List<IUser> deserialize(ObjectMapper objectMapper) throws IOException {
        return null;
    }

    @Override
    public int size() {
        return entities.size();
    }

}
