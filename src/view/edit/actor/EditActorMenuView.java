package view.edit.actor;

import controller.ActorController;
import model.Film.Film;
import repository.FilmsRepository;
import view.IView;
import view.View;

import java.util.LinkedList;

public class EditActorMenuView extends View implements IView {
    ActorController actorController = new ActorController();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Select Actor To Edit------");
            System.out.println(actorController.getActorRepository());
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            } else {
                if (option>= 0 && option < actorController.getActorRepository().findAll().size()) {
                    int actorInd = option;
                    System.out.println(actorController.getActorRepository().findAll().get(option));
                    System.out.println("1. Change name");
                    System.out.println("2. Change films");
                    System.out.println("3. Exit");
                    option = getOption();
                    switch (option) {
                        case 1:
                            setName(actorInd);
                            break;
                        case 2:
                            setFilms(actorInd);
                            break;
                        case 3:
                            show = false;
                        default:
                            break;
                    }
                }
            }
        }
    }

    private void setName(int actorInd) {
        System.out.println("Enter new name");
        String newName = input.next();
        actorController.getActorRepository().findAll().get(actorInd).setName(newName);
    }

    private void setFilms(int actorInd) {
        FilmsRepository filmsRepository = new FilmsRepository();
        LinkedList<Film> newFilms = new LinkedList<>();
        while (true) {
            System.out.println("Select Film to add");
            System.out.println("-1. Exit");
            System.out.println(filmsRepository);
            int option = getOption();
            if (option == -1) {
                break;
            }
            if (option < 0 || option >= filmsRepository.findAll().size()) {
                continue;
            }
            if (!newFilms.contains(filmsRepository.findAll().get(option))) {
                newFilms.add(filmsRepository.findAll().get(option));
            }
        }
        actorController.setFilms(actorInd, newFilms);
    }

    @Override
    public void showMessage(String messsage) {

    }
}
