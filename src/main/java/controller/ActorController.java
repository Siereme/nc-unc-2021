package controller;

import model.IEntity;
import model.actor.Actor;
import model.film.Film;
import repository.ActorRepository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

/** Actor controller
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

    private ActorRepository repository;

    public ActorController() {
        repository = new ActorRepository();
    }

    public ActorController(ActorRepository newActorRepository) {
        repository = newActorRepository;
    }

    public void setFilms(int ind, LinkedList<String> newFilms) {
        Actor actor = getEntity(ind);
        actor.setFilms(newFilms);
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
    public String entityToString(Actor actor) {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ").append(actor.getId()).append("\n");
        sb.append("Name: ").append(actor.getName()).append("\n");
        sb.append("Year: ").append(actor.getYear()).append("\n");
        if (actor.getFilms().isEmpty()) {
            sb.append("Films is empty");
        } else {
            sb.append("Films:\n");
            FilmController filmController = new FilmController();
            for (String filmId : actor.getFilms()) {
                Film film = filmController.getEntityById(filmId);
                sb.append(film.getTittle()).append("\n");
            }
        }
        return new String(sb);
    }

    public int size() {
        return repository.size();
    }

    public Actor getEntity(int ind) {
        return repository.findAll().get(ind);
    }

    public void addEntity(IEntity entity) {
        repository.findAll().add((Actor) entity);
        updateRepository();
    }

    public void updateRepository() {
        try {
            repository.serialize();
        } catch (IOException e) {
            System.out.println("Serialize corrupted... " + e);
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

    public void addFilmToActors(Film film, LinkedList<String> actorsId) {
        for (String actorId : actorsId) {
            Actor actor = getEntityById(actorId);
            LinkedList<String> filmsId = actor.getFilms();
            filmsId.add(film.getId());
        }
    }

    public static boolean isContainsFilms(Actor actor, Film film) {
        for (String filmsId : actor.getFilms()) {
            if (Objects.equals(filmsId, film.getId())) {
                return true;
            }
        }
        return false;
    }

    public void removeFilmFromAllActors(Film film) {
        boolean isChange = false;
        for (Actor actor : repository.findAll()) {
            if (isContainsFilms(actor, film)) {
                LinkedList<String> filmsId = actor.getFilms();
                filmsId.remove(film.getId());
                isChange = true;
            }
        }
        if (isChange) {
            updateRepository();
        }
    }

}
