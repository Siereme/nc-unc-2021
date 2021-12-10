package view;

import controller.commands.Commands;
import controller.commands.main.MainUserCommands;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс главное меню
 * @author vasily
 * @version 1.0
 * */
public class MainMenuView extends View {

    /**
     * @see View
     * */
    public void display() {
        MainUserCommands commands = new MainUserCommands();

        List<View> userCommands = commands.commands.entrySet().stream()
                .filter(e ->  e.getValue() == true)
                .map(x-> {
                    try {
                        return x.getKey().getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());

        boolean show = true;
        while (show) {
            System.out.println("------Main Menu------");
            for (int i = 0; i < userCommands.size(); i++) {
                System.out.println((i + 1) + ". " + userCommands.get(i).getName());
            }
            System.out.println((userCommands.size() + 1) + ". Exit");

            int option = getOption();

            if(option < 1 || option > userCommands.size()){
                break;
            }

            new Commands(userCommands.get(option - 1)).execute();
        }
    }

    /**
     * @see View
     * */
    @Override
    public void showMessage(String messsage) {

    }

}
