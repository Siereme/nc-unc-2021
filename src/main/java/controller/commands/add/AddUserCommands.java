package controller.commands.add;

import view.View;
import view.add.AddActorView;
import view.add.AddDirectorView;
import view.add.AddFilmView;
import view.add.AddGenreView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/** Класс команд добавления сущностей, включает в себя 1) Добавление фильмов 2) Добавление жанров
 * 3) Добавление Актеров 4) Добавление Режисеров
 * @author Sergey
 * @version 1.0
 * */
public class AddUserCommands {
    public HashMap<Class<? extends View>, List<Boolean>> commands = new HashMap<Class<? extends View>, List<Boolean>>()
        {{
            put(AddFilmView.class, new ArrayList<Boolean>(Arrays.asList(true, false)));
            put(AddGenreView.class, new ArrayList<Boolean>(Arrays.asList(true)));
            put(AddActorView.class, new ArrayList<Boolean>(Arrays.asList(true)));
            put(AddDirectorView.class, new ArrayList<Boolean>(Arrays.asList(true)));
        }};
}
