package view.edit.film;

import controller.FilmController;
import repository.GenreRepository;
import view.IView;
import view.View;

import java.util.LinkedList;

public class EditFilmMenuView extends View implements IView {
    private final FilmController filmController = new FilmController();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Select Film To Edit------");
            System.out.println(filmController.getFilmsRepository().toString());
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            } else {
                if (option >= 0 && option < filmController.getFilmsRepository().findAll().size()) {
                    int filmInd = option;
                    boolean show1 = true;
                    while (show1) {
                        System.out.println(filmController.filmToString(
                                filmController.getFilmsRepository().findAll().get(filmInd)));
                        System.out.println("1. Change Tittle");
                        System.out.println("2. Change date");
                        System.out.println("3. Change genres");
                        System.out.println("4. Change directors");
                        System.out.println("5. Change actors");
                        System.out.println("6. Exit");
                        option = getOption();
                        switch (option) {
                            case 1:
                                setTittle(filmInd);
                                break;
                            case 2:
                                setDate(filmInd);
                                break;
                            case 3:
                                setGenres(filmInd);
                                break;
                            case 4:
                                setDirectors(filmInd);
                                break;
                            case 5:
                                setActors(filmInd);
                                break;
                            case 6:
                                show1 = false;
                            default:
                                break;
                        }
                    }
                }
            }
        }
    }

    private void setTittle(int filmInd) {
        String tittle = getStr("Enter a tittle\n");
        filmController.getFilmsRepository().findAll().get(filmInd).setTittle(tittle);
    }

    private void setDate(int filmInd) {
        filmController.getFilmsRepository().findAll().get(filmInd).setDate(getDate());
    }

    private void setGenres(int filmInd) {
        GenreRepository genreRepository = new GenreRepository();
        LinkedList<String> newGenres = new LinkedList<>();
        boolean show = true;
        while (show) {
            System.out.println("Select Genre to add");
            System.out.println("-1. Exit");
            System.out.println(genreRepository);
            int option = getOption();
            if (option == -1) {
                show = false;
            }
            if (option < 0 || option >= genreRepository.findAll().size()) {
                continue;
            }
            if (!newGenres.contains(genreRepository.findAll().get(option).getId())) {
                newGenres.add(genreRepository.findAll().get(option).getId());
                System.out.println(genreRepository.findAll().get(option).getId());
                System.out.println(newGenres.get(0));
            }
        }
        filmController.setGenres(filmInd, newGenres);
    }

    private void setDirectors(int filmInd) {
        filmController.setDirectors(filmInd, getDirectorsId());
    }

    private void setActors(int filmInd) {
        filmController.setActors(filmInd, getActorsId());
    }

    @Override
    public void showMessage(String messsage) {

    }
}
