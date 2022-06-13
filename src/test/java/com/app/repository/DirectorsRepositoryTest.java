package com.app.repository;

import com.app.model.director.Director;
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
public class DirectorsRepositoryTest {

    @Autowired
    private DirectorsRepository repository;
    private Connection connection;

    @BeforeAll
    void initDirectors() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "sa");
    }

    List<Director> getDirectors(ResultSet resultSet) throws SQLException {
        List<Director> directors = new ArrayList<>();
        while (resultSet.next()){
            Director director = new Director();
            director.setId(resultSet.getInt("director_id"));
            director.setName(resultSet.getString("name"));
            director.setYear(resultSet.getString("year"));
            directors.add(director);
        }
        return directors;
    }

    @Test
    @Order(2)
    void testFindAll() throws SQLException {
        List<Director> directorsListTest = repository.findAll();


        PreparedStatement statement = connection.prepareStatement("SELECT * FROM director");
        List<Director> directorsList = getDirectors(statement.executeQuery());

        Assertions.assertEquals(directorsList.size(), directorsListTest.size());
        Assertions.assertEquals(directorsList.get(0).getName(), directorsListTest.get(0).getName());
        Assertions.assertEquals(directorsList.get(1).getName(), directorsListTest.get(1).getName());
        Assertions.assertEquals(directorsList.get(2).getName(), directorsListTest.get(2).getName());
    }

    @Test
    @Order(3)
    void testFindById() throws SQLException {
        Director directorFindTest = repository.findById(1);

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM director WHERE director.director_id = 1");
        Director director = getDirectors(statement.executeQuery()).get(0);

        Assertions.assertEquals(director.getId(), directorFindTest.getId());
        Assertions.assertEquals(director.getName(), directorFindTest.getName());
        Assertions.assertEquals(director.getYear(), directorFindTest.getYear());
    }

    @Test
    @Order(4)
    void find() throws SQLException {
        List<Director> directorsListTest = repository.find(List.of(0, 1, 2));

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM director WHERE director.director_id IN (0, 1, 2)");
        List<Director> directorsList = getDirectors(statement.executeQuery());

        Assertions.assertEquals(directorsList.size(), directorsListTest.size());
        Assertions.assertEquals(directorsList.get(0).getName(), directorsListTest.get(0).getName());
        Assertions.assertEquals(directorsList.get(1).getName(), directorsListTest.get(1).getName());
    }

    @Test
    @Order(1)
    void testAdd() throws SQLException {
        Director director = new Director("director4", "24");

        repository.add(director);
        Director directorAddTest = repository.findByName("director4").get(0);

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM director WHERE director.name = 'director4'");
        Director directorAdd = getDirectors(statement.executeQuery()).get(0);

        Assertions.assertEquals(directorAdd.getId(), directorAddTest.getId());
        Assertions.assertEquals(directorAdd.getName(), directorAddTest.getName());
    }

    @Test
    @Order(9)
    void testDelete() throws SQLException {
        repository.delete(1);
        Director actorDeleteTest = repository.findById(1);

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM director WHERE director.director_id = 1");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int directorDelete = resultSet.getInt(1);

        Assertions.assertNull(actorDeleteTest);
        Assertions.assertEquals(0, directorDelete);
    }

    @Test
    @Order(8)
    void testEdit() throws SQLException {
        Director director = repository.findById(1);
        director.setName("new name");

        repository.edit(director);
        Director directorEditTest = repository.findById(1);

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM director WHERE director.director_id = 1");
        Director directorEdit = getDirectors(statement.executeQuery()).get(0);

        Assertions.assertEquals(directorEdit.getName(), directorEditTest.getName());
    }

    @Test
    @Order(5)
    void testFindByName() throws SQLException {
        List<Director> directorsListTest = repository.findByName("director1");

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM director WHERE director.name = 'director1'");
        List<Director> directorsList = getDirectors(statement.executeQuery());
        Assertions.assertEquals(directorsList.get(0).getName(), directorsListTest.get(0).getName());
    }

    @Test
    @Order(6)
    void testSize() throws SQLException {
        int sizeTest = repository.size();

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM director");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int size = resultSet.getInt(1);

        Assertions.assertEquals(size, sizeTest);
    }

    @Test
    @Order(7)
    void testFindByContains() throws SQLException {
        List<Director> directorsListTest = repository.findByContains("director");

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM director WHERE director.name LIKE '%director%'");
        List<Director> directorsList = getDirectors(statement.executeQuery());

        Assertions.assertFalse(directorsListTest.isEmpty());
        Assertions.assertEquals(directorsList.size(), directorsListTest.size());
        Assertions.assertEquals(directorsList.get(0).getName(), directorsListTest.get(0).getName());
        Assertions.assertEquals(directorsList.get(1).getName(), directorsListTest.get(1).getName());
        Assertions.assertEquals(directorsList.get(2).getName(), directorsListTest.get(2).getName());
    }

}
