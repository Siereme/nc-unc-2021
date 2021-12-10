package controller.commands.remove;

import view.View;
import view.remove.RemoveActorView;
import view.remove.RemoveDirectorView;
import view.remove.RemoveFilmView;
import view.remove.RemoveGenreView;

import java.util.HashMap;

/** Класс команд удаления сущностей, включает в себя 1) Удаление фильмов 2) Удаление жанров
 * 3) Удаление Актеров 4) Удаление Режиссеров
 * @author Sergey
 * @version 1.0
 * */
public class RemoveUserCommands {
    public HashMap<Class<? extends View>, Boolean> commands = new HashMap<Class<? extends View>, Boolean>()
        {{
            put(RemoveFilmView.class, false);
            put(RemoveGenreView.class, true);
            put(RemoveActorView.class, true);
            put(RemoveDirectorView.class, true);
        }};
}
