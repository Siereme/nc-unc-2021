package com.gmail.viktor.yuryev.mvc.console.view;

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
}
