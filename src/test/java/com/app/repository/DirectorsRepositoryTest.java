package com.app.repository;

import com.app.model.director.Director;
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
public class DirectorsRepositoryTest {

    @Autowired
    private DirectorsRepository repository;
    private final List<Director> directors = new ArrayList<>();

    @BeforeAll
    void initDirectors() {
        this.directors.addAll(List.of(
                new Director("director1", "20"),
                new Director("director2", "35"),
                new Director("director3", "30")
        ));
        for (int i = 0; i < directors.size(); i++) {
            directors.get(i).setId(i + 1);
        }
    }


    @Test
    @Order(1)
    void testFindAll() {
        List<Director> directorsListTest = repository.findAll();


        Assertions.assertEquals(directors.size(), directorsListTest.size());
        Assertions.assertEquals(directors.get(0).getName(), directorsListTest.get(0).getName());
        Assertions.assertEquals(directors.get(1).getName(), directorsListTest.get(1).getName());
        Assertions.assertEquals(directors.get(2).getName(), directorsListTest.get(2).getName());
    }

    @Test
    @Order(2)
    void testFindById() {
        Director directorFindTest = repository.findById(1);

        Director director = directors.stream().filter(actorsItem -> actorsItem.getId() == 1).findFirst().orElseGet(Director::new);

        Assertions.assertEquals(director.getId(), directorFindTest.getId());
        Assertions.assertEquals(director.getName(), directorFindTest.getName());
        Assertions.assertEquals(director.getYear(), directorFindTest.getYear());
    }

    @Test
    @Order(3)
    void find() {
        List<Integer> ids = List.of(0, 1, 2);
        List<Director> directorsListTest = repository.find(ids);

        List<Director> directorList = directors.stream()
                .filter(directorsItem ->
                        ids.stream().anyMatch(id -> id == directorsItem.getId())
                ).collect(Collectors.toList());

        Assertions.assertEquals(directorList.size(), directorsListTest.size());
        Assertions.assertEquals(directorList.get(0).getName(), directorsListTest.get(0).getName());
        Assertions.assertEquals(directorList.get(1).getName(), directorsListTest.get(1).getName());
    }

    @Test
    @Order(7)
    void testAdd() {
        Director director = new Director("director4", "24");

        repository.add(director);
        Director directorAddTest = repository.findByName("director4").get(0);

        boolean hasDirector = directors.stream().anyMatch(directorsItem -> Objects.equals(directorsItem.getName(), "director4"));

        Assertions.assertFalse(hasDirector);
        Assertions.assertEquals("director4", directorAddTest.getName());
        Assertions.assertEquals("24", directorAddTest.getYear());
    }

    @Test
    @Order(9)
    void testDelete() {
        repository.delete(1);
        Director directorDeleteTest = repository.findById(1);

        boolean hasDirector = directors.stream().anyMatch(directorsItem -> directorsItem.getId() == 1);

        Assertions.assertTrue(hasDirector);
        Assertions.assertNull(directorDeleteTest);
    }

    @Test
    @Order(8)
    void testEdit() {
        Director director = repository.findById(1);
        director.setName("new name");

        repository.edit(director);
        Director directorEditTest = repository.findById(1);

        boolean hasDirector = directors.stream().anyMatch(directorsItem -> Objects.equals(directorsItem.getName(), "new name"));

        Assertions.assertFalse(hasDirector);
        Assertions.assertNotNull(directorEditTest);
        Assertions.assertEquals("new name", directorEditTest.getName());
    }

    @Test
    @Order(4)
    void testFindByName() {
        List<Director> directorsListTest = repository.findByName("director1");

        List<Director> directorList = directors.stream()
                .filter(directorsItem -> Objects.equals(directorsItem.getName(), "director1"))
                .collect(Collectors.toList());

        Assertions.assertEquals(directorList.get(0).getName(), directorsListTest.get(0).getName());
    }

    @Test
    @Order(5)
    void testSize() {
        int sizeTest = repository.size();
        int size = directors.size();

        Assertions.assertEquals(size, sizeTest);
    }

    @Test
    @Order(6)
    void testFindByContains() {
        List<Director> directorsListTest = repository.findByContains("director");

        List<Director> directorList = directors.stream()
                .filter(directorsItem -> directorsItem.getName().contains("director"))
                .collect(Collectors.toList());

        Assertions.assertEquals(directorList.size(), directorsListTest.size());
        Assertions.assertEquals(directorList.get(0).getName(), directorsListTest.get(0).getName());
        Assertions.assertEquals(directorList.get(1).getName(), directorsListTest.get(1).getName());
        Assertions.assertEquals(directorList.get(2).getName(), directorsListTest.get(2).getName());
    }

}
