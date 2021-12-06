import view.MainMenuView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

// TODO добавить поле первичный ключ
// TODO жанры...
// TODO контролеры, вью
public class test {

    static private int readInt(int left, int right, String message, Scanner in) {
        while (true) {
            System.out.println(message + "from [" + left + "," + right + "]");
            int res = in.nextInt();
            if (res >= left && res <= right) {
                return res;
            }
        }
    }

    static private Date readDate(SimpleDateFormat dateFormat, Scanner in) {
        Date date = null;
        while (true) {
            System.out.println("Enter date in format " + dateFormat.toPattern() + "\n");
            String dateStr = in.next();
            try {
                date = dateFormat.parse(dateStr);
            } catch (ParseException e) {
                System.out.println(e.toString());
                continue;
            }
            return date;
        }
    }

/*    static private Genres readGenres(Genres availableGenres, Scanner in) {
        // копия жанров, будет изменяться, например когда пользователь выберет жанр, мы его удалим из списка
        // для ликвидации возможных ошибок при следующем выборе
        Genres copy = (Genres) availableGenres.clone();
        Genres res = new Genres();
        while (true) {
            System.out.println(copy);
            int ind = readInt(-1, copy.size() - 1, "Select model.Genre or -1 to exit ", in);
            if (ind == -1) {
                return res;
            } else {
                // перемещаем из одного списка в другой
                res.addGenre(copy.remove(ind));
            }
        }
    }*/

/*    static private Boolean check(int number, int size) {
        if (number < 0 | number > size) {
            cls();
            return false;
        }
        return true;
    }*/

    public static void main(String[] argv) {
        //        Films films = new Films();
        //        Genres genres = new Genres("crime", "horror", "fantasy", "romance", "thriller");
        //        films.addFilm(new Film("First film", new Date(), genres.get(1), genres.get(2)));
        //        films.addFilm(new Film("Second film", new Date(), genres.get(2), genres.get(3)));
        //        films.addFilm(new Film("Third film", new Date(), genres.get(0)));
        //
        //        if (films.size() != 0) {
        //            while (true) {
        //                cls();
        //                Scanner in = new Scanner(System.in);
        //                int num = readInt(0, 3, "Select genre - 1\nAdd new model.Film - 2\nCheck all films - 3\nexit - 0\n", in);
        //                if (num == 0) {
        //                    return;
        //                } else if (num == 1) {
        //                    cls();
        //                    if (genres.size() != 0) {
        //                        System.out.println(genres);
        //                        int right = genres.size();
        //                        int genreInd = readInt(0, right - 1, "Select genre", in);
        //                        // Films filmsByGenre = films.getFilmsByGenre(genres.get(genreInd));
        //                        cls();
        //                        // System.out.println(filmsByGenre);
        //                        pressAnyKeyToContinue();
        //                    } else {
        //                        System.out.println("There are no genres available");
        //                        pressAnyKeyToContinue();
        //                    }
        //
        //                } else if (num == 2) {
        //                    System.out.println("Enter a tittle");
        //                    String tittle = in.next();
        //                    Date date = readDate(new SimpleDateFormat("yyyy/MM/dd"), in);
        //                    Genres genres1 = readGenres(genres, in);
        //                    Film newFilm = new Film(tittle, date, genres1);
        //                    films.addFilm(newFilm);
        //                } else if (num == 3) {
        //                    System.out.println(films);
        //                    pressAnyKeyToContinue();
        //                }
        //            }
        //        } else {
        //            System.out.println("Sorry but films are out of stock");
        //            pressAnyKeyToContinue();
        //        }
/*        Actor actor1 = new Actor("Actor1", "1987");
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
                        System.out.println(actors.get(num).getFilms().get(numFilm).getId() + "\n");
                        System.out.println(actors.get(num).getFilms().get(numFilm) + "\n");
                    }
                }
                pressAnyKeyToContinue();

            }
        } else {
            System.out.println("Sorry but films are out of stock");
            pressAnyKeyToContinue();
        }*/

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

        MainMenuView mainMenuView = new MainMenuView();
        mainMenuView.display();
    }
}
