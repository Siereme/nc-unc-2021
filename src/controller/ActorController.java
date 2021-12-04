package controller;

import repository.ActorRepository;

public class ActorController {
    private ActorRepository actorRepository = new ActorRepository();

    public ActorController(ActorRepository newActorRepository) {
        actorRepository = newActorRepository;
    }

/*    public void addFilm(Film film){this.actorRepository.findAll().add(film);}

    public void setFilm(int index, Film film){
        this.films.add(index, film);
        this.films.remove(index + 1);
    }

    public void deleteFilm(int index){this.films.remove(index);}*/


}
