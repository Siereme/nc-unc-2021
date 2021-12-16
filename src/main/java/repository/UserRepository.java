package repository;

import model.user.IUser;

import java.io.File;
import java.io.IOException;
import java.util.List;

/** User repository
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
    public int size() {
        return entities.size();
    }

}
