package app.controller.commands.main;

import app.view.View;
import app.view.add.AddView;
import app.view.edit.EditView;
import app.view.find.FindView;
import app.view.remove.RemoveView;
import app.view.show.ShowAllView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainUserCommands {
    public final HashMap<Class<? extends View>, List<Boolean>> commands = new HashMap<>() {{
        put(AddView.class, new ArrayList<>(Arrays.asList(true, false)));
        put(EditView.class, new ArrayList<>(Arrays.asList(true, false)));
        put(FindView.class, new ArrayList<>(Arrays.asList(true, false)));
        put(RemoveView.class, new ArrayList<>(Arrays.asList(true, false)));
        put(ShowAllView.class, new ArrayList<>(Arrays.asList(true, false)));
    }};
}
