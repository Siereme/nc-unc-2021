package view.find;

import controller.ActorController;
import controller.FilmController;
import model.Actor.Actor;
import view.IView;
import view.View;

import java.util.LinkedList;

public class FindByActorView extends View implements IView {
    ActorController actorController = new ActorController();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Find By Actor Menu------");
            System.out.println("------Select Actor------");
            System.out.println(actorController);
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            } else if (option < actorController.getActorRepository().findAll().size() && option >= 0) {
                Actor actor = actorController.getActorRepository().findAll().get(option);
                FilmController filmController = new FilmController();
                LinkedList<String> filmsId = filmController.getFilmsByActor(actor);
                System.out.println(filmController.filmsByIdToString(filmsId));
                pressAnyKeyToContinue();
            }
        }
    }

    @Override
    public void showMessage(String messsage) {

    }

}
