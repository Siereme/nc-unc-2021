import Film.Film;
import Films.Films;
import Genres.Genres;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

// TODO вывод фильмов по жанрам, добавление фильмов пользователем(админом)
// TODO Добавить функцию проверки ввода
// TODO проверить работу функции readDate
public class test {
    static private void pressAnyKeyToContinue() {
        System.out.println("Press Enter key to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static private void cls() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

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

    static private Genres readGenres(Genres availableGenres, Scanner in) {
        // копия жанров, будет изменяться, например когда пользователь выберет жанр, мы его удалим из списка
        // для ликвидации возможных ошибок при следующем выборе
        Genres copy = (Genres) availableGenres.clone();
        Genres res = new Genres();
        while (true) {
            System.out.println(copy);
            int ind = readInt(-1, copy.size() - 1, "Select Genre or -1 to exit ", in);
            if (ind == -1) {
                return res;
            } else {
                // перемещаем из одного списка в другой
                res.addGenre(copy.remove(ind));
            }
        }
    }

    public static void main(String[] argv) {
        Films films = new Films();
        Genres genres = new Genres("crime", "horror", "fantasy", "romance", "thriller");
        films.addFilm(new Film("First film", new Date(), genres.get(1), genres.get(2)));
        films.addFilm(new Film("Second film", new Date(), genres.get(2), genres.get(3)));
        films.addFilm(new Film("Third film", new Date(), genres.get(0)));

        if (films.size() != 0) {
            while (true) {
                cls();
                Scanner in = new Scanner(System.in);
                int num = readInt(0, 3, "Select genre - 1\nAdd new Film - 2\nCheck all films - 3\nexit - 0\n", in);
                if (num == 0) {
                    return;
                } else if (num == 1) {
                    cls();
                    if (genres.size() != 0) {
                        System.out.println(genres);
                        int right = genres.size();
                        int genreInd = readInt(0, right - 1, "Select genre", in);
                        Films filmsByGenre = films.getFilmsByGenre(genres.get(genreInd));
                        cls();
                        System.out.println(filmsByGenre);
                        pressAnyKeyToContinue();
                    } else {
                        System.out.println("There are no genres available");
                        pressAnyKeyToContinue();
                    }

                } else if (num == 2) {
                    System.out.println("Enter a tittle");
                    String tittle = in.next();
                    Date date = readDate(new SimpleDateFormat("yyyy/MM/dd"), in);
                    Genres genres1 = readGenres(genres, in);
                    Film newFilm = new Film(tittle, date, genres1);
                    films.addFilm(newFilm);
                } else if (num == 3) {
                    System.out.println(films);
                    pressAnyKeyToContinue();
                }
            }
        } else {
            System.out.println("Sorry but films are out of stock");
            pressAnyKeyToContinue();
        }
    }
}
