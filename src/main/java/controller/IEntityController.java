package controller;

import model.IEntity;

import java.util.LinkedList;

/** Controller Interface
 * @author Vasiliy
 * @version 1.0
 * */
public interface IEntityController<T> {

    /** method of getting the entity by id
     * @param id - id entity, to be found
     * @return entity type object with id = @id
     * */
    T getEntityById(String id);

    /** Method converts entity to string
     * @param entity - which will be converted to string
     * @return string value of @entity
     * */
    String entityToString(T entity);

    /** method searches for an entity with an id from the ids list
     *  then converts this entity into a string and so on for each ids from the list
     * @param ids - list of id entities
     * @return string value of all entities in @ids
     * */
    String entitiesByIDsToString(LinkedList<String> ids);

    /** Method gets count of repository items
     * @see repository.IRepository
     * @return return count of repository items
     * */
    int size();

    /** Method gets entity by its index
     * @see repository.IRepository
     * @param ind - index of entity
     * @return entity with index = @ind
     * */
    T getEntity(int ind);

    /** Method adds entity to repository
     * @param entity - entity which will be added
     * @see repository.IRepository
     * */
    void addEntity(IEntity entity);

    /** Method updates repository
     * @see repository.IRepository
     * */
    void updateRepository();

    /** Method removes entity from repository by its(entity) index
     * @param ind - index of the entity being deleted
     * @see repository.IRepository
     * */
    void removeEntity(int ind);

    /** Method gets linked list with ids of entities
     * @return Linked list of id with all entities
     * */
    LinkedList<String> getEntities();

    String getNames();

    String getAllEntitiesAsString();

}
