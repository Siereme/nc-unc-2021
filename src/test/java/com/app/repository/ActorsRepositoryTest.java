package com.app.repository;

import com.app.model.actor.Actor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ActorsRepositoryTest {

    @Autowired
    private ActorsRepository repository;
    private Connection connection;

    @BeforeAll
    void init() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "sa");
    }

    List<Actor> getActors(ResultSet resultSet) throws SQLException {
        List<Actor> actors = new ArrayList<>();
        while (resultSet.next()){
            Actor actor = new Actor();
            actor.setId(resultSet.getInt("actor_id"));
            actor.setName(resultSet.getString("name"));
            actor.setYear(resultSet.getString("year"));
            actors.add(actor);
        }
        return actors;
    }

    @Test
    @Order(2)
    void testFindAll() throws SQLException {
        List<Actor> actorsListTest = repository.findAll();


        PreparedStatement statement = connection.prepareStatement("SELECT * FROM actor");
        List<Actor> actorsList = getActors(statement.executeQuery());

        Assertions.assertEquals(actorsList.size(), actorsListTest.size());
        Assertions.assertEquals(actorsList.get(0).getName(), actorsListTest.get(0).getName());
        Assertions.assertEquals(actorsList.get(1).getName(), actorsListTest.get(1).getName());
        Assertions.assertEquals(actorsList.get(2).getName(), actorsListTest.get(2).getName());
    }

    @Test
    @Order(3)
    void testFindById() throws SQLException {
        Actor actorFindTest = repository.findById(1);

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM actor WHERE actor.actor_id = 1");
        Actor actor = getActors(statement.executeQuery()).get(0);

        Assertions.assertEquals(actor.getId(), actorFindTest.getId());
        Assertions.assertEquals(actor.getName(), actorFindTest.getName());
        Assertions.assertEquals(actor.getYear(), actorFindTest.getYear());
    }

    @Test
    @Order(4)
    void find() throws SQLException {
        List<Actor> actorsListTest = repository.find(List.of(0, 1, 2));

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM actor WHERE actor.actor_id IN (0, 1, 2)");
        List<Actor> actorsList = getActors(statement.executeQuery());

        Assertions.assertEquals(actorsList.size(), actorsListTest.size());
        Assertions.assertEquals(actorsList.get(0).getName(), actorsListTest.get(0).getName());
        Assertions.assertEquals(actorsList.get(1).getName(), actorsListTest.get(1).getName());
    }

    @Test
    @Order(1)
    void testAdd() throws SQLException {
        Actor actor = new Actor("actor4", "24");

        repository.add(actor);
        Actor actorAddTest = repository.findByName("actor4").get(0);

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM actor WHERE actor.name = 'actor4'");
        Actor actorAdd = getActors(statement.executeQuery()).get(0);

        Assertions.assertEquals(actorAdd.getId(), actorAddTest.getId());
        Assertions.assertEquals(actorAdd.getName(), actorAddTest.getName());
    }

    @Test
    @Order(9)
    void testDelete() throws SQLException {
        repository.delete(1);
        Actor actorDeleteTest = repository.findById(1);

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM actor WHERE actor.actor_id = 1");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int actorDelete = resultSet.getInt(1);

        Assertions.assertNull(actorDeleteTest);
        Assertions.assertEquals(0, actorDelete);
    }

    @Test
    @Order(8)
    void testEdit() throws SQLException {
        Actor actor = repository.findById(1);
        actor.setName("new name");

        repository.edit(actor);
        Actor actorEditTest = repository.findById(1);

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM actor WHERE actor.actor_id = 1");
        Actor actorEdit = getActors(statement.executeQuery()).get(0);

        Assertions.assertEquals(actorEdit.getName(), actorEditTest.getName());
    }

    @Test
    @Order(5)
    void testFindByName() throws SQLException {
        List<Actor> actorsListTest = repository.findByName("actor1");

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM actor WHERE actor.name = 'actor1'");
        List<Actor> actorsList = getActors(statement.executeQuery());
        Assertions.assertEquals(actorsList.get(0).getName(), actorsListTest.get(0).getName());
    }

    @Test
    @Order(6)
    void testSize() throws SQLException {
        int sizeTest = repository.size();

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM actor");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int size = resultSet.getInt(1);

        Assertions.assertEquals(size, sizeTest);
    }

    @Test
    @Order(7)
    void testFindByContains() throws SQLException {
        List<Actor> actorsListTest = repository.findByContains("actor");

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM actor WHERE actor.name LIKE '%actor%'");
        List<Actor> actorsList = getActors(statement.executeQuery());

        Assertions.assertFalse(actorsListTest.isEmpty());
        Assertions.assertEquals(actorsList.size(), actorsListTest.size());
        Assertions.assertEquals(actorsList.get(0).getName(), actorsListTest.get(0).getName());
        Assertions.assertEquals(actorsList.get(1).getName(), actorsListTest.get(1).getName());
        Assertions.assertEquals(actorsList.get(2).getName(), actorsListTest.get(2).getName());
    }

}
