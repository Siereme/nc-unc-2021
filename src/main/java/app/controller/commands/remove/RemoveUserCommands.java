package app.controller.commands.remove;

import app.view.View;
import app.view.remove.RemoveActorView;
import app.view.remove.RemoveDirectorView;
import app.view.remove.RemoveFilmView;
import app.view.remove.RemoveGenreView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/** Класс команд удаления сущностей, включает в себя 1) Удаление фильмов 2) Удаление жанров
 * 3) Удаление Актеров 4) Удаление Режиссеров
 * @author Sergey
 * @version 1.0
 * */
public class RemoveUserCommands {
    public final HashMap<Class<? extends View>, List<Boolean>> commands = new HashMap<>() {{
        put(RemoveFilmView.class, new ArrayList<>(Arrays.asList(true, false)));
        put(RemoveGenreView.class, new ArrayList<>(Collections.singletonList(true)));
        put(RemoveActorView.class, new ArrayList<>(Collections.singletonList(true)));
        put(RemoveDirectorView.class, new ArrayList<>(Collections.singletonList(true)));
    }};
}
