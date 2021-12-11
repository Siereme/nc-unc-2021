package view.remove;

import controller.commands.remove.RemoveUserCommands;
import model.User.IUser;
import view.IView;
import view.View;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

/** Класс команд удаления сущностей
 * @author Sergey, Vasiliy
 * @version 1.0
 * */
public class RemoveView extends View implements IView {
    private String name = "Delete anything ...";
    private final IUser currentUser;

    public RemoveView(IUser currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public void display() {

        RemoveUserCommands commands = new RemoveUserCommands();

        List<View> userCommands = commands.commands.entrySet().stream()
                .filter(e -> e.getValue().contains(this.currentUser.isAdmin()))
                .map(x -> {
                    try {
                        return x.getKey().getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());

        while (true) {
            System.out.println("------Delete Menu------");
            for (int i = 0; i < userCommands.size(); i++) {
                System.out.println((i) + ". " + userCommands.get(i).getName());
            }
            System.out.println("-1. Exit");

            int option = getOption();
            if (option == -1) {
                break;
            }

            if (option >= 0 && option < userCommands.size()) {
                userCommands.get(option).display();
            }
        }
    }

    public String getName(){
        return this.name;
    }

    @Override
    public void showMessage(String messsage) {

    }
}
