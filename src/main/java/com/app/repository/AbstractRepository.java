package com.app.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public abstract class AbstractRepository<T> implements IRepository<T> {

/*    @Resource(name = "entityManager")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }*/

    @PersistenceContext
    protected EntityManager entityManager;

/*    @Override
    @Transactional
    public void delete(int id) {
        IEntity entity = entityManager.find(IEntity.class, id);
        entityManager.remove(entity);
    }*/

/*    @Autowired
    protected SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }*/

/*    @Autowired
    protected EntityManager entityManager;

    public EntityManager getEntityManager(){
        return entityManager;
    }

    @Resource(name = "entityManager")
    public void setEntityManager(EntityManager entityManager){
        this.entityManager = entityManager;
    }*/

}
