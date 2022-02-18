package com.app.repository;

import com.app.model.role.Role;
import com.app.model.user.User.User;

import java.util.List;

public class RoleRepository extends AbstractRepository<Role> {
    @Override
    public List<Role> findAll() {
        return (List<Role>) entityManager.createNativeQuery("SELECT role_id, name from role", User.class)
                .getResultList();
    }

    @Override
    public void add(Role entity) {
        // jdbcTemplate.update("insert into role(role_id, name) VALUES (?, ?)", entity.getId(), entity.getName());
    }

    @Override
    public void delete(int id) {
        Role role = entityManager.find(Role.class, id);
        entityManager.remove(role);
    }

    @Override
    public void edit(Role entity) {
        // jdbcTemplate.update("update role set role_id = ?, name = ?", entity.getId(), entity.getName());
    }

    @Override
    public List<Role> findByName(String name) {
        return (List<Role>) entityManager.createNativeQuery("Select role_id, name from role where name = :name",
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
}
