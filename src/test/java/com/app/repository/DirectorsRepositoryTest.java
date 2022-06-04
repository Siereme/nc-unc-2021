package com.app.repository;

import com.app.model.director.Director;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DirectorsRepositoryTest {

    @Autowired
    DirectorsRepository directorsRepository;

    @Test
    void testFindAll() {
        List<Director> directorList = directorsRepository.findAll();
        assertThat(directorList.isEmpty()).isFalse();
    }

    @Test
    void testFindById() {
        Director director = directorsRepository.findById(3);
        assertThat(director.getId()).isEqualTo(3);
    }

    @Test
    void find() {
        List<Director> directors = directorsRepository.find(List.of(1, 2, 3));
        assertThat(directors.size()).isEqualTo(3);
    }

    @Test
    void testAdd() {
        Director director = new Director("director", "25");

        directorsRepository.add(director);

        Director addedDirector = directorsRepository.findByName("director").stream().findFirst().orElse(null);

        assertThat(addedDirector).isNotEqualTo(null);
        assert addedDirector != null;
        assertThat(addedDirector.getName()).isEqualTo("director");
        assertThat(addedDirector.getYear()).isEqualTo("25");
    }

    @Test
    void testDelete() {
        directorsRepository.delete(3);

        Director director = directorsRepository.findById(3);

        assertThat(director).isNull();
    }

    @Test
    void testEdit() {
        Director director = directorsRepository.findById(1);
        director.setName("new name");

        directorsRepository.edit(director);
        Director editedDirector = directorsRepository.findById(1);

        assertThat(editedDirector.getName()).isEqualTo("new name");
    }

    @Test
    void testFindByName() {
        List<Director> directors = directorsRepository.findByName("director1");
        Director director = directors.stream().findFirst().orElse(null);

        assertThat(director).isNotNull();
        assertThat(director.getName()).isEqualTo("director1");
    }

    @Test
    void testSize() {
    }

    @Test
    void testFindByContains() {
        List<Director> directors = directorsRepository.findByContains("director");
        assertThat(directors.isEmpty()).isFalse();
    }

}
