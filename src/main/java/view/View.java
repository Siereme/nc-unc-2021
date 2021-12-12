package view;

import controller.IEntityController;
import model.IEntity;

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
        Date date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        while (true) {
            System.out.println("Enter date in format " + dateFormat.toPattern() + "\n");
            String dateStr = input.nextLine();
            try {
                date = dateFormat.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
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


    protected LinkedList<String> getEntitiesId(IEntityController<? extends IEntity> entityController, String message) {
        LinkedList<String> entities = entityController.getEntities();
        while (true) {
            System.out.println(message);
            System.out.println("-1. Exit");
            System.out.println(entityController);
            int option = getOption();
            if (option == -1) {
                break;
            }
            if (option < 0 || option >= entityController.size()) {
                continue;
            }
            IEntity entity = entityController.getEntity(option);
            if (!entities.contains(entity.getId())) {
                entities.add(entity.getId());
            }
        }
        return entities;
    }

    /** Функция получает подтверждение от пользователя
     * Пользователю выводится сообщение "Confirm deletion (yes/no): "
     * если пользователь отвечает "yes" возвращается значение true
     * если пользователь отвечает "no" возвращается значение false
     * @return true, если пользователь отвечает "yes" или "y", false если пользователь отвечает "no" или "n"
     * */
    protected boolean getConfirm() {
        while (true) {
            String confirm = getStr("Confirm deletion (yes/no): ");
            if (Objects.equals(confirm, "y") || Objects.equals(confirm, "yes")) {
                return true;
            } else if (Objects.equals(confirm, "no") || Objects.equals(confirm, "n")) {
                return false;
            }
        }
    }

    /** В данный момент функция не используется */
    public String getName() {
        return null;
    }

}
