package com.gmail.viktor.yuryev.mvc.console.view;

public class MainMenuView extends View {
    public final FilmsView filmsView = new FilmsView();

    public void display() {
        boolean show = true;
        while (show) {
            System.out.println();
            System.out.println("------MAIN MENU------");
            System.out.println("1. Films Menu");
            System.out.println("4. Exit");

            int option = getOption();
            switch (option) {
                case 1:
                    filmsView.display();
                    break;
                case 4:
                    show = false;
                default:
                    break;
            }
        }
    }

    @Override
    public void showMessage(String messsage) {

    }

}
