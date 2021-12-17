package controller.commands.main;

import view.View;
import view.add.AddView;
import view.edit.EditView;
import view.find.FindView;
import view.remove.RemoveView;
import view.show.ShowAllView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainUserCommands {
    public final HashMap<Class<? extends View>, List<Boolean>> commands = new HashMap<Class<? extends View>, List<Boolean>>()
    {{
        put(AddView.class, new ArrayList<>(Arrays.asList(true, false)));
        put(EditView.class, new ArrayList<>(Arrays.asList(true, false)));
        put(FindView.class, new ArrayList<>(Arrays.asList(true, false)));
        put(RemoveView.class, new ArrayList<>(Arrays.asList(true, false)));
        put(ShowAllView.class, new ArrayList<>(Arrays.asList(true, false)));
    }};
}
