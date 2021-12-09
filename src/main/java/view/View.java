package view;

import controller.ActorController;
import controller.DirectorController;
import controller.FilmController;
import controller.GenreController;
import model.Actor.Actor;
import model.Director.Director;
import model.Film.Film;
import model.Genre.Genre;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;

public abstract class View implements IView {

    protected final Scanner input = new Scanner(System.in);

    protected int getOption() {
        try {
            return Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: input is not an id.");
        }
        return 0;
    }

    protected void pressAnyKeyToContinue() {
        System.out.println("Press Enter key to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void cls() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected Date getDate() {
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        while (true) {
            System.out.println("Enter date in format " + dateFormat.toPattern() + "\n");
            String dateStr = input.nextLine();
            try {
                date = dateFormat.parse(dateStr);
            } catch (ParseException e) {
                System.out.println(e.toString());
                continue;
            }
            break;
        }
        return date;
    }

    protected String getStr(String message) {
        System.out.println(message);
        return input.nextLine();
    }

    protected LinkedList<String> getFilmsId() {
        FilmController filmController = new FilmController();
        LinkedList<String> newFilms = new LinkedList<>();
        while (true) {
            System.out.println("Select Film to add");
            System.out.println("-1. Exit");
            System.out.println(filmController);
            int option = getOption();
            if (option == -1) {
                break;
            }
            if (option < 0 || option >= filmController.size()) {
                continue;
            }
            Film film = filmController.getFilm(option);
            if (!newFilms.contains(film.getId())) {
                newFilms.add(film.getId());
            }
        }
        return newFilms;
    }

    protected LinkedList<String> getActorsId() {
        ActorController actorController = new ActorController();
        LinkedList<String> newActors = new LinkedList<String>();
        while (true) {
            System.out.println("Select Actor to add");
            System.out.println("-1. Exit");
            System.out.println(actorController);
            int option = getOption();
            if (option == -1) {
                break;
            }
            if (option < 0 || option >= actorController.size()) {
                continue;
            }
            Actor actor = actorController.getActor(option);
            if (!newActors.contains(actor.getId())) {
                newActors.add(actor.getId());
            }
        }
        return newActors;
    }

    protected LinkedList<String> getDirectorsId() {
        DirectorController directorController = new DirectorController();
        LinkedList<String> newDirectors = new LinkedList<String>();
        while (true) {
            System.out.println("Select Director to add");
            System.out.println("-1. Exit");
            System.out.println(directorController);
            int option = getOption();
            if (option == -1) {
                break;
            }
            if (option < 0 || option >= directorController.size()) {
                continue;
            }
            Director director = directorController.getDirector(option);
            if (!newDirectors.contains(director.getId())) {
                newDirectors.add(director.getId());
            }
        }
        return newDirectors;
    }

    protected LinkedList<String> getGenresId() {
        GenreController genreController = new GenreController();
        LinkedList<String> newGenres = new LinkedList<>();
        boolean show = true;
        while (show) {
            System.out.println("Select Genre to add");
            System.out.println("-1. Exit");
            System.out.println(genreController);
            int option = getOption();
            if (option == -1) {
                show = false;
            }
            if (option < 0 || option >= genreController.size()) {
                continue;
            }
            Genre genre = genreController.getGenre(option);
            if (!newGenres.contains(genre.getId())) {
                newGenres.add(genre.getId());
            }
        }
        return newGenres;
    }

    protected boolean getConfirm() {
        String confirm = getStr("Confirm deletion (yes/no): ");
        if (Objects.equals(confirm, "y") || Objects.equals(confirm, "yes")) {
            return true;
        }
        return false;
    }

    public String getName() {
        return null;
    }

}
