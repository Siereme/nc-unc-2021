package view.add;

import controller.commands.Commands;
import controller.commands.add.AddUserCommands;
import view.IView;
import view.View;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

/** Меню команд добавления данных
 * @author Sergey, Vasiliy
 * @version 1.0
 * */
public class AddView extends View implements IView {

    @Override
    public void display() {
        AddUserCommands commands = new AddUserCommands();

        List<View> userCommands = commands.commands.entrySet().stream().filter(e -> e.getValue()).map(x -> {
            try {
                return x.getKey().getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());

        while (true) {
            System.out.println("------Add Menu------");
            for (int i = 0; i < userCommands.size(); i++) {
                System.out.println((i) + ". " + userCommands.get(i).getName());
            }
            System.out.println("-1. Exit");

            int option = getOption();

            if (option == -1) {
                break;
            }
            if (option >= 0 && option < userCommands.size()) {
                new Commands(userCommands.get(option)).execute();
            }
        }
    }

    @Override
    public void showMessage(String messsage) {

    }
}
