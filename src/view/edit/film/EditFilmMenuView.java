package view.edit.film;

import controller.FilmController;
import repository.ActorRepository;
import repository.DirectorRepository;
import repository.GenreRepository;
import view.IView;
import view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class EditFilmMenuView extends View implements IView {
    private final FilmController filmController = new FilmController();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Select Film To Edit------");
            System.out.println(filmController.getRepository().toString());
            System.out.println("-1. Exit");
            int option = getOption();
            if (option == -1) {
                show = false;
            } else {
                if (option >= 0 && option < filmController.getRepository().findAll().size()) {
                    int filmInd = option;
                    boolean show1 = true;
                    while (show1) {
                        System.out.println(
                                filmController.filmToString(filmController.getRepository().findAll().get(filmInd)));
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
        System.out.println("Enter tittle: ");
        String tittle = input.next();
        filmController.getRepository().findAll().get(filmInd).setTittle(tittle);
    }

    private void setDate(int filmInd) {
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        while (true) {
            System.out.println("Enter date in format " + dateFormat.toPattern() + "\n");
            String dateStr = input.next();
            try {
                date = dateFormat.parse(dateStr);
            } catch (ParseException e) {
                System.out.println(e.toString());
                continue;
            }
            filmController.getRepository().findAll().get(filmInd).setDate(date);
            break;
        }
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
        DirectorRepository directorRepository = new DirectorRepository();
        LinkedList<String> newDirectors = new LinkedList<String>();
        while (true) {
            System.out.println("Select Director to add");
            System.out.println("-1. Exit");
            System.out.println(directorRepository);
            int option = getOption();
            if (option == -1) {
                break;
            }
            if (option < 0 || option >= directorRepository.findAll().size()) {
                continue;
            }
            if (!newDirectors.contains(directorRepository.findAll().get(option).getId())) {
                newDirectors.add(directorRepository.findAll().get(option).getId());
            }
        }
        filmController.setDirectors(filmInd, newDirectors);
    }

    private void setActors(int filmInd) {
        ActorRepository actorRepository = new ActorRepository();
        LinkedList<String> newActors = new LinkedList<String>();
        while (true) {
            System.out.println("Select Actor to add");
            System.out.println("-1. Exit");
            System.out.println(actorRepository);
            int option = getOption();
            if (option == -1) {
                break;
            }
            if (option < 0 || option >= actorRepository.findAll().size()) {
                continue;
            }
            if (!newActors.contains(actorRepository.findAll().get(option).getId())) {
                newActors.add(actorRepository.findAll().get(option).getId());
            }
        }
        filmController.setActors(filmInd, newActors);
    }

    @Override
    public void showMessage(String messsage) {

    }
}
