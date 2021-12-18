package com.gmail.viktor.yuryev.unc2021.shared.dto;

import java.io.Serializable;
import java.util.Map;

public class Response implements Serializable {
    private String name;

    public Response(String name) {
        this.name = name;
    }

}
