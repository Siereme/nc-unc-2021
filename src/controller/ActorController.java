package controller;

import model.Actor.Actor;
import model.Film.Film;
import repository.ActorRepository;

import java.util.LinkedList;

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

/*    public void addFilm(Film film){this.actorRepository.findAll().add(film);}

    public void setFilm(int index, Film film){
        this.films.add(index, film);
        this.films.remove(index + 1);
    }

    public void deleteFilm(int index){this.films.remove(index);}*/

    public void setFilms(int ind, LinkedList<Film> newFilms) {
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


}
