package controller;

import model.IEntity;
import model.user.IUser;
import repository.UserRepository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class UserController implements IEntityController<IUser>{
    UserRepository repository;

    public UserController() {
        this.repository = new UserRepository();
    }

    public UserController(List<IUser> users) {
        this.repository = new UserRepository(users);
    }

    @Override
    public IUser getEntityById(String id) {
        for (IUser user : repository.findAll()) {
            if (Objects.equals(user.getId(), id)) {
                return user;
            }
        }
        return null;
    }

    public IUser getEntityByLogin(String login, String password){
        for(IUser user : repository.findAll()){
            if(Objects.equals(user.getName(), login) && Objects.equals(user.getPassword(), password)){
                return user;
            }
        }
        return null;
    }

    @Override
    public String entityToString(IUser user) {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ").append(user.getId()).append("\n");
        sb.append("Tittle: ").append(user.getName()).append("\n");
        return new String(sb);
    }

    @Override
    public String entitiesByIDsToString(LinkedList<String> users) {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (String s : users) {
            sb.append(ind++).append(". ").append(entityToString(getEntityById(s))).append("\n");
        }
        return new String(sb);
    }

    @Override
    public int size() {
        return repository.size();
    }

    @Override
    public IUser getEntity(int ind) {
        return repository.findAll().get(ind);
    }

    @Override
    public void addEntity(IEntity entity) {

    }

    @Override
    public void updateRepository() {
        try {
            repository.serialize();
        } catch (IOException e) {
            System.out.println("Serialize corrupted... " + e);
        }
    }

    @Override
    public void removeEntity(int ind) {
        repository.findAll().remove(ind);
    }

    @Override
    public LinkedList<String> getEntities() {
        return null;
    }
}
