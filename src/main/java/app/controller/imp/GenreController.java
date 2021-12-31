package app.controller.imp;

import app.controller.IEntityController;
import app.model.IEntity;
import app.model.director.Director;
import app.model.genre.Genre;
import app.repository.imp.GenreRepository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/** Genre app.controller
 * @see Genre
 * @see IEntityController
 * @see GenreRepository
 * @author Vasiliy
 * @version 1.0
 * */
public class GenreController implements IEntityController<Genre> {

    public GenreRepository getRepository() {
        return repository;
    }

    final GenreRepository repository;

    public GenreController() {
        repository = new GenreRepository();
    }

    @Override
    public String getNames() {
        StringBuffer sb = new StringBuffer();
        for (Genre genre : repository.findAll()) {
            sb.append(genre.getTittle()).append("\n");
        }
        return new String(sb);
    }

    @Override
    public String getAllEntitiesAsString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Genre genre : repository.findAll()) {
            sb.append(ind++).append(". ");
            sb.append(entityToString(genre));
            sb.append("\n");
        }
        return new String(sb);
    }

    @Override
    public String getIdByName(String name) {
        for (Genre genre : repository.findAll()) {
            String genreName = genre.getTittle();
            if (Objects.equals(genreName, name)) {
                return genre.getId();
            }
        }
        return null;
    }

    @Override
    public boolean remove(String id) {
        Genre genre = getEntityById(id);
        return repository.findAll().remove(genre);
    }

    @Override
    public boolean remove(Genre entity) {
        FilmController filmController = new FilmController();
        repository.findAll().remove(entity);
        filmController.removeGenreFromAllFilms(entity);
        return updateRepository();
    }

    public void addEntity(IEntity entity) {
        FilmController filmController = new FilmController();
        Genre genre = new Genre((Genre) entity);
        filmController.addGenreToFilms(genre , genre.getFilms());
        getRepository().findAll().add(genre);
        updateRepository();
    }

    @Override
    public boolean edit(Genre entity) {
        Genre genre = getEntityById(entity.getId());
        genre.setTittle(entity.getTittle());
        editEntityInFilms(genre, entity);
        genre.clearFilms();
        genre.setFilms(entity.getFilms());
        return updateRepository();
    }

    private void editEntityInFilms(Genre genre, Genre editGenre) {
        FilmController filmController = new FilmController();
        List<String> addList = new LinkedList<>();
        List<String> removeList = new LinkedList<>();
        for(String id : genre.getFilms()){
            boolean isContains = editGenre.getFilms().stream().anyMatch(editId -> Objects.equals(editId, id));
            if(!isContains) removeList.add(id);
        }
        for(String editId : editGenre.getFilms()){
            boolean isContains = genre.getFilms().stream().anyMatch(id -> Objects.equals(id, editId));
            if(!isContains) addList.add(editId);
        }
        if(addList.size() > 0){
            filmController.addGenreToFilms(genre, addList);
        }
        if(removeList.size() > 0){
            filmController.removeGenreFromFilms(genre, removeList);
        }
    }

    public Genre getEntityById(String id) {
        for (Genre genre : repository.findAll()) {
            if (Objects.equals(genre.getId(), id)) {
                return genre;
            }
        }
        return null;
    }

    @Override
    public Genre getEntityByName(String title) {
        for (Genre genre : repository.findAll()) {
            if (Objects.equals(genre.getTittle(), title)) {
                return genre;
            }
        }
        return null;
    }

    public String entityToString(Genre genre) {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ").append(genre.getId()).append(" ");
        sb.append("Tittle: ").append(genre.getTittle()).append(" ");
        return new String(sb);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Genre genre : repository.findAll()) {
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
        return repository.size();
    }

    public Genre getEntity(int ind) {
        return repository.findAll().get(ind);
    }


    public boolean updateRepository() {
        try {
            repository.serialize();
            return true;
        } catch (IOException e) {
            System.out.println("Serialize corrupted... " + e);
            return false;
        }
    }

    public void removeEntity(int ind) {
        repository.findAll().remove(ind);
        updateRepository();
    }

    @Override
    public LinkedList<String> getEntities() {
        LinkedList<String> ids = new LinkedList<>();
        for (Genre genre : repository.findAll()) {
            ids.add(genre.getId());
        }
        return ids;
    }

    public void setFilmToEntities(String filmId, List<String> genreIds){
        if(genreIds.size() > 0){
            for(String id : genreIds){
                Genre genre = getEntityById(id);
                genre.setFilm(filmId);
            }
            updateRepository();
        }
    }

    public void deleteFilmFromEntities(String filmId, List<String> genreIds){
        if(genreIds.size() > 0){
            for(String id : genreIds){
                Genre genre = getEntityById(id);
                genre.deleteFilm(filmId);
            }
            updateRepository();
        }
    }
    public void deleteFilmFromEntities(String filmId){
        for(Genre genre : repository.findAll()){
            genre.deleteFilm(filmId);
        }
        updateRepository();
    }

}
