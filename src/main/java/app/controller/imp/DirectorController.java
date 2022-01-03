package app.controller.imp;

import app.controller.IEntityController;
import app.model.IEntity;
import app.model.actor.Actor;
import app.model.director.Director;
import app.model.genre.Genre;
import app.repository.imp.DirectorRepository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
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

    @Override
    public String getIdByName(String name) {
        for (Director director : repository.findAll()) {
            if (Objects.equals(director.getName(), name)) {
                return director.getId();
            }
        }
        return null;
    }

    @Override
    public boolean remove(String id) {
        Director director = getEntityById(id);
        return repository.findAll().remove(director);
    }

    @Override
    public boolean remove(Director entity) {
        FilmController filmController = new FilmController();
        repository.findAll().remove(entity);
        filmController.removeDirectorFromAllFilms(entity);
        return updateRepository();
    }

    public void addEntity(IEntity entity) {
        FilmController filmController = new FilmController();
        Director director = new Director((Director) entity);
        filmController.addDirectorToFilms(director , director.getFilms());
        getRepository().findAll().add(director);
        updateRepository();
    }

    @Override
    public boolean edit(Director entity) {
        Director director = getEntityById(entity.getId());
        director.setName(entity.getName());
        director.setYear(entity.getYear());
        editEntityInFilms(director, entity);
        director.clearFilms();
        director.setFilms(entity.getFilms());
        return updateRepository();
    }

    @Override
    public List<Director> findAll() {
        return repository.findAll();
    }

    private void editEntityInFilms(Director director, Director editDirector) {
        FilmController filmController = new FilmController();
        List<String> addList = new LinkedList<>();
        List<String> removeList = new LinkedList<>();
        for(String id : director.getFilms()){
            boolean isContains = editDirector.getFilms().stream().anyMatch(editId -> Objects.equals(editId, id));
            if(!isContains) removeList.add(id);
        }
        for(String editId : editDirector.getFilms()){
            boolean isContains = director.getFilms().stream().anyMatch(id -> Objects.equals(id, editId));
            if(!isContains) addList.add(editId);
        }
        if(addList.size() > 0){
            filmController.addDirectorToFilms(director, addList);
        }
        if(removeList.size() > 0){
            filmController.removeDirectorFromFilms(director, removeList);
        }
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

    public boolean updateRepository() {
        try {
            repository.serialize();
            return true;
        } catch (IOException e) {
            System.out.println("Serialize corrupted... " + e);
            return false;
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

    public void setFilmToEntities(String filmId, List<String> directorIds){
        if(directorIds.size() > 0){
            for(String id : directorIds){
                Director director = getEntityById(id);
                director.setFilm(filmId);
            }
            updateRepository();
        }
    }

    public void deleteFilmFromEntities(String filmId, List<String> directorIds){
        if(directorIds.size() > 0){
            for(String id : directorIds){
                Director director = getEntityById(id);
                director.deleteFilm(filmId);
            }
            updateRepository();
        }
    }

    public void deleteFilmFromEntities(String filmId){
        for(Director director : repository.findAll()){
            director.deleteFilm(filmId);
        }
        updateRepository();
    }

}
