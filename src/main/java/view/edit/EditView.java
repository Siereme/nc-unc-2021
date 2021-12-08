package view.edit;

import controller.commands.edit.EditCommands;
import controller.commands.edit.EditUserCommands;
import view.IView;
import view.View;
import view.edit.actor.EditActorMenuView;
import view.edit.director.EditDirectorMenuView;
import view.edit.film.EditFilmMenuView;
import view.edit.genre.EditGenreMenuView;

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
                        return x.getKey().newInstance();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());

        boolean show = true;
        while (show) {
            System.out.println("------Add Menu------");
            for (int i = 0; i < userCommands.size(); i++) {
                System.out.println((i + 1) + ". " + userCommands.get(i).getName());
            }
            System.out.println((userCommands.size() + 1) + ". Exit");

            int option = getOption();

            if(option < 1 || option > userCommands.size()){
                break;
            }

            new EditCommands(userCommands.get(option)).execute();
        }
    }

    @Override
    public void showMessage(String messsage) {

    }
}
