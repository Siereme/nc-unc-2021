package controller.commands.add;

import controller.commands.Command;
import view.View;

public class AddCommands implements Command {
    private final String name;
    private final View view;

    public AddCommands(View view) {
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
