package view.add;

import controller.commands.Commands;
import controller.commands.add.AddUserCommands;
import view.IView;
import view.View;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public class AddView extends View implements IView {
    AddFilmView addFilmView = new AddFilmView();
    AddGenreView addGenreView = new AddGenreView();
    AddActorView addActorView = new AddActorView();
    AddDirectorView addDirectorView = new AddDirectorView();

    @Override
    public void display() {
        AddUserCommands commands = new AddUserCommands();

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
            System.out.println("------Add Menu------");
            for(int i = 0; i < userCommands.size(); i++){
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

    @Override
    public void showMessage(String messsage) {

    }
}
