package app.controller.commands.find;

import app.view.View;
import app.view.find.FindByActorView;
import app.view.find.FindByDirectorView;
import app.view.find.FindByGenreView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FindUserCommands {
    public final HashMap<Class<? extends View>, List<Boolean>> commands = new HashMap<>() {{
        put(FindByActorView.class, new ArrayList<>(Arrays.asList(true, false)));
        put(FindByGenreView.class, new ArrayList<>(Arrays.asList(true, false)));
        put(FindByDirectorView.class, new ArrayList<>(Arrays.asList(true, false)));
    }};
}
