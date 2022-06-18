package com.app.repository;

import com.app.model.actor.Actor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ActorsRepositoryTest {

    @Autowired
    private ActorsRepository repository;
    private final List<Actor> actors = new ArrayList<>();

    @BeforeAll
    void init() {
        this.actors.addAll(List.of(
                new Actor("actor1", "20"),
                new Actor("actor2", "23"),
                new Actor("actor3", "25")
        ));
        for (int i = 0; i < actors.size(); i++) {
            actors.get(i).setId(i + 1);
        }
    }

    @Test
    @Order(1)
    void testFindAll() {
        List<Actor> actorsListTest = repository.findAll();


        Assertions.assertEquals(actors.size(), actorsListTest.size());
        Assertions.assertEquals(actors.get(0).getName(), actorsListTest.get(0).getName());
        Assertions.assertEquals(actors.get(1).getName(), actorsListTest.get(1).getName());
        Assertions.assertEquals(actors.get(2).getName(), actorsListTest.get(2).getName());
    }

    @Test
    @Order(2)
    void testFindById() {
        Actor actorFindTest = repository.findById(1);

        Actor actor = actors.stream().filter(actorsItem -> actorsItem.getId() == 1).findFirst().orElseGet(Actor::new);

        Assertions.assertEquals(actor.getId(), actorFindTest.getId());
        Assertions.assertEquals(actor.getName(), actorFindTest.getName());
        Assertions.assertEquals(actor.getYear(), actorFindTest.getYear());
    }

    @Test
    @Order(3)
    void find() {
        List<Integer> ids = List.of(0, 1, 2);
        List<Actor> actorsListTest = repository.find(ids);

        List<Actor> actorsList = actors.stream()
                .filter(actorsItem ->
                        ids.stream().anyMatch(id -> id == actorsItem.getId())
                ).collect(Collectors.toList());

        Assertions.assertEquals(actorsList.size(), actorsListTest.size());
        Assertions.assertEquals(actorsList.get(0).getName(), actorsListTest.get(0).getName());
        Assertions.assertEquals(actorsList.get(1).getName(), actorsListTest.get(1).getName());
    }

    @Test
    @Order(7)
    void testAdd() {
        Actor actor = new Actor("actor4", "24");

        repository.add(actor);
        Actor actorAddTest = repository.findByName("actor4").get(0);

        boolean hasActor = actors.stream().anyMatch(actorsItem -> Objects.equals(actorsItem.getName(), "actor4"));

        Assertions.assertFalse(hasActor);
        Assertions.assertEquals("actor4", actorAddTest.getName());
        Assertions.assertEquals("24", actorAddTest.getYear());
    }

    @Test
    @Order(9)
    void testDelete() {
        repository.delete(1);
        Actor actorDeleteTest = repository.findById(1);

        boolean hasActor = actors.stream().anyMatch(actorsItem -> actorsItem.getId() == 1);

        Assertions.assertTrue(hasActor);
        Assertions.assertNull(actorDeleteTest);
    }

    @Test
    @Order(8)
    void testEdit() {
        Actor actor = repository.findById(1);
        actor.setName("new name");

        repository.edit(actor);
        Actor actorEditTest = repository.findById(1);

        boolean hasActor = actors.stream().anyMatch(actorsItem -> Objects.equals(actorsItem.getName(), "new name"));

        Assertions.assertFalse(hasActor);
        Assertions.assertNotNull(actorEditTest);
        Assertions.assertEquals("new name", actorEditTest.getName());
    }

    @Test
    @Order(4)
    void testFindByName() {
        List<Actor> actorsListTest = repository.findByName("actor1");

        List<Actor> actorList = actors.stream()
                .filter(actorsItem -> Objects.equals(actorsItem.getName(), "actor1"))
                .collect(Collectors.toList());

        Assertions.assertEquals(actorList.get(0).getName(), actorsListTest.get(0).getName());
    }

    @Test
    @Order(5)
    void testSize() throws SQLException {
        int sizeTest = repository.size();
        int size = actors.size();

        Assertions.assertEquals(size, sizeTest);
    }

    @Test
    @Order(6)
    void testFindByContains() {
        List<Actor> actorsListTest = repository.findByContains("actor");

        List<Actor> actorList = actors.stream()
                .filter(actorsItem -> actorsItem.getName().contains("actor"))
                .collect(Collectors.toList());

        Assertions.assertEquals(actorList.size(), actorsListTest.size());
        Assertions.assertEquals(actorList.get(0).getName(), actorsListTest.get(0).getName());
        Assertions.assertEquals(actorList.get(1).getName(), actorsListTest.get(1).getName());
        Assertions.assertEquals(actorList.get(2).getName(), actorsListTest.get(2).getName());
    }

}
