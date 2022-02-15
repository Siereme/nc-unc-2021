package com.example.app.repository;

import com.example.app.model.user.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

@Repository
public class UserRepository extends AbstractRepository<User> implements UserDetailsService {

    @Lazy
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return entityManager.createNamedQuery("User.findAllWithRoles", User.class).getResultList();
    }

    @Override
    @Transactional
    public void add(User entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
/*        jdbcTemplate.update("INSERT INTO user(username, password) VALUES(?, ?)", entity.getUsername(),
                entity.getPassword());*/
    }

    @Transactional
    @Override
    public void delete(int id) {
        User user = entityManager.find(User.class, id);
/*        entityManager.getTransaction().begin();
        entityManager.remove(user);
        entityManager.getTransaction().commit();*/
        entityManager.remove(user);
    }

    @Override
    public void edit(User entity) {
        jdbcTemplate.update("UPDATE user SET username=?, password=? WHERE user_id=?", entity.getUsername(),
                entity.getPassword());
    }

    @Override
    public List<User> findByName(String username) {

        // ?...
        try {
            // TODO
            User user = (User) entityManager.createQuery(
                            "select distinct u from User u left join fetch u.roles where u.username = :name")
                    .setParameter("name", username).getResultList();
            List<User> users = new LinkedList<>();
            users.add(user);
            return users;
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
        if (!(userRepository.isUserExist(userName))) {
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

}
