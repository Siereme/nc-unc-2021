package com.example.app.model;


import com.example.app.model.actor.Actor;
import com.example.app.model.director.Director;
import com.example.app.model.film.Film;
import com.example.app.model.genre.Genre;
import com.example.app.model.user.Admin.Admin;
import com.example.app.model.user.Visitor.Visitor;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Admin.class, name = "admin"),
        @JsonSubTypes.Type(value = Visitor.class, name = "visitor"),
        @JsonSubTypes.Type(value = Genre.class, name = "genre"),
        @JsonSubTypes.Type(value = Actor.class, name = "actor"),
        @JsonSubTypes.Type(value = Director.class, name = "director"),
        @JsonSubTypes.Type(value = Film.class, name = "film"),
    }
)
public interface IEntity {
    String getId();
}
