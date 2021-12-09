package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Director.Director;
import repository.DirectorRepository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

public class DirectorController implements IController<Director> {

    public DirectorRepository getDirectorRepository() {
        return directorRepository;
    }

    public void setDirectorRepository(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    private DirectorRepository directorRepository = new DirectorRepository();

    public Director getEntityById(String id) {
        for (Director director : directorRepository.findAll()) {
            if (Objects.equals(director.getId(), id)) {
                return director;
            }
        }
        return null;
    }

    public String entityToString(Director director) {
        StringBuffer sb = new StringBuffer();
        sb.append("Id: ").append(director.getId()).append("\n");
        sb.append("Name: ").append(director.getName()).append("\n");
        sb.append("Year: ").append(director.getYear()).append("\n");
        if (director.getFilms().isEmpty()) {
            sb.append("Films is empty\n");
        } else {
            FilmController filmController = new FilmController();
            sb.append(filmController.entitiesByIDsToString(director.getFilms())).append("\n");
        }
        return new String(sb);

    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Director director : directorRepository.findAll()) {
            sb.append(ind++).append(". ").append(entityToString(director)).append("\n");
        }
        return new String(sb);
    }

    public String entitiesByIDsToString(LinkedList<String> directors) {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (String d : directors) {
            sb.append(ind++).append(". ").append(entityToString(getEntityById(d))).append("\n");
        }
        return new String(sb);
    }

    public int size() {
        return directorRepository.size();
    }

    public Director getEntity(int ind) {
        return directorRepository.findAll().get(ind);
    }

    public void addEntity() {
        directorRepository.findAll().add(new Director());
    }

    public void updateRepository() {
        try {
            directorRepository.serialize(new ObjectMapper());
        } catch (IOException e) {
            System.out.println("Serialize corrupted... " + e);
        }
    }

    public void removeEntity(int ind){
        directorRepository.findAll().remove(ind);
    }

}
