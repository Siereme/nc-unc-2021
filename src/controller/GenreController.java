package controller;

import repository.GenreRepository;

public class GenreController {
    GenreRepository genreRepository = new GenreRepository();

    GenreController() {
        genreRepository = new GenreRepository();
    }

}
