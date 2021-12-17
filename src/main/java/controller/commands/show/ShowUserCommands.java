package controller.commands.show;

import view.View;
import view.show.ShowAllActorsView;
import view.show.ShowAllDirectorsView;
import view.show.ShowAllFilmsView;
import view.show.ShowAllGenresView;

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
        put(ShowAllActorsView.class, new ArrayList<Boolean>(Arrays.asList(true, false)));
        put(ShowAllDirectorsView.class, new ArrayList<Boolean>(Arrays.asList(true, false)));
        put(ShowAllFilmsView.class, new ArrayList<Boolean>(Arrays.asList(true, false)));
        put(ShowAllGenresView.class, new ArrayList<Boolean>(Arrays.asList(true, false)));
    }};
}
