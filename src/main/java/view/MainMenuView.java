package view;

import controller.commands.main.MainUserCommands;
import model.User.IUser;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс главное меню
 * @author vasily
 * @version 1.0
 * */
public class MainMenuView extends View {
    public IUser currentUser;

    public MainMenuView(IUser currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * @see View
     * */
    public void display() {
        MainUserCommands commands = new MainUserCommands();
        List<View> userCommands = commands.commands.entrySet().stream()
                .filter(e ->  e.getValue().contains(this.currentUser.isAdmin()))
                .map(x-> {
                    try {
                        return x.getKey().getConstructor(IUser.class).newInstance(this.currentUser);
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

            userCommands.get(option - 1).display();
        }
    }

    /**
     * @see View
     * */
    @Override
    public void showMessage(String messsage) {

    }

}
