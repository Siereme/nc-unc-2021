package view.find;

import controller.commands.find.FindUserCommands;
import model.User.IUser;
import view.IView;
import view.View;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

/** Класс меню команд поиска
 * @author Vasiliy
 * @version 1.0
 * */
public class FindView extends View implements IView {
    private String name = "Find film by ...";
    private final IUser currentUser;

    public FindView(IUser currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public void display() {
        FindUserCommands commands = new FindUserCommands();

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
            System.out.println("------Find Menu------");
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
