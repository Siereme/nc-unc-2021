package controller.commands.add;

import view.View;
import view.add.AddActorView;
import view.add.AddDirectorView;
import view.add.AddFilmView;
import view.add.AddGenreView;

import java.util.HashMap;

public class AddUserCommands {
    public HashMap<Class<? extends View>, Boolean> commands = new HashMap<Class<? extends View>, Boolean>()
        {{
            put(AddFilmView.class, false);
            put(AddGenreView.class, true);
            put(AddActorView.class, true);
            put(AddDirectorView.class, true);
        }};
}
