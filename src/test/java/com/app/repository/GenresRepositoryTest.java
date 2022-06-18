package com.app.repository;

import com.app.model.genre.Genre;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GenresRepositoryTest {

    @Autowired
    private GenresRepository repository;
    private final List<Genre> genres = new ArrayList<>();

    @BeforeAll
    void initGenres() {
        this.genres.addAll(List.of(
                new Genre("genre1"),
                new Genre("genre2"),
                new Genre("genre3")
        ));
        for (int i = 0; i < genres.size(); ++i) {
            genres.get(i).setId(i + 1);
        }
    }

    @Test
    @Order(1)
    void testFindAll() {
        List<Genre> genresListTest = repository.findAll();


        Assertions.assertEquals(genres.size(), genresListTest.size());
        Assertions.assertEquals(genres.get(0).getTittle(), genresListTest.get(0).getTittle());
        Assertions.assertEquals(genres.get(1).getTittle(), genresListTest.get(1).getTittle());
        Assertions.assertEquals(genres.get(2).getTittle(), genresListTest.get(2).getTittle());
    }

    @Test
    @Order(2)
    void testFindById() {
        Genre genreFindTest = repository.findById(1);

        Genre genreFind = genres.stream().filter(genresItem -> genresItem.getId() == 1).findFirst().orElseGet(Genre::new);

        Assertions.assertEquals(genreFind.getId(), genreFindTest.getId());
        Assertions.assertEquals(genreFind.getTittle(), genreFindTest.getTittle());
    }

    @Test
    @Order(3)
    void find(){
        List<Integer> ids = List.of(0, 1, 2);
        List<Genre> actorsListTest = repository.find(ids);

        List<Genre> genresList = genres.stream()
                .filter(genresItem ->
                        ids.stream().anyMatch(id -> id == genresItem.getId())
                ).collect(Collectors.toList());

        Assertions.assertEquals(genresList.size(), actorsListTest.size());
        Assertions.assertEquals(genresList.get(0).getTittle(), actorsListTest.get(0).getTittle());
        Assertions.assertEquals(genresList.get(1).getTittle(), actorsListTest.get(1).getTittle());
    }

    @Test
    @Order(7)
    void testAdd() {
        Genre genre = new Genre("genre4");

        repository.add(genre);
        Genre genreAddTest = repository.findByName("genre4").get(0);

        boolean hasGenre = genres.stream().anyMatch(genresItem -> Objects.equals(genresItem.getTittle(), "genre4"));

        Assertions.assertFalse(hasGenre);
        Assertions.assertEquals("genre4", genreAddTest.getTittle());
    }

    @Test
    @Order(9)
    void testDelete() {
        repository.delete(1);
        Genre genreDeleteTest = repository.findById(1);

        boolean hasGenre = genres.stream().anyMatch(genresItem -> genresItem.getId() == 1);

        Assertions.assertTrue(hasGenre);
        Assertions.assertNull(genreDeleteTest);
    }

    @Test
    @Order(8)
    void testEdit() {
        Genre genre = repository.findById(1);
        genre.setTittle("new tittle");

        repository.edit(genre);
        Genre genreEditTest = repository.findById(1);

        boolean hasGenre = genres.stream().anyMatch(genresItem -> Objects.equals(genresItem.getTittle(), "new tittle"));

        Assertions.assertFalse(hasGenre);
        Assertions.assertNotNull(genreEditTest);
        Assertions.assertEquals("new tittle", genreEditTest.getTittle());
    }

    @Test
    @Order(4)
    void testFindByName() {
        List<Genre> genresListTest = repository.findByName("genre1");

        List<Genre> genresList = genres.stream()
                .filter(genresItem -> Objects.equals(genresItem.getTittle(), "genre1"))
                .collect(Collectors.toList());

        Assertions.assertEquals(genresList.get(0).getTittle(), genresListTest.get(0).getTittle());
    }

    @Test
    @Order(5)
    void testSize() {
        int sizeTest = repository.size();
        int size = genres.size();

        Assertions.assertEquals(size, sizeTest);
    }

    @Test
    @Order(6)
    void testFindByContains() {
        List<Genre> genresListTest = repository.findByContains("genre");

        List<Genre> genresList = genres.stream()
                .filter(genresItem -> genresItem.getTittle().contains("genre"))
                .collect(Collectors.toList());

        Assertions.assertEquals(genresList.size(), genresListTest.size());
        Assertions.assertEquals(genresList.get(0).getTittle(), genresListTest.get(0).getTittle());
        Assertions.assertEquals(genresList.get(1).getTittle(), genresListTest.get(1).getTittle());
        Assertions.assertEquals(genresList.get(2).getTittle(), genresListTest.get(2).getTittle());
    }

}
