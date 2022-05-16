package com.app.model.user;

import com.app.model.IEntity;
import com.app.model.confirmEmail.ConfirmEmail;
import com.app.model.film.Film;
import com.app.model.role.Role;
import com.app.validator.CheckUserName;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/** Visitor entity
 * privileges are less than those of the admin
 * @author Sergey
 * @version 1.0
 * */

@JsonTypeName("visitor")
@CheckUserName
public class User implements IEntity, UserDetails {

    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Id
    private int user_id;
    private String username;
    private String password;
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

    @NotNull
    @Size(min = 1, message = "pick at least one role")
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
        this.username = "";
        this.password = "";
        this.roles = new HashSet<>();
    }

    public User(int user_id, String username, String password) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.roles = new HashSet<>();
    }

    public User(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User(String username, String password, Set<Role> roles, Set<Film> films) {
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

    public int getUser_id() {
        return user_id;
    }

    @Override
    public int getId() {
        return getUser_id();
    }
}
