package controller.commands.edit;

import view.View;
import view.edit.actor.EditActorMenuView;
import view.edit.director.EditDirectorMenuView;
import view.edit.film.EditFilmMenuView;
import view.edit.genre.EditGenreMenuView;

import java.util.HashMap;

public class EditUserCommands {
    public HashMap<Class<? extends View>, Boolean> commands = new HashMap<Class<? extends View>, Boolean>()
    {{
        put(EditFilmMenuView.class, false);
        put(EditGenreMenuView.class, true);
        put(EditActorMenuView.class, true);
        put(EditDirectorMenuView.class, true);
    }};
}
