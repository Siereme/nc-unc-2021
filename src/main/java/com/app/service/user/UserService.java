package com.app.service.user;

import com.app.model.confirmEmail.ConfirmEmail;
import com.app.model.role.Role;
import com.app.model.user.User;
import com.app.service.AbstractService;
import com.app.service.SequenceGeneratorService;
import com.app.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class UserService extends AbstractService<User> implements UserDetailsService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RoleService roleService;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @Override
    public List<User> findByContains(String query) {
        Criteria regex = Criteria.where("username").regex(query);
        return mongoTemplate.find(new Query().addCriteria(regex), User.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        List<User> users = mongoTemplate.find(query, User.class);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("username not found");
        } else {
            return users.get(0);
        }
    }

    public boolean isUserExist(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        List<User> users = mongoTemplate.find(query, User.class);
        return !users.isEmpty();
    }

    public boolean saveUser(User user) {
        String userName = user.getUsername();
        if (!(isUserExist(userName))) {
            String password = user.getPassword();

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
            user.setPassword(hashedPassword);

            user.setId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));

            mongoTemplate.save(user);
            return true;
        }
        return false;
    }

    public void addRoleToUser(User user, String Role) {
        Role role = roleService.findByName(Role);
        user.getRoles().add(role);
    }

    public boolean isTokenExist(String token) {
        User user = getCurrentUser();
        Query query = new Query();
        query.addCriteria(Criteria.where("token").is(token));
        query.addCriteria(Criteria.where("userId").is(user.getId()));
        List<ConfirmEmail> confirmEmails = mongoTemplate.find(query, ConfirmEmail.class);
        if (confirmEmails.size() != 0) {
            LocalDateTime now = LocalDateTime.now();
            return confirmEmails.get(0).getEndDate().isAfter(now);
        }
        return false;
    }

    public int getCountActiveLinks(User user) {
        int userId = user.getId();
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        LocalDateTime now = LocalDateTime.now();
        int count = 0;
        List<ConfirmEmail> confirmEmails = mongoTemplate.find(query, ConfirmEmail.class);
        for (ConfirmEmail confirmEmail : confirmEmails) {
            if (confirmEmail.getEndDate().isAfter(now)) {
                count++;
            }
        }
        return count;
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

    public void removeRoleFromUser(User user, String role) {
        Role removedRole = roleService.findByName(role);
        Set<Role> roleSet = user.getRoles();
        roleSet.remove(removedRole);
        user.setRoles(roleSet);
    }
}
