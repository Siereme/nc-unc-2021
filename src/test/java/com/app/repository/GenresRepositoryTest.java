package com.app.repository;

import com.app.model.genre.Genre;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GenresRepositoryTest {

    @Mock
    private GenresRepository repository;
    private List<Genre> genres = new ArrayList<>();

    @BeforeAll
    void initGenres() {
        this.genres.addAll(List.of(new Genre("genre1"), new Genre("genre2"), new Genre("genre3")));
        for (int i = 0; i < genres.size(); i++) {
            genres.get(i).setId(i);
        }
    }

    @Test
    @Order(2)
    void testFindAll() {
        Mockito.when(repository.findAll()).thenReturn(genres);
        List<Genre> genres = repository.findAll();

        Mockito.verify(repository).findAll();
        Assertions.assertEquals(3, genres.size());
        Assertions.assertEquals("genre1", genres.get(0).getTittle());
        Assertions.assertEquals("genre2", genres.get(1).getTittle());
        Assertions.assertEquals("genre3", genres.get(2).getTittle());
    }

    @Test
    @Order(3)
    void testFindById() {
        Genre genre = genres.get(1);

        Mockito.when(repository.findById(1)).thenReturn(genre);
        Genre genreFind = repository.findById(1);

        Mockito.verify(repository).findById(1);
        Assertions.assertEquals(1, genreFind.getId());
        Assertions.assertEquals("genre2", genreFind.getTittle());
    }

    @Test
    @Order(4)
    void find() {
        Mockito.when(repository.find(List.of(0, 1, 2))).thenReturn(genres);

        List<Genre> genreList = repository.find(List.of(0, 1, 2));

        Mockito.verify(repository).find(List.of(0, 1, 2));
        Assertions.assertEquals(3, genreList.size());
        Assertions.assertEquals("genre1", genreList.get(0).getTittle());
        Assertions.assertEquals("genre2", genreList.get(1).getTittle());
        Assertions.assertEquals("genre3", genreList.get(2).getTittle());
    }

    @Test
    @Order(1)
    void testAdd() {
        Genre genre = new Genre("genre4");

        Mockito.when(repository.add(genre)).thenReturn(genre);
        repository.add(genre);

        Mockito.verify(repository).add(genre);
        Assertions.assertEquals("genre4", genre.getTittle());
    }

    @Test
    @Order(9)
    void testDelete() {
        repository.delete(1);
        Mockito.verify(repository).delete(1);

        Genre genre = repository.findById(2);
        Assertions.assertNull(genre);
    }

    @Test
    @Order(8)
    void testEdit() {
        Genre genre = genres.get(1);
        genre.setTittle("new tittle");

        repository.edit(genre);

        Mockito.verify(repository).edit(genre);
        Assertions.assertEquals("new tittle", genre.getTittle());
    }

    @Test
    @Order(5)
    void testFindByName() {
        Genre genre = genres.get(0);

        Mockito.when(repository.findByName("genre1")).thenReturn(List.of(genre));
        List<Genre> genres = repository.findByName("genre1");

        Mockito.verify(repository).findByName("genre1");
        Assertions.assertEquals("genre1", genres.get(0).getTittle());
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
        Mockito.when(repository.findByContains("genre")).thenReturn(genres);
        List<Genre> genreList = repository.findByContains("genre");

        Mockito.verify(repository).findByContains("genre");
        Assertions.assertFalse(genreList.isEmpty());
        Assertions.assertEquals(3, genreList.size());
        Assertions.assertEquals("genre1", genreList.get(0).getTittle());
        Assertions.assertEquals("genre2", genreList.get(1).getTittle());
        Assertions.assertEquals("genre3", genreList.get(2).getTittle());
    }

}
