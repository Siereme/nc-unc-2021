package com.app.model.emailInfo;

import java.util.HashSet;
import java.util.Set;

public class NewFilmEmail {
    private String userName;

    private String filmName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public Set<String> getActorNames() {
        return actorNames;
    }

    public void setActorNames(Set<String> actorNames) {
        this.actorNames = actorNames;
    }

    private Set<String> actorNames = new HashSet<>();

}
