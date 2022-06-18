package com.app.repository;

import com.app.model.film.Film;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ActiveProfiles("test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FilmsRepositoryTest {

    @Autowired
    private FilmsRepository repository;
    private final List<Film> films = new ArrayList<>();

    @BeforeAll
    void init(){
        this.films.addAll(List.of(
                new Film("film1", Date.valueOf("2009-03-28")),
                new Film("film2", Date.valueOf("2009-03-28")),
                new Film("film3", Date.valueOf("2009-03-28"))
        ));
        for (int i = 0; i < films.size(); i++) {
            films.get(i).setId(i + 1);
        }
    }

    @Test
    @Order(1)
    void testFindAll() {
        List<Film> filmsListTest = repository.findAll();

        Assertions.assertEquals(films.size(), filmsListTest.size());
        Assertions.assertEquals(films.get(0).getTittle(), filmsListTest.get(0).getTittle());
        Assertions.assertEquals(films.get(1).getTittle(), filmsListTest.get(1).getTittle());
        Assertions.assertEquals(films.get(2).getTittle(), filmsListTest.get(2).getTittle());
    }

    @Test
    @Order(2)
    void testFindById() {
        Film filmFindTest = repository.findById(1);

        Film filmFind = films.stream().filter(filmsItem -> filmsItem.getId() == 1).findFirst().orElseGet(Film::new);

        Assertions.assertEquals(filmFind.getId(), filmFindTest.getId());
        Assertions.assertEquals(filmFind.getTittle(), filmFindTest.getTittle());
        Assertions.assertEquals(filmFind.getDate(), filmFindTest.getDate());
    }

    @Test
    @Order(3)
    void find() {
        List<Integer> ids = List.of(0, 1, 2);
        List<Film> filmsListTest = repository.find(ids);

        List<Film> filmsList = films.stream()
                .filter(filmsItem ->
                        ids.stream().anyMatch(id -> id == filmsItem.getId())
                ).collect(Collectors.toList());

        Assertions.assertEquals(filmsList.size(), filmsListTest.size());
        Assertions.assertEquals(filmsList.get(0).getTittle(), filmsListTest.get(0).getTittle());
        Assertions.assertEquals(filmsList.get(1).getTittle(), filmsListTest.get(1).getTittle());
    }

    @Test
    @Order(7)
    void testAdd() {
        Film film = new Film("film4", Date.valueOf("2009-03-28"));

        repository.add(film);
        Film filmAddTest = repository.findByName("film4").get(0);

        boolean hasFilm = films.stream().anyMatch(filmsItem -> Objects.equals(filmsItem.getTittle(), "film4"));

        Assertions.assertFalse(hasFilm);
        Assertions.assertEquals("film4", filmAddTest.getTittle());
        Assertions.assertEquals(Date.valueOf("2009-03-28"), filmAddTest.getDate());
    }

    @Test
    @Order(9)
    void testDelete() {
        repository.delete(1);
        Film filmDeleteTest = repository.findById(1);

        boolean hasFilm = films.stream().anyMatch(filmsItem -> filmsItem.getId() == 1);

        Assertions.assertTrue(hasFilm);
        Assertions.assertNull(filmDeleteTest);
    }

    @Test
    @Order(8)
    void testEdit() {
        Film film = repository.findById(1);
        film.setTittle("new tittle");

        repository.edit(film);
        Film filmEditTest = repository.findById(1);

        boolean hasFilm = films.stream().anyMatch(filmsItem -> Objects.equals(filmsItem.getTittle(), "new tittle"));

        Assertions.assertFalse(hasFilm);
        Assertions.assertNotNull(filmEditTest);
        Assertions.assertEquals("new tittle", filmEditTest.getTittle());
    }

    @Test
    @Order(4)
    void testFindByName() {
        List<Film> filmsListTest = repository.findByName("film1");

        List<Film> filmsList = films.stream()
                .filter(filmsItem -> Objects.equals(filmsItem.getTittle(), "film1"))
                .collect(Collectors.toList());

        Assertions.assertEquals(filmsList.get(0).getTittle(), filmsListTest.get(0).getTittle());
    }

    @Test
    @Order(5)
    void testSize() {
        int sizeTest = repository.size();
        int size = films.size();

        Assertions.assertEquals(size, sizeTest);
    }

    @Test
    @Order(6)
    void testFindByContains() {
        List<Film> filmsListTest = repository.findByContains("film");

        List<Film> filmsList = films.stream()
                .filter(filmsItem -> filmsItem.getTittle().contains("film"))
                .collect(Collectors.toList());

        Assertions.assertEquals(filmsList.size(), filmsListTest.size());
        Assertions.assertEquals(filmsList.get(0).getTittle(), filmsListTest.get(0).getTittle());
        Assertions.assertEquals(filmsList.get(1).getTittle(), filmsListTest.get(1).getTittle());
        Assertions.assertEquals(filmsList.get(2).getTittle(), filmsListTest.get(2).getTittle());
    }
}