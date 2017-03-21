package com.example.jack.hal.descriptors;

/**
 * Created by Jack on 2017-03-12.
 */

public class DeviceDescriptor {
    private int id;
    private String name;
    private Status status;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public DeviceDescriptor(int id, Status status, String name) {

        this.id = id;
        this.status = status;
        this.name = name;
    }
}
