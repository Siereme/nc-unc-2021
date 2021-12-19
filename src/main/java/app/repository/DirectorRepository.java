package app.repository;

import app.model.director.Director;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/** Director app.repository
 * @see AbstractRepository
 * @see Director
 * @author Vasiliy,Sergey
 * @version 1.0
 * */
public class DirectorRepository extends AbstractRepository<Director>  {
    /** Путь для сериализации\десериализации */
    public static final String DIRECTOR_FILE_PATH = new File("src/main/resources/database/Directors.json").getAbsolutePath();


    public DirectorRepository(Director... directors) {
        super(DIRECTOR_FILE_PATH, Arrays.asList(directors));
        this.entities.addAll(Arrays.asList(directors));
    }

    public DirectorRepository() {
        super(DIRECTOR_FILE_PATH);
        init();
    }

    public void init() {
        try {
            entities.addAll(deserialize());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Director> findAll() {
        return entities;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        int ind = 0;
        for (Director director : entities) {
            sb.append(ind).append(". ").append(director).append("\n");
            ++ind;
        }
        return new String(sb);
    }

    public int size() {
        return entities.size();
    }

}
