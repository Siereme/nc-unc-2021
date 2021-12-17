package controller.commands.edit;

import view.View;
import view.edit.actor.EditActorMenuView;
import view.edit.director.EditDirectorMenuView;
import view.edit.film.EditFilmMenuView;
import view.edit.genre.EditGenreMenuView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/** Класс команд редактирования, включает в себя 1) Редактирование Фильмов 2) Редактирование Жанров 3) Редактирование Актеров
 * 4) Редактирование Режиссеров
 * @author Sergey
 * @version 1.0
 * */
public class EditUserCommands {
    public final HashMap<Class<? extends View>, List<Boolean>> commands = new HashMap<Class<? extends View>, List<Boolean>>()
    {{
        put(EditFilmMenuView.class, new ArrayList<>(Arrays.asList(true, false)));
        put(EditGenreMenuView.class, new ArrayList<>(Collections.singletonList(true)));
        put(EditActorMenuView.class, new ArrayList<>(Collections.singletonList(true)));
        put(EditDirectorMenuView.class, new ArrayList<>(Collections.singletonList(true)));
    }};
}
