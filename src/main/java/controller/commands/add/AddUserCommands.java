package controller.commands.add;

import view.View;
import view.add.AddActorView;
import view.add.AddDirectorView;
import view.add.AddFilmView;
import view.add.AddGenreView;

import java.util.HashMap;

/** Класс команд добавления сущностей, включает в себя 1) Добавление фильмов 2) Добавление жанров
 * 3) Добавление Актеров 4) Добавление Режисеров
 * @author Sergey
 * @version 1.0
 * */
public class AddUserCommands {
    public HashMap<Class<? extends View>, Boolean> commands = new HashMap<Class<? extends View>, Boolean>()
        {{
            put(AddFilmView.class, false);
            put(AddGenreView.class, true);
            put(AddActorView.class, true);
            put(AddDirectorView.class, true);
        }};
}
