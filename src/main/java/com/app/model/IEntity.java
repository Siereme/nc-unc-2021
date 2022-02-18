package com.app.model;


//@JsonTypeInfo(
//        use = JsonTypeInfo.Id.NAME,
//        property = "type")
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = User.class, name = "visitor"),
//        @JsonSubTypes.Type(value = Genre.class, name = "genre"),
//        @JsonSubTypes.Type(value = Actor.class, name = "actor"),
//        @JsonSubTypes.Type(value = Director.class, name = "director"),
//        @JsonSubTypes.Type(value = Film.class, name = "film"),
//    }
//)
public interface IEntity{
    int getId();
}
