package controller.commands;

import view.View;

/** Класс Команды
 * @author Sergey
 * @version 1.0
 * */
public class Commands implements Command {
    private final String name;
    private final View view;

    public Commands(View view) {
        this.name = view.getName();
        this.view = view;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void execute() {
        view.display();
    }
}
