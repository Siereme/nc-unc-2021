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
        actorRepository.findAll().get(ind).setFilms(newFilms);
    }

    public static String tittlesToString(LinkedList<Actor> actors) {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Actor actor : actors) {
            sb.append(ind).append(". ").append(actor.getName()).append("\n");
            ++ind;
        }
        return new String(sb);
    }

    Actor actorById(String id) {
        return (Actor) actorRepository.findAll().stream().filter(a -> Objects.equals(a.getId(), id));
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Actor actor : actorRepository.findAll()) {
            sb.append(++ind).append(". ").append(actorToString(actor)).append("\n");
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
            sb.append(filmController.filmsById(actor.getFilms())).append("\n");
        }
        return new String(sb);
    }

    public String actorsById(LinkedList<String> actors) {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (String a : actors) {
            sb.append(ind++).append(". ").append(actorToString(actorById(a))).append("\n");
        }
        return new String(sb);
    }

}
