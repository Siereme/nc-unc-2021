package com.app.repository;

import com.app.model.user.User.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Transactional
@Repository
public class UserRepository extends AbstractRepository<User> implements UserDetailsService {

    @Override
    public List<User> findAll() {
        return entityManager.createNamedQuery("User.findAllWithRoles", User.class).getResultList();
    }

    @Override
    @Transactional
    public void add(User entity) {
        entityManager.persist(entity);
/*        jdbcTemplate.update("INSERT INTO user(username, password) VALUES(?, ?)", entity.getUsername(),
                entity.getPassword());*/
    }

    @Override
    public void delete(int id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public void edit(User entity) {

    }

    @Override
    public List<User> findByName(String username) {

        try {
            return (List<User>) entityManager.createQuery(
                            "select distinct u from User u left join fetch u.roles where u.username = :name")
                    .setParameter("name", username).getResultList();
        } catch (NoResultException e) {
            // или вернуть пустую коллекцию
            return null;
        }
    }

    @Override
    public int size() {
        return (int) entityManager.createNativeQuery("SELECT count(*) FROM user", Integer.class).getSingleResult();
    }

    @Override
    public List<User> findByContains(String name) {
        return null;
    }

    public boolean saveUser(User user) {
        String userName = user.getUsername();
        if (!(isUserExist(userName))) {
            String password = user.getPassword();

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
            user.setPassword(hashedPassword);

            add(user);
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        List<User> users = findByName(userName);
        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("user not found");
        }
        return users.get(0);
    }

    public boolean isUserExist(String userName) {
        BigInteger count =
                (BigInteger) entityManager.createNativeQuery("Select count(*) from user where username = :username")
                        .setParameter("username", userName).getSingleResult();
        // передаю спасибо https://stackoverflow.com/questions/31072498/how-to-check-if-a-biginteger-is-null
        return !BigInteger.ZERO.equals(count);
    }

    @Override
    public User findById(int id) {
        return null;
    }
}
