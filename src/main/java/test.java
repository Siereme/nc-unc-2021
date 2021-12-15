import model.user.IUser;
import view.MainMenuView;
import view.authorization.login.LoginView;

public class test {

    public static void main(String[] argv) {
/*        GenreRepository mergeGenres = new GenreRepository(
                new Genre("Genre1"),
                new Genre("Genre2"),
                new Genre("Genre3"),
                new Genre("Genre4"),
                new Genre("Genre5"),
                new Genre("Genre6"),
                new Genre("Genre7"),
                new Genre("Genre8"),
                new Genre("Genre9")
        );
        String genre1 = new File("src/main/resources/test/Genre1.json").getAbsolutePath();
        String genre2 = new File("src/main/resources/test/Genre2.json").getAbsolutePath();
        try {
            mergeGenres.serialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mergeGenres.mergeFiles(genre1);

        ActorRepository mergeActors = new ActorRepository(
                new Actor("Actor1"),
                new Actor("Actor2"),
                new Actor("Actor3"),
                new Actor("Actor4"),
                new Actor("Actor5"),
                new Actor("Actor6"),
                new Actor("Actor7"),
                new Actor("Actor8"),
                new Actor("Actor9")
        );
        String actor1 = new File("src/main/resources/test/Actor1.json").getAbsolutePath();
        String actor2 = new File("src/main/resources/test/Actor2.json").getAbsolutePath();
        try {
            mergeActors.serialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mergeActors.mergeFiles(actor1);

        DirectorRepository mergeDirectors = new DirectorRepository(
                new Director("Director1"),
                new Director("Director2"),
                new Director("Director3"),
                new Director("Director4"),
                new Director("Director5"),
                new Director("Director6"),
                new Director("Director7"),
                new Director("Director8"),
                new Director("Director9")
        );
        String director1 = new File("src/main/resources/test/Director1.json").getAbsolutePath();
        String director2 = new File("src/main/resources/test/Director2.json").getAbsolutePath();
        try {
            mergeDirectors.serialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mergeDirectors.mergeFiles(director1);

        FilmsRepository mergeFilms = new FilmsRepository(
                new Film("Film1"),
                new Film("Film2"),
                new Film("Film3"),
                new Film("Film4"),
                new Film("Film5"),
                new Film("Film6"),
                new Film("Film7"),
                new Film("Film8"),
                new Film("Film9")
        );
        String film1 = new File("src/main/resources/test/Film1.json").getAbsolutePath();
        String film2 = new File("src/main/resources/test/Film2.json").getAbsolutePath();
        try {
            mergeFilms.serialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mergeFilms.mergeFiles(film1);
//
        UserRepository mergeUsers = new UserRepository();
        String user1 = new File("src/main/resources/test/User1.json").getAbsolutePath();
        String user2 = new File("src/main/resources/test/User2.json").getAbsolutePath();
        mergeUsers.mergeFiles(user1);*/

/*        AuthorithationView authorithationView = new AuthorithationView();
        authorithationView.display();
        IUser user =  authorithationView.getCurrentUser();

        MainMenuView mainMenuView = new MainMenuView();
        mainMenuView.display();*/


        LoginView login = new LoginView();
        login.display();
        IUser currentUser = login.getCurrentUser();

        MainMenuView mainMenu = new MainMenuView(currentUser);
        mainMenu.display();
    }
}
