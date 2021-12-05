package view.edit.film;

import controller.FilmController;
import model.Actor.Actor;
import model.Director.Director;
import model.Genre.Genre;
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
            System.out.println(filmController.getFilms().toString());
            System.out.println("0. Exit");
            int option = getOption();
            if (option == 0) {
                break;
            } else {
                if (option < filmController.getFilms().size()) {
                    int filmInd = option;
                    System.out.println(filmController.getFilms().get(option));
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
                            break;
                    }
                }
            }
        }
    }

    private void setTittle(int filmInd) {
        System.out.println("Enter tittle: ");
        String tittle = input.next();
        filmController.getFilms().get(filmInd).setTittle(tittle);
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
            filmController.getFilms().get(filmInd).setDate(date);
            break;
        }
    }

    private void setGenres(int filmInd) {
        GenreRepository genreRepository = new GenreRepository();
        LinkedList<Genre> newGenres = new LinkedList<>();
        while (true) {
            System.out.println("Select Genre to add");
            System.out.println("-1. Exit");
            System.out.println(genreRepository);
            int option = getOption();
            if (option == -1) {
                break;
            }
            if (option < 0 || option >= genreRepository.findAll().size()) {
                continue;
            }
            if (!newGenres.contains(genreRepository.findAll().get(option))) {
                newGenres.add(genreRepository.findAll().get(option));
            }
        }
        filmController.setGenres(filmInd, newGenres);
    }

    private void setDirectors(int filmInd) {
        DirectorRepository directorRepository = new DirectorRepository();
        LinkedList<Director> newDirectors = new LinkedList<Director>();
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
            if (!newDirectors.contains(directorRepository.findAll().get(option))) {
                newDirectors.add(directorRepository.findAll().get(option));
            }
        }
        filmController.setDirectors(filmInd, newDirectors);
    }

    private void setActors(int filmInd) {
        ActorRepository actorRepository = new ActorRepository();
        LinkedList<Actor> newActors = new LinkedList<Actor>();
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
            if (!newActors.contains(actorRepository.findAll().get(option))) {
                newActors.add(actorRepository.findAll().get(option));
            }
        }
        filmController.setActors(filmInd, newActors);
    }

    @Override
    public void showMessage(String messsage) {

    }
}
