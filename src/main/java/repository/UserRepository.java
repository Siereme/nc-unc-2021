package repository;

import model.user.IUser;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
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

    /** Список хранимых пользователей */
    private LinkedList<IUser> users = new LinkedList<>();

    public UserRepository(List<IUser> users){
        super(USERS_FILE_PATH, users);
    }

    public UserRepository() {
        super(USERS_FILE_PATH);
        init();
    }
    private void init() {
        try {
            this.users.addAll(deserialize());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<IUser> findAll() {
        return this.users;
    }

    @Override
    public boolean deleteById(Integer id) {
        for (IUser user : users) {
            if (user.getId().equals(id.toString())) {
                return users.removeIf(f -> Objects.equals(f.getId(), id.toString()));
            }
        }
        return false;
    }

    @Override
    public boolean findById(Integer Id) {
        return users.stream().allMatch(f -> Objects.equals(f.getId(), Id.toString()));
    }

    @Override
    public boolean create(IUser user) {
        return users.add(user);
    }

    @Override
    public void clear() {
        users.clear();
    }

    @Override
    public int size() {
        return users.size();
    }

}
