package view.show;

import controller.commands.show.ShowUserCommands;
import model.User.IUser;
import view.IView;
import view.View;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public class ShowAllEntitiesView extends View implements IView {
    private final String name = "Show all entities";
    private final IUser currentUser;

    public ShowAllEntitiesView(IUser currentUser) {
        this.currentUser = currentUser;
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

        boolean show = true;
        while (show) {
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
        return this.name;
    }

    @Override
    public void showMessage(String messsage) {

    }
}
