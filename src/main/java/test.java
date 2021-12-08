import com.fasterxml.jackson.databind.ObjectMapper;
import model.Actor.Actor;
import model.Director.Director;
import model.Film.Film;
import model.Genre.Genre;
import repository.*;
import view.MainMenuView;

import java.io.IOException;

public class test {
    // TODO добавление удаление поиск просмотр


    public static void main(String[] argv) {

        //        Director director1 = new Director("Director1", "1987");
        //        director1.addFilm(new Film("Film1", new Date()));
        //        director1.addFilm(new Film("Film2", new Date()));
        //        director1.addFilm(new Film("Film3", new Date()));
        //
        //        Director director2 = new Director("Director2", "1964");
        //        Director director3 = new Director("Director3", "1973");
        //
        //        Directors directors = new Directors(new LinkedList<Director>(Arrays.asList(director1, director2, director3)));
        //
        //        Director director4 = new Director("Director4", "1944");
        //        directors.set(1, director4);
        //
        //        if (directors.size() != 0) {
        //            while (true) {
        //                cls();
        //                System.out.println(directors);
        //                Scanner in = new Scanner(System.in);
        //                int rightBorder = directors.size() - 1;
        //                System.out.println("Select director to get more info ... from [0," + rightBorder + "] or -1 to exit");
        //                int num = in.nextInt();
        //                if(check(num, directors.size() - 1)){
        //                    cls();
        //                    System.out.println(directors.get(num).toString() + "\n");
        //                    for(Film film : directors.get(num).getFilms()){
        //                        System.out.println(film);
        //                    }
        //                    System.out.println("Select film to get more info ... from [0," + rightBorder + "] or -1 to exit");
        //                    int numFilm = in.nextInt();
        //                    if(check(numFilm, directors.get(num).getCountFilms() - 1)){
        //                        System.out.println(directors.get(num).getFilms().get(numFilm) + "\n");
        //                    }
        //                }
        //                pressAnyKeyToContinue();
        //
        //            }
        //        } else {
        //            System.out.println("Sorry but films are out of stock");
        //            pressAnyKeyToContinue();
        //        }

        //        MainMenuView mainMenuView = new MainMenuView();
        //        mainMenuView.display();

        FilmsRepository filmsRepository = new FilmsRepository(
                new Film("Film1"),
                new Film("Film2"),
                new Film("Film3"),
                new Film("Film4"),
                new Film("Film5"),
                new Film("Film6"),
                new Film("Film7"),
                new Film("Film8"),
                new Film("Film9"),
                new Film("Film10")
        );
        try {
            filmsRepository.serialize(new ObjectMapper());
            System.out.println(filmsRepository.deserialize(new ObjectMapper()).get(0).getTittle());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ActorRepository actorRepository = new ActorRepository(
                new Actor("Actor1"),
                new Actor("Actor2"),
                new Actor("Actor3"),
                new Actor("Actor4"),
                new Actor("Actor5"),
                new Actor("Actor6"),
                new Actor("Actor7"),
                new Actor("Actor8"),
                new Actor("Actor9"),
                new Actor("Actor10")
        );
        try {
            actorRepository.serialize(new ObjectMapper());
            System.out.println(actorRepository.deserialize(new ObjectMapper()).get(0).getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        DirectorRepository directorRepository = new DirectorRepository(
                new Director("Director1"),
                new Director("Director2"),
                new Director("Director3"),
                new Director("Director4"),
                new Director("Director5"),
                new Director("Director6"),
                new Director("Director7"),
                new Director("Director8"),
                new Director("Director9"),
                new Director("Director10")
        );
        try {
            directorRepository.serialize(new ObjectMapper());
            System.out.println(directorRepository.deserialize(new ObjectMapper()).get(0).getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        GenreRepository genreRepository = new GenreRepository(
                new Genre("Genre1"),
                new Genre("Genre2"),
                new Genre("Genre3"),
                new Genre("Genre4"),
                new Genre("Genre5"),
                new Genre("Genre6"),
                new Genre("Genre7"),
                new Genre("Genre8"),
                new Genre("Genre9"),
                new Genre("Genre10")
        );
        try {
            genreRepository.serialize(new ObjectMapper());
            System.out.println(genreRepository.deserialize(new ObjectMapper()).get(0).getTittle());
        } catch (IOException e) {
            e.printStackTrace();
        }

        UserRepository userRepository = new UserRepository();
        try {
            System.out.println(userRepository.deserialize(new ObjectMapper()));
        } catch (IOException e) {
            e.printStackTrace();
        }


        MainMenuView mainMenuView = new MainMenuView();
        mainMenuView.display();

    }
}
