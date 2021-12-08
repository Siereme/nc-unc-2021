package controller;

import model.Actor.Actor;
import repository.ActorRepository;

import java.util.LinkedList;
import java.util.Objects;

public class ActorController {
    public ActorRepository getActorRepository() {
        return actorRepository;
    }

    private ActorRepository actorRepository = new ActorRepository();

    public ActorController() {

    }

    public ActorController(ActorRepository newActorRepository) {
        actorRepository = newActorRepository;
    }

    public void setFilms(int ind, LinkedList<String> newFilms) {
        Actor actor = getActor(ind);
        actor.setFilms(newFilms);
    }

    Actor getActorById(String id) {
        for (Actor actor : actorRepository.findAll()) {
            if (Objects.equals(actor.getId(), id)) {
                return actor;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Actor actor : actorRepository.findAll()) {
            sb.append(ind++).append(". ").append(actorToString(actor)).append("\n");
        }
        return new String(sb);
    }

    public String actorToString(Actor actor) {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ").append(actor.getId()).append("\n");
        sb.append("Name: ").append(actor.getName()).append("\n");
        sb.append("Year: ").append(actor.getYear()).append("\n");
        if (actor.getFilms().isEmpty()) {
            sb.append("Films is empty");
        } else {
            FilmController filmController = new FilmController();
            sb.append(filmController.filmsByIdToString(actor.getFilms())).append("\n");
        }
        return new String(sb);
    }

    public String actorsByIdToString(LinkedList<String> actors) {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (String a : actors) {
            sb.append(ind++).append(". ").append(actorToString(getActorById(a))).append("\n");
        }
        return new String(sb);
    }

    public int size() {
        return actorRepository.size();
    }

    public Actor getActor(int ind) {
        return actorRepository.findAll().get(ind);
    }

    public void addActor() {
        actorRepository.findAll().add(new Actor());
    }

    public void updateRepository() {
        actorRepository.init();
    }

}
