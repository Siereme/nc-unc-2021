package com.example.app.model.user.User;

import com.example.app.model.IEntity;
import com.example.app.model.role.Role;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/** Visitor entity
 * privileges are less than those of the admin
 * @author Sergey
 * @version 1.0
 * */
@JsonTypeName("visitor")
public class User implements IEntity, UserDetails {
    private int id;

    private String username;

    private String password;

    private String passwordConfirm;

    private Set<Role> roles;

    public User() {
        this.username = "";
        this.password = "";
    }

    public User(int id, String name, String password) {
        this.username = name;
        this.password = password;
    }

    @Override
    public int getId() {
        return this.id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
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
}
