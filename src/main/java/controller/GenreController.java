package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.IEntity;
import model.genre.Genre;
import repository.GenreRepository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

/** Контроллер для сущности жанр
 * @see Genre
 * @see IEntityController
 * @see GenreRepository
 * @author Vasiliy
 * @version 1.0
 * */
public class GenreController implements IEntityController<Genre> {

    public GenreRepository getGenreRepository() {
        return genreRepository;
    }

    GenreRepository genreRepository = new GenreRepository();

    public GenreController() {
        genreRepository = new GenreRepository();
    }

    public Genre getEntityById(String id) {
        for (Genre genre : genreRepository.findAll()) {
            if (Objects.equals(genre.getId(), id)) {
                return genre;
            }
        }
        return null;
    }

    public String entityToString(Genre genre) {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ").append(genre.getId()).append("\n");
        sb.append("Tittle: ").append(genre.getTittle()).append("\n");
        return new String(sb);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Genre genre : genreRepository.findAll()) {
            sb.append(ind).append(". ").append(entityToString(genre)).append("\n");
            ++ind;
        }
        return new String(sb);
    }

    public String entitiesByIDsToString(LinkedList<String> genres) {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (String s : genres) {
            sb.append(ind++).append(". ").append(entityToString(getEntityById(s))).append("\n");
        }
        return new String(sb);
    }

    public int size() {
        return genreRepository.size();
    }

    public Genre getEntity(int ind) {
        return genreRepository.findAll().get(ind);
    }

    public void addEntity(IEntity entity) {
        genreRepository.findAll().add((Genre) entity);
    }

    public void updateRepository() {
        try {
            genreRepository.serialize(new ObjectMapper());
        } catch (IOException e) {
            System.out.println("Serialize corrupted... " + e);
        }
    }

    public void removeEntity(int ind) {
        genreRepository.findAll().remove(ind);
    }

    @Override
    public LinkedList<String> getEntities() {
        LinkedList<String> ids = new LinkedList<>();
        for (Genre genre : genreRepository.findAll()) {
            ids.add(genre.getId());
        }
        return ids;
    }

}
