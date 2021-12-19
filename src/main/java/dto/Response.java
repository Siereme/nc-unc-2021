package dto;

import java.io.Serializable;
import java.util.Map;

public class Response implements Serializable {
    private String name;

    public Response(String name) {
        this.name = name;
    }

}
