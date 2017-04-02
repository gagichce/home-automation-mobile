package com.example.jack.hal.descriptors;

/**
 * Created by Jack on 2017-03-12.
 */

public class DeviceDescriptor {
    private int id;
    private int roomId;
    private String name;
    private Status status;

    public DeviceDescriptor(int id, String name, int roomId, Status status) {
        this.id = id;
        this.name = name;
        this.roomId = roomId;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
