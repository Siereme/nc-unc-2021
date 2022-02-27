package com.app.repository;

import com.app.model.role.Role;
import com.app.model.user.User.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class RoleRepository extends AbstractRepository<Role> {
    @Override
    public List<Role> findAll() {
        return entityManager.createQuery("SELECT distinct r FROM Role r left join fetch r.users", Role.class)
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
        entityManager.persist(entity);
    }

    @Override
    public List<Role> findByName(String name) {
        return entityManager.createQuery("Select distinct r from Role r left join fetch r.users where r.name = :name",
                Role.class).setParameter("name", name).getResultList();
    }

    @Override
    public int size() {
        /*return jdbcTemplate.queryForObject("Select count(*) from role", Integer.TYPE);*/
        //TODO
        return -1;
    }

    @Override
    public List<Role> findByContains(String name) {
        return null;
    }

    @Override
    public Role findById(int id) {
        return null;
    }
}
