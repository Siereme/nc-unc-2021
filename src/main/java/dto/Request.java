package dto;


import java.io.Serializable;
import java.util.Map;

public class Request implements Serializable {
    public Request(String   name) {
        this.name = name;
    }
    private String name;

}
