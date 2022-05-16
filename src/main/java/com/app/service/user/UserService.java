package com.app.service.user;

import com.app.model.genre.Genre;
import com.app.model.user.User;
import com.app.service.AbstractService;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends AbstractService<User> implements UserDetailsService {
    @Override
    public List<User> findByContains(String query) {
        Criteria regex = Criteria.where("username").regex(query);
        return mongoTemplate.find(new Query().addCriteria(regex), User.class);
    }

    // TODO
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    // TODO
    public boolean isUserExist(String username) {
        return false;
    }

    public boolean saveUser(User user) {
        String userName = user.getUsername();
        if (!(isUserExist(userName))) {
            String password = user.getPassword();

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
            user.setPassword(hashedPassword);

            mongoTemplate.save(user);
            return true;
        }
        return false;
    }

    // TODO
    public void addRoleToUser(User user, String Role) {
    }

    // TODO
    public boolean isTokenExist(String token) {
        return false;
    }

    public int getCountActiveLinks(User user) {
/*        int userId = user.getUser_id();
        LocalDateTime now = LocalDateTime.now();
        // TODO добавить сравнение даты
        //        BigInteger count = (BigInteger) entityManager.createNativeQuery(
        //                        "select count(*) from confirm_tokens where user_id = :user_id")
        //                .setParameter("user_id", userId).getSingleResult();
        //        return count.intValue();
        return 0;*/
        return -1;
    }

    public boolean isLinksEnough(User user, int countLinks) {
        int countActiveLinks = getCountActiveLinks(user);
        return countActiveLinks > countLinks;
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

    // TODO
    public void removeRoleNoConfirmedFromUser(User currentUser) {
    }
}
