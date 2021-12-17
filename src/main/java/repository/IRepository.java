package repository;

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

    /** Method serializes repository **/
    void serialize() throws IOException;

    /** Method deserializes repository
     * @return list of the entities with type T
     * */
    List<T> deserialize() throws IOException;

    /** Method deserializes repository
     * @param file - object gets repository from the file
     * @return list of the entities with type T
     * */
    List<T> deserialize(String file) throws IOException;

    /** Method gets size - count of repository elements
     * @return count of repository elements
     * */
    int size();


}
