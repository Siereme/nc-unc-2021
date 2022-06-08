package com.app.repository;

import com.app.model.director.Director;
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
public class DirectorsRepositoryTest {

    @Mock
    private DirectorsRepository repository;
    private List<Director> directors = new ArrayList<>();

    @BeforeAll
    void initDirectors() {
        this.directors.addAll(List.of(new Director("director1", "21"), new Director("director2", "22"),
                new Director("director3", "23")));
        for (int i = 0; i < directors.size(); i++) {
            directors.get(i).setId(i);
        }
    }

    @Test
    @Order(2)
    void testFindAll() {
        Mockito.when(repository.findAll()).thenReturn(directors);
        List<Director> directors = repository.findAll();

        Mockito.verify(repository).findAll();
        Assertions.assertEquals(3, directors.size());
        Assertions.assertEquals("director1", directors.get(0).getName());
        Assertions.assertEquals("director2", directors.get(1).getName());
        Assertions.assertEquals("director3", directors.get(2).getName());
    }

    @Test
    @Order(3)
    void testFindById() {
        Director director = directors.get(1);

        Mockito.when(repository.findById(1)).thenReturn(director);
        Director directorFind = repository.findById(1);

        Mockito.verify(repository).findById(1);
        Assertions.assertEquals(1, directorFind.getId());
        Assertions.assertEquals("director2", directorFind.getName());
    }

    @Test
    @Order(4)
    void find() {
        Mockito.when(repository.find(List.of(0, 1, 2))).thenReturn(directors);

        List<Director> directorList = repository.find(List.of(0, 1, 2));

        Mockito.verify(repository).find(List.of(0, 1, 2));
        Assertions.assertEquals(3, directorList.size());
        Assertions.assertEquals("director1", directorList.get(0).getName());
        Assertions.assertEquals("director2", directorList.get(1).getName());
        Assertions.assertEquals("director3", directorList.get(2).getName());
    }

    @Test
    @Order(1)
    void testAdd() {
        Director director = new Director("director4", "24");

        Mockito.when(repository.add(director)).thenReturn(director);
        repository.add(director);

        Mockito.verify(repository).add(director);
        Assertions.assertEquals("director4", director.getName());
    }

    @Test
    @Order(9)
    void testDelete() {
        repository.delete(1);
        Mockito.verify(repository).delete(1);

        Director director = repository.findById(2);
        Assertions.assertNull(director);
    }

    @Test
    @Order(8)
    void testEdit() {
        Director director = directors.get(1);
        director.setName("new name");

        repository.edit(director);

        Mockito.verify(repository).edit(director);
        Assertions.assertEquals("new name", director.getName());
    }

    @Test
    @Order(5)
    void testFindByName() {
        Director director = directors.get(0);

        Mockito.when(repository.findByName("director1")).thenReturn(List.of(director));
        List<Director> directors = repository.findByName("director1");

        Mockito.verify(repository).findByName("director1");
        Assertions.assertEquals("director1", directors.get(0).getName());
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
        Mockito.when(repository.findByContains("director")).thenReturn(directors);
        List<Director> directorList = repository.findByContains("director");

        Mockito.verify(repository).findByContains("director");
        Assertions.assertFalse(directorList.isEmpty());
        Assertions.assertEquals(3, directorList.size());
        Assertions.assertEquals("director1", directorList.get(0).getName());
        Assertions.assertEquals("director2", directorList.get(1).getName());
        Assertions.assertEquals("director3", directorList.get(2).getName());
    }

}
