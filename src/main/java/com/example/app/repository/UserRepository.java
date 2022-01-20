package com.example.app.repository;

import com.example.app.model.user.User.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository extends AbstractRepository<User> implements UserDetailsService {
    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM user",
                (rs, rowNum) -> new User(rs.getInt("user_id"), rs.getString("name"), rs.getString("password")));
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
        return jdbcTemplate.query("SELECT * FROM data_base.user where username = ?",
                (rs, rowNum) -> new User(rs.getInt("user_id"), rs.getString("name"), rs.getString("password")));
    }

    @Override
    public int size() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM user", Integer.class);
    }

    public boolean saveUser(User user) {
        String userName = user.getUsername();
        List<User> users = findByName(userName);
        if (users != null) {
            return false;
        }

        String password = user.getPassword();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);

        add(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // ???
        List<User> users = findByName(userName);
        if (users == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return users.get(0);
    }
}
