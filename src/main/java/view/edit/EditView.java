package view.edit;

import controller.commands.Commands;
import controller.commands.edit.EditUserCommands;
import view.IView;
import view.View;
import view.edit.actor.EditActorMenuView;
import view.edit.director.EditDirectorMenuView;
import view.edit.film.EditFilmMenuView;
import view.edit.genre.EditGenreMenuView;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public class EditView extends View implements IView {
    private final EditFilmMenuView editFilmMenuView = new EditFilmMenuView();
    private final EditActorMenuView editActorView = new EditActorMenuView();
    private final EditGenreMenuView editGenreView = new EditGenreMenuView();
    private final EditDirectorMenuView editDirectorView = new EditDirectorMenuView();

    @Override
    public void display() {
        EditUserCommands commands = new EditUserCommands();

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
            System.out.println("------Edit Menu------");
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

    @Override
    public void showMessage(String messsage) {

    }
}
