package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.User.Admin.Admin;
import model.User.IUser;
import model.User.Visitor.Visitor;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class UserRepository implements IRepository<IUser> {
    private final String filePath = "src/main/resources/Users.json";
    private LinkedList<IUser> users = new LinkedList<>();

    public UserRepository(){
        users.add(new Admin("admin1", "123"));
        users.add(new Admin("admin2", "123"));
        users.add(new Visitor("visitor1", "123"));
        users.add(new Visitor("visitor1", "123"));
        init();
    }

    private void init() {
        try {
            serialize(new ObjectMapper());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        this.users.addAll(new AdminRepository().findAll());
//        this.users.addAll(new VisitorRepository().findAll());
    }

    public UserRepository(LinkedList<IUser> users) {
        this.users = users;
    }

    @Override
    public List findAll() {
        return this.users;
    }

    @Override
    public boolean deleteById(Integer id) {
        for (IUser user : users) {
            if (user.getId().equals(id.toString())) {
                // предикат, надеюсь не ошибся в использовании.
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

    public void serialize(ObjectMapper objectMapper) throws IOException {
        String file = new File(this.filePath).getAbsolutePath();
        objectMapper.writerFor(new TypeReference<List<IUser>>() {}).writeValue(new File(file), this.users);
    }

    public List<IUser> deserialize(ObjectMapper objectMapper) throws IOException {
        String file = new File(this.filePath).getAbsolutePath();
        return objectMapper.readerFor(new TypeReference<List<IUser>>() {}).readValue(new FileReader(file));
    }
}
