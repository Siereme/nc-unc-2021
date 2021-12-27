package app.controller.imp;

import app.controller.IEntityController;
import app.model.IEntity;
import app.model.user.IUser;
import app.repository.imp.UserRepository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/** user app.controller
 * @author Sergey
 * @version 1.0
 * */
public class UserController implements IEntityController<IUser> {
    final UserRepository repository;

    @Override
    public String getNames() {
        StringBuffer sb = new StringBuffer();
        for (IUser user : repository.findAll()) {
            sb.append(user.getName()).append("\n");
        }
        return new String(sb);
    }

    @Override
    public String getAllEntitiesAsString() {
        return null;
    }

    @Override
    public String getIdByName(String name) {
        return null;
    }

    @Override
    public boolean remove(String id) {
        return false;
    }

    @Override
    public boolean remove(IUser entity) {
        return repository.findAll().remove(entity);
    }

    @Override
    public boolean edit(IUser entity) {
        return false;
    }

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

    @Override
    public IUser getEntityByName(String name) {
        for (IUser user : repository.findAll()) {
            if (Objects.equals(user.getName(), name)) {
                return user;
            }
        }
        return null;
    }

    public IUser getEntityByLogin(String login, String password) {
        for (IUser user : repository.findAll()) {
            if (Objects.equals(user.getName(), login) && Objects.equals(user.getPassword(), password)) {
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
    public boolean updateRepository() {
        try {
            repository.serialize();
            return true;
        } catch (IOException e) {
            System.out.println("Serialize corrupted... " + e);
            return false;
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
