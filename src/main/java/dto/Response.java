package dto;

import java.io.Serializable;

public class Response implements Serializable {
    final String message;

    public Response(String name) {
        message = name;
    }
}
