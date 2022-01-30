package com.example.app.repository;

import com.example.app.model.role.Role;
import com.example.app.model.user.User.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;

@Repository
public class UserRepository extends AbstractRepository<User> implements UserDetailsService {
    @Override
    public List<User> findAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM user",
                (rs, rowNum) -> new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password")));
        for (User user : users) {
            getRolesForUser(user);
        }
        return users;
    }

    @Override
    public void add(User entity) {
        jdbcTemplate.update("INSERT INTO user(username, password) VALUES(?, ?)", entity.getUsername(),
                entity.getPassword());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM user WHERE user_id=?", id);
    }

    @Override
    public void edit(User entity) {
        jdbcTemplate.update("UPDATE user SET username=?, password=? WHERE user_id=?", entity.getUsername(),
                entity.getPassword());
    }

    @Override
    public List<User> findByName(String name) {
        List<User> users = jdbcTemplate.query("SELECT * FROM data_base.user where username = ?",
                (rs, rowNum) -> new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password")),
                name);
        for (User user : users) {
            getRolesForUser(user);
        }
        return users;
    }

    @Override
    public int size() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM user", Integer.class);
    }

    @Override
    public List<User> findByContains(String name) {
        return null;
    }

    public boolean saveUser(User user) {
        String userName = user.getUsername();
        List<User> users = findByName(userName);
        if (!users.isEmpty()) {
            return false;
        }

        String password = user.getPassword();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);

        add(user);
        return true;
    }

    private void getRolesForUser(User user) {
        int id = user.getId();
        List<Role> roles = jdbcTemplate.query(
                "SELECT r.role_id , r.name FROM data_base.role r join user_role ur on ur.role_id = r.role_id where user_id = ?",
                ((rs, rowNum) -> new Role(rs.getInt("role_id"), rs.getString("name"))), id);
        HashSet<Role> roleSet = new HashSet<>(roles);
        user.setRoles(roleSet);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        List<User> users = findByName(userName);
        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("user not found");
        }
        return users.get(0);
    }
}
