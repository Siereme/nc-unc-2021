package app.controller.commands.add;

import app.view.View;
import app.view.add.AddActorView;
import app.view.add.AddDirectorView;
import app.view.add.AddFilmView;
import app.view.add.AddGenreView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/** Класс команд добавления сущностей, включает в себя 1) Добавление фильмов 2) Добавление жанров
 * 3) Добавление Актеров 4) Добавление Режисеров
 * @author Sergey
 * @version 1.0
 * */
public class AddUserCommands {
    public final HashMap<Class<? extends View>, List<Boolean>> commands = new HashMap<>() {{
        put(AddFilmView.class, new ArrayList<>(Arrays.asList(true, false)));
        put(AddGenreView.class, new ArrayList<>(Collections.singletonList(true)));
        put(AddActorView.class, new ArrayList<>(Collections.singletonList(true)));
        put(AddDirectorView.class, new ArrayList<>(Collections.singletonList(true)));
    }};
}
