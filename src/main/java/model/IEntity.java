package model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import model.actor.Actor;
import model.director.Director;
import model.film.Film;
import model.genre.Genre;
import model.user.Admin.Admin;
import model.user.Visitor.Visitor;

import java.io.Serializable;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
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
public interface IEntity extends Serializable {
    String getId();
}
