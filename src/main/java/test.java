import Actor.Actor;
import Actors.Actors;
import Film.Film;
import Films.*;

import java.io.IOException;
import java.util.*;

public class test {
    static private void pressAnyKeyToContinue()
    {
        System.out.println("Press Enter key to continue...");
        try
        {
            System.in.read();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    static private void cls(){
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    static private Boolean check(int number, int size){
        if(number < 0 | number > size){
            cls();
            return false;
        }
        return true;
    }

    public static void main(String[] argv) {
//        Films films = new Films();
//        films.addFilm(new Film("First film", new Date()));
//        films.addFilm(new Film("Second film", new Date()));
//        films.addFilm(new Film("Third film", new Date()));
//
//        if (films.size() != 0) {
//            while (true) {
//                cls();
//                System.out.println(films);
//                Scanner in = new Scanner(System.in);
//                int rightBorder = films.size() - 1;
//                System.out.println("Select film to get more info ... from [0," + rightBorder + "] or -1 to exit");
//                int num = in.nextInt();
//                if (num == -1) {
//                    return;
//                } else if (num >= films.size()) {
//                    cls();
//                }
//                else {
//                    cls();
//                    System.out.println(films.get(num));
//                    pressAnyKeyToContinue();
//                }
//            }
//        } else {
//            System.out.println("Sorry but films are out of stock");
//            pressAnyKeyToContinue();
//        }

        Actor actor1 = new Actor("Actor1", "1987");
        actor1.addFilm(new Film("Film1", new Date()));
        actor1.addFilm(new Film("Film2", new Date()));
        actor1.addFilm(new Film("Film3", new Date()));

        Actor actor2 = new Actor("Actor2", "1964");
        Actor actor3 = new Actor("Actor3", "1973");
        Actors actors = new Actors(new LinkedList<Actor>(Arrays.asList(actor1, actor2, actor3)));

        Actor actor4 = new Actor("Actor4", "1944");
        actors.set(1, actor4);

        if (actors.size() != 0) {
            while (true) {
                cls();
                System.out.println(actors);
                Scanner in = new Scanner(System.in);
                int rightBorder = actors.size() - 1;
                System.out.println("Select actor to get more info ... from [0," + rightBorder + "] or -1 to exit");
                int num = in.nextInt();
                if(check(num, actors.size() - 1)){
                    cls();
                    System.out.println(actors.get(num).toString() + "\n");
                    for(Film film : actors.get(num).getFilms()){
                        System.out.println(film);
                    }
                    System.out.println("Select film to get more info ... from [0," + rightBorder + "] or -1 to exit");
                    int numFilm = in.nextInt();
                    if(check(numFilm, actors.get(num).getCountFilms() - 1)){
                        System.out.println(actors.get(num).getFilms().get(numFilm) + "\n");
                    }
                }
                pressAnyKeyToContinue();

            }
        } else {
            System.out.println("Sorry but films are out of stock");
            pressAnyKeyToContinue();
        }

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
    }
}
