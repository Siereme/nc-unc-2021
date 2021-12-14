package repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/** Repository interface
 * @author Sergey
 * @version 1.0
 * */
public interface IRepository<T> {
    /** Method gets list of entities
     * @return list of entities with type T
     * */
    List<T> findAll();

    /** Method removes Entity by its id
     * @param Id - id entity
     * @return true if deletion successful, else - false
     * */
    boolean deleteById(Integer Id);

    /** Method searches entity by its id
     * @param Id - id of the entity
     * @return true if search successful, else - false
     * */
    boolean findById(Integer Id);

    /** Method adds new entity to the repository
     * (unused)
     * */
    boolean create(T entity);

    /** unused */
    void clear();

    /** Method serializes repository
     * @param objectMapper - object to write repository to the file
     * */
    void serialize(ObjectMapper objectMapper) throws IOException;

    /** Method deserializes repository
     * @param objectMapper - object gets repository from the file
     * @return list of the entities with type T
     * */
    List<T> deserialize(ObjectMapper objectMapper) throws IOException;

    /** Method gets size - count of repository elements
     * @return count of repository elements
     * */
    int size();


}
