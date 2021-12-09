package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.User.IUser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserRepository implements IRepository<IUser> {
    private final String filePath = new File("src/main/resources/Users.json").getAbsolutePath();
    private LinkedList<IUser> users = new LinkedList<>();

    public UserRepository(){
//        users.add(new Admin("admin1", "123"));
//        users.add(new Admin("admin2", "123"));
//        users.add(new Visitor("visitor1", "123"));
//        users.add(new Visitor("visitor1", "123"));
        init();
    }

    private void init() {
        try {
            this.users.addAll(deserialize(new ObjectMapper()));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        objectMapper.writerFor(new TypeReference<List<IUser>>() {}).writeValue(new File(this.filePath), this.users);
    }

    public List<IUser> deserialize(ObjectMapper objectMapper) throws IOException {
        return objectMapper.readerFor(new TypeReference<List<IUser>>() {}).readValue(new FileReader(this.filePath));
    }

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public void mergeFiles(String file1, String file2){
        List<IUser> users = new LinkedList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            users.addAll(mapper.readerFor(new TypeReference<List<IUser>>() {}).readValue(new File(file1)));
            users.addAll(mapper.readerFor(new TypeReference<List<IUser>>() {}).readValue(new File(file2)));
            List<IUser> result = users.stream()
                    .filter(distinctByKey(p -> p.getId()))
                    .collect(Collectors.toList());
            mapper.writeValue(new File(this.filePath), result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}