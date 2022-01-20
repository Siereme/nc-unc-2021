package com.example.app.repository;

import com.example.app.model.role.Role;

import java.util.List;

public class RoleRepository implements IRepository<Role>{
    @Override
    public List<Role> findAll() {
        return null;
    }

    @Override
    public void add(Role entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void edit(Role entity) {

    }

    @Override
    public List<Role> findByName(String name) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
