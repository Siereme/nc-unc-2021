package app.controller.comandsFX.remove;

import app.viewFX.IView;
import app.viewFX.menu.add.addFilm.AddFilm;
import app.viewFX.menu.remove.removeActor.RemoveActor;
import app.viewFX.menu.remove.removeDirector.RemoveDirector;
import app.viewFX.menu.remove.removeFilm.RemoveFilm;
import app.viewFX.menu.remove.removeGenre.RemoveGenre;

import java.util.*;

public class RemoveUserCommands {
    public final HashMap<Class<? extends IView>, List<Boolean>> commands = new HashMap<Class<? extends IView>, List<Boolean>>()
    {{
        put(RemoveFilm.class, new ArrayList<>(Arrays.asList(true, false)));
        put(RemoveActor.class, new ArrayList<>(Collections.singletonList(true)));
        put(RemoveDirector.class, new ArrayList<>(Collections.singletonList(true)));
        put(RemoveGenre.class, new ArrayList<>(Collections.singletonList(true)));
    }};
}
