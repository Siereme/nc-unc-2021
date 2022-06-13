package com.app.repository;

import com.app.model.film.Film;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FilmsRepositoryTest {

    @Autowired
    private FilmsRepository repository;
    private Connection connection;

    @BeforeAll
    void init() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "sa");
    }

    List<Film> getFilms(ResultSet resultSet) throws SQLException {
        List<Film> films = new ArrayList<>();
        while (resultSet.next()){
            Film film = new Film();
            film.setId(resultSet.getInt("film_id"));
            film.setTittle(resultSet.getString("tittle"));
            film.setDate(resultSet.getDate("date"));
            films.add(film);
        }
        return films;
    }

    @Test
    @Order(2)
    void testFindAll() throws SQLException {
        List<Film> filmsListTest = repository.findAll();


        PreparedStatement statement = connection.prepareStatement("SELECT * FROM film");
        List<Film> filmsList = getFilms(statement.executeQuery());

        Assertions.assertEquals(filmsList.size(), filmsListTest.size());
        Assertions.assertEquals(filmsList.get(0).getTittle(), filmsListTest.get(0).getTittle());
        Assertions.assertEquals(filmsList.get(1).getTittle(), filmsListTest.get(1).getTittle());
        Assertions.assertEquals(filmsList.get(2).getTittle(), filmsListTest.get(2).getTittle());
    }

    @Test
    @Order(3)
    void testFindById() throws SQLException {
        Film filmFindTest = repository.findById(1);

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM film WHERE film.film_id = 1");
        Film film = getFilms(statement.executeQuery()).get(0);

        Assertions.assertEquals(film.getId(), filmFindTest.getId());
        Assertions.assertEquals(film.getTittle(), filmFindTest.getTittle());
        Assertions.assertEquals(film.getDate(), filmFindTest.getDate());
    }

    @Test
    @Order(4)
    void find() throws SQLException {
        List<Film> filmsListTest = repository.find(List.of(0, 1, 2));

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM film WHERE film.film_id IN (0, 1, 2)");
        List<Film> filmsList = getFilms(statement.executeQuery());

        Assertions.assertEquals(filmsList.size(), filmsListTest.size());
        Assertions.assertEquals(filmsList.get(0).getTittle(), filmsListTest.get(0).getTittle());
        Assertions.assertEquals(filmsList.get(1).getTittle(), filmsListTest.get(1).getTittle());
    }

    @Test
    @Order(1)
    void testAdd() throws SQLException {
        Film film = new Film("film4", Date.valueOf("2009-03-28"));

        repository.add(film);
        Film filmAddTest = repository.findByName("film4").get(0);

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM film WHERE film.tittle = 'film4'");
        Film filmAdd = getFilms(statement.executeQuery()).get(0);

        Assertions.assertEquals(filmAdd.getId(), filmAddTest.getId());
        Assertions.assertEquals(filmAdd.getTittle(), filmAddTest.getTittle());
    }

    @Test
    @Order(9)
    void testDelete() throws SQLException {
        repository.delete(1);
        Film filmDeleteTest = repository.findById(1);

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM film WHERE film.film_id = 1");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int filmDelete = resultSet.getInt(1);

        Assertions.assertNull(filmDeleteTest);
        Assertions.assertEquals(0, filmDelete);
    }

    @Test
    @Order(8)
    void testEdit() throws SQLException {
        Film film = repository.findById(1);
        film.setTittle("new tittle");

        repository.edit(film);
        Film filmEditTest = repository.findById(1);

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM film WHERE film.film_id = 1");
        Film filmEdit = getFilms(statement.executeQuery()).get(0);

        Assertions.assertEquals(filmEdit.getTittle(), filmEditTest.getTittle());
    }

    @Test
    @Order(5)
    void testFindByName() throws SQLException {
        List<Film> filmsListTest = repository.findByName("film1");

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM film WHERE film.tittle = 'film1'");
        List<Film> filmsList = getFilms(statement.executeQuery());
        Assertions.assertEquals(filmsList.get(0).getTittle(), filmsListTest.get(0).getTittle());
    }

    @Test
    @Order(6)
    void testSize() throws SQLException {
        int sizeTest = repository.size();

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM film");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int size = resultSet.getInt(1);

        Assertions.assertEquals(size, sizeTest);
    }

    @Test
    @Order(7)
    void testFindByContains() throws SQLException {
        List<Film> filmsListTest = repository.findByContains("film");

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM film WHERE film.tittle LIKE '%film%'");
        List<Film> filmsList = getFilms(statement.executeQuery());

        Assertions.assertFalse(filmsListTest.isEmpty());
        Assertions.assertEquals(filmsList.size(), filmsListTest.size());
        Assertions.assertEquals(filmsList.get(0).getTittle(), filmsListTest.get(0).getTittle());
        Assertions.assertEquals(filmsList.get(1).getTittle(), filmsListTest.get(1).getTittle());
        Assertions.assertEquals(filmsList.get(2).getTittle(), filmsListTest.get(2).getTittle());
    }
}