package controller.commands.show;

import view.View;
import view.show.ShowAllActorsView;
import view.show.ShowAllDirectorsView;
import view.show.ShowAllFilmsView;
import view.show.ShowAllGenresView;

import java.util.HashMap;

/** Класс команд "показать все сущности" Включает в себя команды 1) Показать все фильмы 2) Показать всех режиссеров
 * 3) Показать всех актеров 4) Показать все жанры
 * @author Sergey
 * @version 1.0
 * */
public class ShowUserCommands {
    public HashMap<Class<? extends View>, Boolean> commands = new HashMap<Class<? extends View>, Boolean>() {{
        put(ShowAllActorsView.class, true);
        put(ShowAllDirectorsView.class, true);
        put(ShowAllFilmsView.class, true);
        put(ShowAllGenresView.class, true);
    }};
}
