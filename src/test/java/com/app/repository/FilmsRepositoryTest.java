package com.app.repository;

import com.app.model.film.Film;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class FilmsRepositoryTest {

    @Autowired
    FilmsRepository repository;

    @Test
    void testFindAll() {
        List<Film> filmList = repository.findAll();
        assertThat(filmList.isEmpty()).isFalse();
    }

    @Test
    void testFindById() {
        Film film = repository.findById(3);
        assertThat(film.getId()).isEqualTo(3);
    }

    @Test
    void find() {
        List<Film> films = repository.find(List.of(1,2,3));
        assertThat(films.size()).isEqualTo(3);
    }

    @Test
    void testAdd() {
        Film film = new Film("film1212123", LocalDate.of(2022, 6, 2));

        repository.add(film);

        Film addFilm = repository.findByName("film1212123").stream().findFirst().orElse(null);

        assertThat(addFilm).isNotEqualTo(null);
        assertThat(addFilm.getTittle()).isEqualTo(film.getTittle());
        assertThat(addFilm.getDate()).isEqualTo(film.getDate());
    }

    @Test
    void testDelete() {
        repository.delete(3);

        Film film = repository.findById(3);

        assertThat(film).isNull();
    }

    @Test
    void testEdit() {
        Film film = repository.findById(1);
        film.setTittle("new tittle");

        repository.edit(film);
        Film editFilm = repository.findById(1);

        assertThat(editFilm.getTittle()).isEqualTo(film.getTittle());
    }

    @Test
    void testFindByName() {
        List<Film> films = repository.findByName("film1");
        Film film = films.stream().findFirst().orElse(null);

        assertThat(film).isNotNull();
        assertThat(film.getTittle()).isEqualTo("film1");
    }

    @Test
    void testSize() {
    }

    @Test
    void testFindByContains() {
        List<Film> films = repository.findByContains("film");
        assertThat(films.isEmpty()).isFalse();
    }
}