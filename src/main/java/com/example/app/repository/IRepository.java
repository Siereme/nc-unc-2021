package com.example.app.repository;

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

//    /** Method serializes app.repository **/
//    void serialize();
//
//    /** Method deserializes app.repository
//     * @return list of the entities with type T
//     * */
//    List<T> deserialize();
//
//    /** Method deserializes app.repository
//     * @param file - object gets app.repository from the file
//     * @return list of the entities with type T
//     * */
//    List<T> deserialize(String file);
//
//    /** Method gets size - count of app.repository elements
//     * @return count of app.repository elements
//     * */
    int size();


}
