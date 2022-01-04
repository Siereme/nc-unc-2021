package app.controller;

import app.model.IEntity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/** Controller Interface
 * @author Vasiliy
 * @version 1.0
 * */
public interface IEntityController<T extends IEntity> {
    /** method of getting the entity by id
     * @param id - id entity, to be found
     * @return entity type object with id = @id
     * */
    T getEntityById(String id);

    /** method of getting the entity by name
     * @param name - id entity, to be found
     * @return entity type object with id = @id
     * */
    T getEntityByName(String name);

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

    /** Method gets count of app.repository items
     * @see app.repository.IRepository
     * @return return count of app.repository items
     * */
    int size();

    /** Method gets entity by its index
     * @see app.repository.IRepository
     * @param ind - index of entity
     * @return entity with index = @ind
     * */
    T getEntity(int ind);

    /** Method adds entity to app.repository
     * @param entity - entity which will be added
     * @see app.repository.IRepository
     * */
    void addEntity(IEntity entity);

    /** Method updates app.repository
     * @see app.repository.IRepository
     * */
    boolean updateRepository();

    /** Method removes entity from app.repository by its(entity) index
     * @param ind - index of the entity being deleted
     * @see app.repository.IRepository
     * */
    void removeEntity(int ind);

    /** Method gets linked list with ids of entities
     * @return Linked list of id with all entities
     * */
    LinkedList<String> getEntities();

    String getNames();

    String getAllEntitiesAsString();

    String getIdByName(String name);

    default LinkedList<String> getIdsByNames(LinkedList<String> names) {
        LinkedList<String> ids = new LinkedList<>();
        for (String name : names) {
            String id = getIdByName(name);
            if (id != null) {
                if (!ids.contains(id)) {
                    ids.add(id);
                }
            }
        }
        return ids;
    }

    default List<T> getEntitiesByNames(List<String> names) {
//        LinkedList<String> ids = getIdsByNames(names);
        LinkedList<T> entities = new LinkedList<>();
        for (String name : names) {
            T entity = getEntityByName(name);
            entities.add(entity);
        }
        return entities;
    }

    boolean remove(String id);

    boolean remove(T entity);

    boolean edit(T entity);

    List<T> findAll();

    List<T> findBy(Map<String, List<String>> entityIds);
}
