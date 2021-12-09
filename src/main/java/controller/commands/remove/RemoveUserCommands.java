package controller.commands.remove;

import view.View;
import view.remove.RemoveActorView;
import view.remove.RemoveDirectorView;
import view.remove.RemoveFilmView;
import view.remove.RemoveGenreView;

import java.util.HashMap;

public class RemoveUserCommands {
    public HashMap<Class<? extends View>, Boolean> commands = new HashMap<Class<? extends View>, Boolean>()
        {{
            put(RemoveFilmView.class, false);
            put(RemoveGenreView.class, true);
            put(RemoveActorView.class, true);
            put(RemoveDirectorView.class, true);
        }};
}
