package com.app.repository;

import com.app.model.film.Film;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FilmsRepositoryTest {

    @Mock
    private FilmsRepository repository;
    private List<Film> films = new ArrayList<>();

    @BeforeAll
    void initFilms(){
        this.films.addAll(List.of(
                new Film("film1", LocalDate.of(2022, 6, 2)),
                new Film("film2", LocalDate.of(2022, 6, 2)),
                new Film("film3", LocalDate.of(2022, 6, 2))
        ));
        for (int i = 0; i < films.size(); i++) {
            films.get(i).setId(i);
        }
    }

    @Test
    @Order(2)
    void testFindAll() {
        Mockito.when(repository.findAll()).thenReturn(films);
        List<Film> filmList = repository.findAll();

        Mockito.verify(repository).findAll();
        Assertions.assertEquals(3, filmList.size());
        Assertions.assertEquals("film1", filmList.get(0).getTittle());
        Assertions.assertEquals("film2", filmList.get(1).getTittle());
        Assertions.assertEquals("film3", filmList.get(2).getTittle());
    }

    @Test
    @Order(3)
    void testFindById() {
        Film film = films.get(1);

        Mockito.when(repository.findById(1)).thenReturn(film);
        Film filmFind = repository.findById(1);

        Mockito.verify(repository).findById(1);
        Assertions.assertEquals(1, filmFind.getId());
        Assertions.assertEquals("film2", filmFind.getTittle());
    }

    @Test
    @Order(4)
    void find() {
        Mockito.when(repository.find(List.of(0, 1, 2))).thenReturn(films);

        List<Film> filmList = repository.find(List.of(0, 1, 2));

        Mockito.verify(repository).find(List.of(0, 1, 2));
        Assertions.assertEquals(3, filmList.size());
        Assertions.assertEquals("film1", filmList.get(0).getTittle());
        Assertions.assertEquals("film2", filmList.get(1).getTittle());
        Assertions.assertEquals("film3", filmList.get(2).getTittle());
    }

    @Test
    @Order(1)
    void testAdd() {
        Film film = new Film("film4", LocalDate.of(2022, 6, 2));

        Mockito.when(repository.add(film)).thenReturn(film);
        repository.add(film);

        Mockito.verify(repository).add(film);
        Assertions.assertNotNull(film.getId());
        Assertions.assertEquals("film4", film.getTittle());
    }

    @Test
    @Order(9)
    void testDelete() {
        repository.delete(1);
        Mockito.verify(repository).delete(1);
    }

    @Test
    @Order(8)
    void testEdit() {
        Film film = films.get(1);
        film.setTittle("new tittle");

        repository.edit(film);

        Mockito.verify(repository).edit(film);
        Assertions.assertEquals("new tittle", film.getTittle());
    }

    @Test
    @Order(5)
    void testFindByName() {
        Film film = films.get(0);

        Mockito.when(repository.findByName("film1")).thenReturn(List.of(film));
        List<Film> films = repository.findByName("film1");

        Mockito.verify(repository).findByName("film1");
        Assertions.assertEquals("film1", films.get(0).getTittle());
    }

    @Test
    @Order(6)
    void testSize() {
        Mockito.when(repository.size()).thenReturn(3);
        int size = repository.size();

        Mockito.verify(repository).size();
        Assertions.assertEquals(3, size);
    }

    @Test
    @Order(7)
    void testFindByContains() {
        Mockito.when(repository.findByContains("film")).thenReturn(films);
        List<Film> filmList = repository.findByContains("film");

        Mockito.verify(repository).findByContains("film");
        Assertions.assertFalse(filmList.isEmpty());
        Assertions.assertEquals(3, filmList.size());
        Assertions.assertEquals("film1", filmList.get(0).getTittle());
        Assertions.assertEquals("film2", filmList.get(1).getTittle());
        Assertions.assertEquals("film3", filmList.get(2).getTittle());
    }
}