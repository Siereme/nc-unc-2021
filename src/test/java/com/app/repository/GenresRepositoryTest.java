package com.app.repository;

import com.app.model.genre.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class GenresRepositoryTest {

    @Autowired
    GenresRepository genresRepository;

    @Test
    void testFindAll() {
        List<Genre> genres = genresRepository.findAll();
        assertThat(genres.isEmpty()).isFalse();
    }

    @Test
    void testFindById() {
        Genre genre = genresRepository.findById(3);
        assertThat(genre.getId()).isEqualTo(3);
    }

    @Test
    void find() {
        List<Genre> genres = genresRepository.find(List.of(1, 2, 3));
        assertThat(genres.size()).isEqualTo(3);
    }

    @Test
    void testAdd() {
        Genre genre = new Genre("genre");

        genresRepository.add(genre);

        Genre addeddGenre = genresRepository.findByName("genre").stream().findFirst().orElse(null);

        assertThat(addeddGenre).isNotEqualTo(null);
        assert addeddGenre != null;
        assertThat(addeddGenre.getTittle()).isEqualTo("genre");
    }

    @Test
    void testDelete() {
        genresRepository.delete(3);

        Genre genre = genresRepository.findById(3);

        assertThat(genre).isNull();
    }

    @Test
    void testEdit() {
        Genre genre = genresRepository.findById(1);
        genre.setTittle("new name");

        genresRepository.edit(genre);
        Genre editedGenre = genresRepository.findById(1);

        assertThat(editedGenre.getTittle()).isEqualTo("new name");
    }

    @Test
    void testFindByName() {
        List<Genre> genres = genresRepository.findByName("genre1");
        Genre genre = genres.stream().findFirst().orElse(null);

        assertThat(genre).isNotNull();
        assertThat(genre.getTittle()).isEqualTo("genre1");
    }

    @Test
    void testSize() {
    }

    @Test
    void testFindByContains() {
        List<Genre> genres = genresRepository.findByContains("genre");
        assertThat(genres.isEmpty()).isFalse();
    }

}
