package com.example.app.repository;

import com.example.app.model.actor.Actor;
import com.example.app.model.role.Role;

import java.util.List;

public class RoleRepository extends AbstractRepository<Role> {
    @Override
    public List<Role> findAll() {
        return jdbcTemplate.query("Select * from role",
                (rs, rowNum) -> new Role(rs.getInt("role_id"), rs.getString("name")));
    }

    @Override
    public void add(Role entity) {
        jdbcTemplate.update("insert into role(role_id, name) VALUES (?, ?)", entity.getId(), entity.getName());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("delete from role where role_id = ?", id);
    }

    @Override
    public void edit(Role entity) {
        jdbcTemplate.update("update role set role_id = ?, name = ?", entity.getId(), entity.getName());
    }

    @Override
    public List<Role> findByName(String name) {
        return jdbcTemplate.query("Select * from role where name = ?",
                (rs, rowNum) -> new Role(rs.getInt("role_id"), rs.getString("name")), name);
    }

    @Override
    public int size() {
        return jdbcTemplate.queryForObject("Select count(*) from role", Integer.TYPE);
    }
}
