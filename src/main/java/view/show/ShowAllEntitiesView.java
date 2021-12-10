package view.show;

import controller.commands.Commands;
import controller.commands.show.ShowUserCommands;
import view.IView;
import view.View;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public class ShowAllEntitiesView extends View implements IView {
    private final String name = "Show all entities";
    @Override
    public void display() {
        ShowUserCommands commands = new ShowUserCommands();

        List<View> userCommands = commands.commands.entrySet().stream().filter(e -> e.getValue()).map(x -> {
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
                new Commands(userCommands.get(option - 1)).execute();
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
