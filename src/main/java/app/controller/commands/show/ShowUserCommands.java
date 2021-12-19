package app.controller.commands.show;

import app.view.View;
import app.view.show.ShowAllActorsView;
import app.view.show.ShowAllDirectorsView;
import app.view.show.ShowAllFilmsView;
import app.view.show.ShowAllGenresView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/** Класс команд "показать все сущности" Включает в себя команды 1) Показать все фильмы 2) Показать всех режиссеров
 * 3) Показать всех актеров 4) Показать все жанры
 * @author Sergey
 * @version 1.0
 * */
public class ShowUserCommands {
    public final HashMap<Class<? extends View>, List<Boolean>> commands = new HashMap<Class<? extends View>, List<Boolean>>() {{
        put(ShowAllActorsView.class, new ArrayList<>(Arrays.asList(true, false)));
        put(ShowAllDirectorsView.class, new ArrayList<>(Arrays.asList(true, false)));
        put(ShowAllFilmsView.class, new ArrayList<>(Arrays.asList(true, false)));
        put(ShowAllGenresView.class, new ArrayList<>(Arrays.asList(true, false)));
    }};
}
