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

/**
 * Класс представление, от которого будут наследованы все остальные классы-представления
 * @author vasiliy
 * @version 1.0
 * */

public abstract class View implements IView {

    /** Поле сканер, для считывания данных с клавиатуры */
    protected final Scanner input = new Scanner(System.in);

    /** Функция для взаимодействия с пользователем, пользователь должен ввести какое-либо число
     * @return возвращает число, введенное пользователем
     * */
    protected int getOption() {
        try {
            return Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: input is not an id.");
        }
        return 0;
    }

    /** Функция показывает сообщение пользователю Press Enter key to continue... и блокирует дальнейшее выполнение программы
     * пока пользователь не нажмет какую-либо клавишу на клавиатуре
     * */
    protected void pressAnyKeyToContinue() {
        System.out.println("Press Enter key to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Функция очищает консоль */
    protected void cls() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** Функция предназначена для получения даты, введенной пользователем
     * @return возвращает дату, которую ввел пользователь
     * */
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

    /** Функция предназначена для получения строки, которую ввел пользователь
     * при этом пользователю выводится определенное сообщение, согласно которому он вводит данные
     * @param message - строка(сообщение), которая будет выведена пользователю
     * @return возвращает строку(сообщение), которую ввел пользователь
     * */
    protected String getStr(String message) {
        System.out.println(message);
        return input.nextLine();
    }

    /** Функция получает список ID фильмов, которые выбирает пользователь
     * пользователю предлагается выбрать фильмы, которые есть в базе
     * взаимодействует с контроллером фильмов
     * @return возвращает список ID фильмов, которые выбрал пользователь
     * */
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

    /** Функция получает список ID актеров, которые выбирает пользователь
     * пользователю предлагают выбрать акеров, которые есть в базе
     * взаимодействует с контроллером актеров
     * @return возвращает список ID актеров, которые выбрал пользователь
     * */
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

    /** Функция получает список ID директоров, которые выбирает пользователь
     * взаимодействует с контроллером директоров
     * пользователю предлагают выбрать список директоров, которые есть в базе
     * @return возвращает список ID директоров, которые выбрал пользователь
     * */
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

    /** Функция получает список ID жанров, которые выбирает пользователь
     * взаимодействует с контроллером жанров
     * пользователю предлагают выбрать список жанров, которые есть в базе
     * @return возвращает список ID жанров, которые выбрал пользователь
     * */
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

    /** Функция получает подтвердение от п*/
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
