package app.controller;

import app.model.IEntity;
import app.model.director.Director;
import app.repository.DirectorRepository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

/** Director app.controller
 * @see IEntityController
 * @see Director
 * @see DirectorRepository
 * @author Vasiliy
 * @version 1.0
 * */
public class DirectorController implements IEntityController<Director> {

    public DirectorRepository getRepository() {
        return repository;
    }

    public void setRepository(DirectorRepository repository) {
        this.repository = repository;
    }

    private DirectorRepository repository = new DirectorRepository();

    @Override
    public String getNames() {
        StringBuffer sb = new StringBuffer();
        for (Director director : repository.findAll()) {
            sb.append(director.getName()).append("\n");
        }
        return new String(sb);
    }

    @Override
    public String getAllEntitiesAsString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Director director : repository.findAll()) {
            sb.append(ind).append(". ");
            sb.append(entityToString(director));
            sb.append("\n");
        }
        return new String(sb);
    }

    public Director getEntityById(String id) {
        for (Director director : repository.findAll()) {
            if (Objects.equals(director.getId(), id)) {
                return director;
            }
        }
        return null;
    }

    @Override
    public Director getEntityByName(String name) {
        for (Director director : repository.findAll()) {
            if (Objects.equals(director.getName(), name)) {
                return director;
            }
        }
        return null;
    }

    public String entityToString(Director director) {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ").append(director.getId()).append(" ");
        sb.append("Name: ").append(director.getName()).append(" ");
        sb.append("Age: ").append(director.getYear());
        return new String(sb);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Director director : repository.findAll()) {
            sb.append(ind++).append(". ").append(entityToString(director)).append("\n");
        }
        return new String(sb);
    }

    public String entitiesByIDsToString(LinkedList<String> directors) {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (String d : directors) {
            sb.append(ind++).append(". ").append(entityToString(getEntityById(d))).append("\n");
        }
        return new String(sb);
    }

    public int size() {
        return repository.size();
    }

    public Director getEntity(int ind) {
        return repository.findAll().get(ind);
    }

    public void addEntity(IEntity entity) {
        repository.findAll().add((Director) entity);
    }

    public void updateRepository() {
        try {
            repository.serialize();
        } catch (IOException e) {
            System.out.println("Serialize corrupted... " + e);
        }
    }

    public void removeEntity(int ind) {
        FilmController filmController = new FilmController();
        Director director = getEntity(ind);
        // firstly we remove director from all common films
        filmController.removeDirectorFromAllFilms(director);
        filmController.updateRepository();
        // remove director from director app.repository
        repository.findAll().remove(ind);
        updateRepository();
    }

    @Override
    public LinkedList<String> getEntities() {
        LinkedList<String> ids = new LinkedList<>();
        for (Director director : repository.findAll()) {
            ids.add(director.getId());
        }
        return ids;
    }

}
