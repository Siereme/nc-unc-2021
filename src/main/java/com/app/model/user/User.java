package com.app.model.user;

import com.app.model.IEntity;
import com.app.model.role.Role;
import com.app.validator.CheckUserName;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/** Visitor entity
 * privileges are less than those of the admin
 * @author Sergey
 * @version 1.0
 * */

@Document
@JsonTypeName("visitor")
@CheckUserName
public class User implements IEntity, UserDetails {

    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";

    public void setId(int id) {
        this.id = id;
    }

    @Id
    private int id;
    private String username;
    private String password;
    @Transient
    private String passwordConfirm;
    private String email;

    public Set<Integer> getConfirmEmailsIds() {
        return confirmEmailsIds;
    }

    public void setConfirmEmailsIds(Set<Integer> confirmEmailsIds) {
        this.confirmEmailsIds = confirmEmailsIds;
    }

    private Set<Integer> confirmEmailsIds = new HashSet<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private Set<Role> roles;

    public User() {
        this.username = "";
        this.password = "";
        this.roles = new HashSet<>();
    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = new HashSet<>();
    }

    public User(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.roles = new HashSet<>();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public int getId() {
        return id;
    }
}
