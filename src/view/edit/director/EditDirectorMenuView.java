package view.edit.director;

import controller.DirectorController;
import repository.FilmsRepository;
import view.IView;
import view.View;

import java.util.LinkedList;

public class EditDirectorMenuView extends View implements IView {
    DirectorController directorController = new DirectorController();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Select Director To Edit------");
            System.out.println(directorController.getDirectorRepository());
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            } else {
                if (option >= 0 && option < directorController.getDirectorRepository().findAll().size()) {
                    int actorInd = option;
                    System.out.println(directorController.getDirectorRepository().findAll().get(option));
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

    private void setName(int directorInd) {
        System.out.println("Enter new name");
        String newName = input.next();
        directorController.getDirectorRepository().findAll().get(directorInd).setName(newName);
    }

    private void setFilms(int directorInd) {
        FilmsRepository filmsRepository = new FilmsRepository();
        LinkedList<String> newFilms = new LinkedList<>();
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
            if (!newFilms.contains(filmsRepository.findAll().get(option).getId())) {
                newFilms.add(filmsRepository.findAll().get(option).getId());
            }
        }
        directorController.setFilms(directorInd, newFilms);
    }

    @Override
    public void showMessage(String messsage) {

    }
}
