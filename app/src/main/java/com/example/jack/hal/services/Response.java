package com.example.jack.hal.services;

/**
 * Created by Jack on 2017-04-01.
 */

enum InstType {
    DEVICES, ROOMS, ACTIONS
};

public class Response {
    private InstType type;
    private String data;

    public Response(String data, InstType type) {
        this.data = data;
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public InstType getType() {
        return type;
    }

    public void setType(InstType type) {
        this.type = type;
    }
}