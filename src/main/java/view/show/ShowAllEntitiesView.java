package view.show;

import controller.commands.show.ShowUserCommands;
import model.user.IUser;
import view.IView;
import view.View;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public class ShowAllEntitiesView extends View implements IView {

    public ShowAllEntitiesView(IUser currentUser) {
        super(currentUser);
    }

    @Override
    public void display() {
        ShowUserCommands commands = new ShowUserCommands();

        List<View> userCommands = commands.commands.entrySet().stream()
                .filter(e -> e.getValue().contains(currentUser.isAdmin()))
                .map(x -> {
                    try {
                        return x.getKey().getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());

        while (true) {
            System.out.println("------Show Menu------");
            for (int i = 0; i < userCommands.size(); i++) {
                System.out.println((i) + ". " + userCommands.get(i).getName());
            }
            System.out.println("-1. Exit");

            int option = getOption();

            if (option == -1) {
                break;
            }

            if (option >= 0 && option < userCommands.size()) {
                userCommands.get(option - 1).display();
            }
        }
    }

    public String getName(){
        /** Name of the command*/
        return "Show all entities";
    }

    @Override
    public void showMessage(String messsage) {

    }
}
