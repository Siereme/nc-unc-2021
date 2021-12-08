package model.User;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import model.User.Admin.Admin;
import model.User.Visitor.Visitor;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Admin.class, name = "admin"),
        @JsonSubTypes.Type(value = Visitor.class, name = "visitor") }
)
public interface IUser {
    String getId();
    String getName();
    void setName(String name);
    String getPassword();
    void setPassword(String password);
    Boolean getRole();
}
