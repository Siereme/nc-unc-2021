package com.app.repository;

import com.app.model.role.Role;
import com.app.model.user.User.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Repository
public class RoleRepository extends AbstractRepository<Role> {
    @Override
    public List<Role> findAll() {
        return entityManager.createQuery("SELECT distinct r FROM Role r", Role.class)
                .getResultList();
    }

    @Transactional
    @Override
    public void add(Role entity) {
        entityManager.persist(entity);
    }

    @Transactional
    @Override
    public void delete(int id) {
        Role role = entityManager.find(Role.class, id);
        entityManager.remove(role);
    }

    @Transactional
    @Override
    public void edit(Role entity) {
        entityManager.merge(entity);
    }

    @Override
    public List<Role> findByName(String name) {
        return entityManager.createQuery("Select distinct r from Role r where r.name = :name",
                Role.class).setParameter("name", name).getResultList();
    }

    @Override
    public int size() {
        BigInteger bigInteger =
                (BigInteger) entityManager.createNativeQuery("SELECT count(*) from role", BigInteger.class)
                        .getSingleResult();
        return bigInteger.intValue();
    }

    @Override
    public List<Role> findByContains(String name) {
        return entityManager.createQuery(
                        "select distinct r from Role r where r.name like :name ESCAPE '!'", Role.class)
                .setParameter("name", '%' + name + '%').getResultList();
    }

    @Override
    public Role findById(int id) {
        return entityManager.createQuery("select r from Role r where r.id = :id", Role.class)
                .setParameter("id", id).getSingleResult();
    }
}
