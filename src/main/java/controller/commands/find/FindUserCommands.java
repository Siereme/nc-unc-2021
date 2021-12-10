package controller.commands.find;

import view.View;
import view.find.FindByActorView;
import view.find.FindByDirectorView;
import view.find.FindByGenreView;

import java.util.HashMap;

public class FindUserCommands {
    public HashMap<Class<? extends View>, Boolean> commands = new HashMap<Class<? extends View>, Boolean>() {{
        put(FindByActorView.class, true);
        put(FindByGenreView.class, true);
        put(FindByDirectorView.class, true);
    }};
}
