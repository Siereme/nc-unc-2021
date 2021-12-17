package controller.commands.find;

import view.View;
import view.find.FindByActorView;
import view.find.FindByDirectorView;
import view.find.FindByGenreView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FindUserCommands {
    public final HashMap<Class<? extends View>, List<Boolean>> commands = new HashMap<Class<? extends View>, List<Boolean>>() {{
        put(FindByActorView.class, new ArrayList<Boolean>(Arrays.asList(true, false)));
        put(FindByGenreView.class, new ArrayList<Boolean>(Arrays.asList(true, false)));
        put(FindByDirectorView.class, new ArrayList<Boolean>(Arrays.asList(true, false)));
    }};
}
