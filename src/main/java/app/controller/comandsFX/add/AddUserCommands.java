package app.controller.comandsFX.add;

import app.viewFX.IView;
import app.viewFX.menu.add.addFilm.AddFilm;

import java.util.*;

public class AddUserCommands {
    public final HashMap<Class<? extends IView>, List<Boolean>> commands = new HashMap<Class<? extends IView>, List<Boolean>>()
    {{
        put(AddFilm.class, new ArrayList<>(Arrays.asList(true, false)));
    }};
}
