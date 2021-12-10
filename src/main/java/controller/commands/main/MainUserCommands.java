package controller.commands.main;

import view.View;
import view.add.AddView;
import view.edit.EditView;
import view.find.FindView;
import view.remove.RemoveView;
import view.show.ShowAllView;

import java.util.HashMap;

public class MainUserCommands {
    public HashMap<Class<? extends View>, Boolean> commands = new HashMap<Class<? extends View>, Boolean>()
    {{
        put(AddView.class, true);
        put(EditView.class, true);
        put(FindView.class, true);
        put(RemoveView.class, true);
        put(ShowAllView.class, true);
    }};
}
