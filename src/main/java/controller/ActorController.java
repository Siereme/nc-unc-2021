package controller;

import model.IEntity;
import model.actor.Actor;
import model.film.Film;
import repository.ActorRepository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

/** Контроллер для сущности актер
 * @see IEntityController
 * @see Actor
 * @see ActorRepository
 * @author Vasiliy
 * @version 1.0
 * */
public class ActorController implements IEntityController<Actor> {
    public ActorRepository getActorRepository() {
        return actorRepository;
    }

    private ActorRepository actorRepository;

    public ActorController() {
        actorRepository = new ActorRepository();
    }

    public ActorController(ActorRepository newActorRepository) {
        actorRepository = newActorRepository;
    }

    public void setFilms(int ind, LinkedList<String> newFilms) {
        Actor actor = getEntity(ind);
        actor.setFilms(newFilms);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Actor actor : actorRepository.findAll()) {
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
        for (Actor actor : actorRepository.findAll()) {
            if (Objects.equals(actor.getId(), id)) {
                return actor;
            }
        }
        return null;
    }

    @Override
    public String entityToString(Actor actor) {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ").append(actor.getId()).append("\n");
        sb.append("Name: ").append(actor.getName()).append("\n");
        sb.append("Year: ").append(actor.getYear()).append("\n");
        if (actor.getFilms().isEmpty()) {
            sb.append("Films is empty");
        } else {
            FilmController filmController = new FilmController();
            sb.append(filmController.entitiesByIDsToString(actor.getFilms())).append("\n");
        }
        return new String(sb);
    }

    public int size() {
        return actorRepository.size();
    }

    public Actor getEntity(int ind) {
        return actorRepository.findAll().get(ind);
    }

    public void addEntity(IEntity entity) {
        actorRepository.findAll().add((Actor) entity);
    }

    public void updateRepository() {
        try {
            actorRepository.serialize();
        } catch (IOException e) {
            System.out.println("Serialize corrupted... " + e);
        }
    }

    public void removeEntity(int ind) {
        FilmController filmController = new FilmController();
        Actor actor = getEntity(ind);
        filmController.removeActorFromAllFilms(actor);
        filmController.updateRepository();
        actorRepository.findAll().remove(ind);
    }

    @Override
    public LinkedList<String> getEntities() {
        LinkedList<String> ids = new LinkedList<>();
        for (Actor actor : actorRepository.findAll()) {
            ids.add(actor.getId());
        }
        return ids;
    }

    public void addFilmToActors(Film film, LinkedList<String> actorsId) {
        for (String actorId : actorsId) {
            Actor actor = getEntityById(actorId);
            LinkedList<String> filmsId = actor.getFilms();
            filmsId.add(film.getId());
        }
    }

    public static boolean isContainsFilms(Actor actor, Film film) {
        for (String filmsId : actor.getFilms()) {
            if (filmsId.equals(film.getId())) {
                return true;
            }
        }
        return false;
    }

    public void removeFilmFromAllActors(Film film) {
        for (Actor actor : actorRepository.findAll()) {
            if (isContainsFilms(actor, film)) {
                LinkedList<String> filmsId = actor.getFilms();
                filmsId.remove(film.getId());
            }
        }
    }

}
