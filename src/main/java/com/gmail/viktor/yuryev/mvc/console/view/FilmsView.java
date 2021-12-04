package com.gmail.viktor.yuryev.mvc.console.view;

import com.gmail.viktor.yuryev.mvc.console.controller.FilmController;
import com.gmail.viktor.yuryev.mvc.console.model.Film;

public class FilmsView extends View implements IView {
    private FilmController filmController = new FilmController();

    @Override
    public void display() {
        boolean show = true;
        while (show) {
            System.out.println("------Films Menu------");
            System.out.println("1. Edit Film");
            System.out.println("2. Delete Film");
            System.out.println("3. Add Film");
            System.out.println("4. Show All");
            System.out.println("5. Back To Main Menu");
            int option = getOption();
            switch (option) {
                case 1:
                    editById();
                    break;
                case 2:
                    deleteById();
                    break;
                case 3:
                    System.out.println("not implemented");
                    break;
                case 4:
                    showAll();
                    break;
                case 5:
                    show = false;
                default:
                    break;
            }
        }
    }



    private void editById() {
        System.out.println("not implemented");
    }

    private void deleteById() {
        boolean show = true;
        while (show) {
            System.out.println("Enter id: ");
            int id = getOption();
            boolean innerShow = true;
            while (innerShow) {
                System.out.println("Confirm deletion (yes/no): ");
                String confirm = input.nextLine();
                if (confirm.equals("yes")) {
                    filmController.deleteById(id);
                    innerShow = false;
                } else if (confirm.equals("no")) {
                    return;
                }
            }
            show = false;

        }
    }

    private void showAll() {
        for (Film film : filmController.getFilms()) {
            System.out.println("Film Id " + film.getId());
            System.out.println("Film Name " + film.getFilmName());
            System.out.println("Film Directors " + film.getDirectors());
        }
    }

    @Override
    public void showMessage(String messsage) {
        System.out.println(messsage);
    }

}
