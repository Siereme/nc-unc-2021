package dto.request;

import java.util.LinkedList;

public class CreateGetEntitiesByNamesRequest extends Request {
    public LinkedList<String> getNames() {
        return names;
    }

    private final LinkedList<String> names;

    CreateGetEntitiesByNamesRequest(LinkedList<String> names) {
        this.names = names;
    }

    public String toString() {
        return "GetEntitiesByNamesRequest : request " + "names = " + names;
    }

}
