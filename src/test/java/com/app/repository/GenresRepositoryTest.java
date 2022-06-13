package com.app.repository;

import com.app.model.genre.Genre;
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
public class GenresRepositoryTest {

    @Autowired
    private GenresRepository repository;
    private Connection connection;

    @BeforeAll
    void initGenres() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "sa");
    }

    List<Genre> getGenres(ResultSet resultSet) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        while (resultSet.next()){
            Genre genre = new Genre();
            genre.setId(resultSet.getInt("genre_id"));
            genre.setTittle(resultSet.getString("tittle"));
            genres.add(genre);
        }
        return genres;
    }

    @Test
    @Order(2)
    void testFindAll() throws SQLException {
        List<Genre> genresListTest = repository.findAll();


        PreparedStatement statement = connection.prepareStatement("SELECT * FROM genre");
        List<Genre> genresList = getGenres(statement.executeQuery());

        Assertions.assertEquals(genresList.size(), genresListTest.size());
        Assertions.assertEquals(genresList.get(0).getTittle(), genresListTest.get(0).getTittle());
        Assertions.assertEquals(genresList.get(1).getTittle(), genresListTest.get(1).getTittle());
        Assertions.assertEquals(genresList.get(2).getTittle(), genresListTest.get(2).getTittle());
    }

    @Test
    @Order(3)
    void testFindById() throws SQLException {
        Genre genreFindTest = repository.findById(1);

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM genre WHERE genre.genre_id = 1");
        Genre genre = getGenres(statement.executeQuery()).get(0);

        Assertions.assertEquals(genre.getId(), genreFindTest.getId());
        Assertions.assertEquals(genre.getTittle(), genreFindTest.getTittle());
    }

    @Test
    @Order(4)
    void find() throws SQLException {
        List<Genre> actorsListTest = repository.find(List.of(0, 1, 2));

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM genre WHERE genre.genre_id IN (0, 1, 2)");
        List<Genre> genresList = getGenres(statement.executeQuery());

        Assertions.assertEquals(genresList.size(), actorsListTest.size());
        Assertions.assertEquals(genresList.get(0).getTittle(), actorsListTest.get(0).getTittle());
        Assertions.assertEquals(genresList.get(1).getTittle(), actorsListTest.get(1).getTittle());
    }

    @Test
    @Order(1)
    void testAdd() throws SQLException {
        Genre genre = new Genre("genre4");

        repository.add(genre);
        Genre genreAddTest = repository.findByName("genre4").get(0);

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM genre WHERE genre.tittle = 'genre4'");
        Genre genreAdd = getGenres(statement.executeQuery()).get(0);

        Assertions.assertEquals(genreAdd.getId(), genreAddTest.getId());
        Assertions.assertEquals(genreAdd.getTittle(), genreAddTest.getTittle());
    }

    @Test
    @Order(9)
    void testDelete() throws SQLException {
        repository.delete(1);
        Genre genreDeleteTest = repository.findById(1);

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM genre WHERE genre.genre_id = 1");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int genreDelete = resultSet.getInt(1);

        Assertions.assertNull(genreDeleteTest);
        Assertions.assertEquals(0, genreDelete);
    }

    @Test
    @Order(8)
    void testEdit() throws SQLException {
        Genre genre = repository.findById(1);
        genre.setTittle("new tittle");

        repository.edit(genre);
        Genre genreEditTest = repository.findById(1);

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM genre WHERE genre.genre_id = 1");
        Genre genreEdit = getGenres(statement.executeQuery()).get(0);

        Assertions.assertEquals(genreEdit.getTittle(), genreEditTest.getTittle());
    }

    @Test
    @Order(5)
    void testFindByName() throws SQLException {
        List<Genre> genresListTest = repository.findByName("genre1");

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM genre WHERE genre.tittle = 'genre1'");
        List<Genre> genresList = getGenres(statement.executeQuery());
        Assertions.assertEquals(genresList.get(0).getTittle(), genresListTest.get(0).getTittle());
    }

    @Test
    @Order(6)
    void testSize() throws SQLException {
        int sizeTest = repository.size();

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM genre");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int size = resultSet.getInt(1);

        Assertions.assertEquals(size, sizeTest);
    }

    @Test
    @Order(7)
    void testFindByContains() throws SQLException {
        List<Genre> genresListTest = repository.findByContains("genre");

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM genre WHERE genre.tittle LIKE '%genre%'");
        List<Genre> genresList = getGenres(statement.executeQuery());

        Assertions.assertFalse(genresListTest.isEmpty());
        Assertions.assertEquals(genresList.size(), genresListTest.size());
        Assertions.assertEquals(genresList.get(0).getTittle(), genresListTest.get(0).getTittle());
        Assertions.assertEquals(genresList.get(1).getTittle(), genresListTest.get(1).getTittle());
        Assertions.assertEquals(genresList.get(2).getTittle(), genresListTest.get(2).getTittle());
    }

}
