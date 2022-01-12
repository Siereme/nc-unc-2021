package app.controller.imp;

import app.controller.IEntityController;
import app.model.IEntity;
import app.model.actor.Actor;
import app.model.film.Film;
import app.repository.imp.ActorRepository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Actor app.controller
 * @see IEntityController
 * @see Actor
 * @see ActorRepository
 * @author Vasiliy
 * @version 1.0
 * */
public class ActorController implements IEntityController<Actor> {

    public ActorRepository getRepository() {
        return repository;
    }

    private final ActorRepository repository;

    public ActorController() {
        repository = new ActorRepository();
    }

    public ActorController(ActorRepository newActorRepository) {
        repository = newActorRepository;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Actor actor : repository.findAll()) {
            sb.append(ind++).append(". ").append(entityToString(actor)).append("\n");
        }
        return new String(sb);
    }

    public String entitiesByIDsToString(LinkedList<String> actors) {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (String a : actors) {
            sb.append(ind++).append(". ").append(entityToString(getEntityById(a))).append("\n");
        }
        return new String(sb);
    }

    @Override
    public Actor getEntityById(String id) {
        for (Actor actor : repository.findAll()) {
            if (Objects.equals(actor.getId(), id)) {
                return actor;
            }
        }
        return null;
    }

    @Override
    public Actor getEntityByName(String name) {
        for (Actor actor : repository.findAll()) {
            if (Objects.equals(actor.getName(), name)) {
                return actor;
            }
        }
        return null;
    }

    @Override
    public String entityToString(Actor actor) {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ").append(actor.getId()).append(" ");
        sb.append("Name: ").append(actor.getName()).append(" ");
        sb.append("Age: ").append(actor.getYear());
        return new String(sb);
    }

    public int size() {
        return repository.size();
    }

    public Actor getEntity(int ind) {
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
        Actor actor = getEntity(ind);
        FilmController filmController = new FilmController();
        //firstly we delete this actor from all films in which he starred
        filmController.removeActorFromAllFilms(actor);
        //secondly we remove actor from actor repo
        repository.findAll().remove(ind);
        updateRepository();
    }

    @Override
    public LinkedList<String> getEntities() {
        LinkedList<String> ids = new LinkedList<>();
        for (Actor actor : repository.findAll()) {
            ids.add(actor.getId());
        }
        return ids;
    }

    @Override
    public String getNames() {
        StringBuffer sb = new StringBuffer();
        for (Actor actor : repository.findAll()) {
            sb.append(actor.getName()).append("\n");
        }
        return new String(sb);
    }

    @Override
    public String getAllEntitiesAsString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Actor actor : repository.findAll()) {
            sb.append(ind++).append(". ");
            sb.append(entityToString(actor));
            sb.append("\n");
        }
        return new String(sb);
    }

    @Override
    public String getIdByName(String name) {
        for (Actor actor : repository.findAll()) {
            if (Objects.equals(actor.getName(), name)) {
                return actor.getId();
            }
        }
        return null;
    }

    public void addEntity(IEntity entity) {
        FilmController filmController = new FilmController();
        Actor actor = new Actor((Actor) entity);
        filmController.addActorToFilms(actor , actor.getFilms());
        getRepository().findAll().add(actor);
        updateRepository();
    }

    @Override
    public boolean remove(String id) {
        Actor actor = getEntityById(id);
        return repository.findAll().remove(actor);
    }

    @Override
    public boolean remove(Actor entity) {
        FilmController filmController = new FilmController();
        repository.findAll().remove(entity);
        filmController.removeActorFromAllFilms(entity);
        return updateRepository();
    }

    @Override
    public boolean edit(Actor entity) {
        Actor actor = getEntityById(entity.getId());
        actor.setName(entity.getName());
        actor.setYear(entity.getYear());
        editEntityInFilms(actor, entity);
        actor.clearFilms();
        actor.setFilms(entity.getFilms());
        return updateRepository();
    }

    @Override
    public List<Actor> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Actor> findBy(Map<String, List<String>> entityIds) {
        LinkedList<Actor> actors = new LinkedList<>();
        if(entityIds.containsKey("actor")){
            List<String> actorsIds = entityIds.get("actor");
            for(Actor actor : this.getRepository().findAll()){
                for (String actorId : actorsIds) {
                    if (Objects.equals(actor.getId(), actorId)) {
                        actors.add(actor);
                    }
                }
            }
        }
        return actors;
    }

    @Override
    public List<Actor> search(String entityName) {
        return null;
    }

    private void editEntityInFilms(Actor actor, Actor editActor) {
        FilmController filmController = new FilmController();
        List<String> addList = new LinkedList<>();
        List<String> removeList = new LinkedList<>();
        for(String id : actor.getFilms()){
            boolean isContains = editActor.getFilms().stream().anyMatch(editId -> Objects.equals(editId, id));
            if(!isContains) removeList.add(id);
        }
        for(String editId : editActor.getFilms()){
            boolean isContains = actor.getFilms().stream().anyMatch(id -> Objects.equals(id, editId));
            if(!isContains) addList.add(editId);
        }
        if(addList.size() > 0){
            filmController.addActorToFilms(actor, addList);
        }
        if(removeList.size() > 0){
            filmController.removeActorFromFilms(actor, removeList);
        }
    }

    public void setFilmToEntities(String filmId, List<String> actorIds){
        if(actorIds.size() > 0){
            for(String id : actorIds){
                Actor actor = getEntityById(id);
                actor.setFilm(filmId);
            }
            updateRepository();
        }
    }

    public void deleteFilmFromEntities(String filmId, List<String> actorIds){
        if(actorIds.size() > 0){
            for(String id : actorIds){
                Actor actor = getEntityById(id);
                actor.deleteFilm(filmId);
            }
            updateRepository();
        }
    }

    public void deleteFilmFromEntities(String filmId){
        for(Actor actor : repository.findAll()){
            actor.deleteFilm(filmId);
        }
        updateRepository();
    }





}
