package controller.commands.remove;

import view.View;
import view.remove.RemoveActorView;
import view.remove.RemoveDirectorView;
import view.remove.RemoveFilmView;
import view.remove.RemoveGenreView;

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
    public final HashMap<Class<? extends View>, List<Boolean>> commands = new HashMap<Class<? extends View>, List<Boolean>>()
        {{
            put(RemoveFilmView.class, new ArrayList<>(Arrays.asList(true, false)));
            put(RemoveGenreView.class, new ArrayList<>(Collections.singletonList(true)));
            put(RemoveActorView.class, new ArrayList<>(Collections.singletonList(true)));
            put(RemoveDirectorView.class, new ArrayList<>(Collections.singletonList(true)));
        }};
}
