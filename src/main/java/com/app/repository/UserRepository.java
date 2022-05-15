package com.app.repository;

import com.app.model.confirmEmail.ConfirmEmail;
import com.app.model.film.Film;
import com.app.model.role.Role;
import com.app.model.user.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Transactional
@Repository
public class UserRepository extends AbstractRepository<User> implements UserDetailsService {


    public List<User> findByName(String username) {

/*        try {
            TypedQuery<User> query = entityManager.createQuery(
                    "select distinct u from User u left join fetch u.roles where u.username = :name", User.class);
            query.setParameter("name", username);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }*/
        return null;
    }




    public boolean saveUser(User user) {
        String userName = user.getUsername();
        if (!(isUserExist(userName))) {
            String password = user.getPassword();

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
            user.setPassword(hashedPassword);

            save(user);
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
//        BigInteger count =
//                (BigInteger) entityManager.createNativeQuery("Select count(*) from user where username = :username")
//                        .setParameter("username", userName).getSingleResult();
//        return !BigInteger.ZERO.equals(count);
        return true;
    }


    public void addRoleToUser(User user, String Role) {
    }

    public void addNoConfirmedRoleToUser(User user) {

    }

    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    public String getCurrentEmail() {
        User currentUser = getCurrentUser();
        return currentUser.getEmail();
    }

    public User getCurrentUser() {
        String currentUserName = getCurrentUsername();
        return (User) loadUserByUsername(currentUserName);
    }

    public boolean isTokenExist(String token) {
        return false;
    }

    public void removeRoleNoConfirmedFromUser(User user) {

    }

    public int getCountActiveLinks(User user) {
        int userId = user.getUser_id();
        LocalDateTime now = LocalDateTime.now();
        // TODO добавить сравнение даты
//        BigInteger count = (BigInteger) entityManager.createNativeQuery(
//                        "select count(*) from confirm_tokens where user_id = :user_id")
//                .setParameter("user_id", userId).getSingleResult();
//        return count.intValue();
        return 0;
    }

    public boolean isLinksEnough(User user, int countLinks) {
        int countActiveLinks = getCountActiveLinks(user);
        return countActiveLinks > countLinks;
    }

    @Override
    public <S extends User> S save(S entity) {
        return null;
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<User> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Iterable<User> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<User> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends User> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends User, R> R findBy(Example<S> example,
                                        Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public User findById(int id) {
        return null;
    }
}
