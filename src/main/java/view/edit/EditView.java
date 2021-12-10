package view.edit;

import controller.commands.Commands;
import controller.commands.edit.EditUserCommands;
import view.IView;
import view.View;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

/** Класс Меню команд редактирования сущностей
 * @author Sergey, Vasiliy
 * @version 1.0
 * */
public class EditView extends View implements IView {

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
