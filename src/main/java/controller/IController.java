package controller;

import java.util.LinkedList;

public interface IController<T> {

    T getEntityById(String id);

    String entityToString(T entity);

    String entitiesByIDsToString(LinkedList<String> ids);

    int size();

    T getEntity(int ind);

    void addEntity();

    void updateRepository();

    void removeEntity(int ind);

}
