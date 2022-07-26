package controller;

import model.IEntity;
import model.genre.Genre;
import repository.GenreRepository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

/** Genre controller
 * @see Genre
 * @see IEntityController
 * @see GenreRepository
 * @author Vasiliy
 * @version 1.0
 * */
public class GenreController implements IEntityController<Genre> {

    public GenreRepository getRepository() {
        return repository;
    }

    GenreRepository repository;

    public GenreController() {
        repository = new GenreRepository();
    }

    @Override
    public String getNames() {
        StringBuffer sb = new StringBuffer();
        for (Genre genre : repository.findAll()) {
            sb.append(genre.getTittle()).append("\n");
        }
        return new String(sb);
    }

    @Override
    public String getAllEntitiesAsString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Genre genre : repository.findAll()) {
            sb.append(ind++).append(". ");
            sb.append(entityToString(genre));
            sb.append("\n");
        }
        return new String(sb);
    }

    public Genre getEntityById(String id) {
        for (Genre genre : repository.findAll()) {
            if (Objects.equals(genre.getId(), id)) {
                return genre;
            }
        }
        return null;
    }

    public String entityToString(Genre genre) {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ").append(genre.getId()).append(" ");
        sb.append("Tittle: ").append(genre.getTittle()).append(" ");
        return new String(sb);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Genre genre : repository.findAll()) {
            sb.append(ind).append(". ").append(entityToString(genre)).append("\n");
            ++ind;
        }
        return new String(sb);
    }

    public String entitiesByIDsToString(LinkedList<String> genres) {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (String s : genres) {
            sb.append(ind++).append(". ").append(entityToString(getEntityById(s))).append("\n");
        }
        return new String(sb);
    }

    public int size() {
        return repository.size();
    }

    public Genre getEntity(int ind) {
        return repository.findAll().get(ind);
    }

    public void addEntity(IEntity entity) {
        repository.findAll().add((Genre) entity);
    }

    public void updateRepository() {
        try {
            repository.serialize();
        } catch (IOException e) {
            System.out.println("Serialize corrupted... " + e);
        }
    }

    public void removeEntity(int ind) {
        repository.findAll().remove(ind);
        updateRepository();
    }

    @Override
    public LinkedList<String> getEntities() {
        LinkedList<String> ids = new LinkedList<>();
        for (Genre genre : repository.findAll()) {
            ids.add(genre.getId());
        }
        return ids;
    }

}
