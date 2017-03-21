package com.example.jack.hal.descriptors;

import java.util.List;

/**
 * Created by Jack on 2017-03-12.
 */

public class RoomDescriptor {
    private int id;
    private String name;
    private List<DeviceDescriptor> deviceDescriptors;


    public List<DeviceDescriptor> getDeviceDescriptors() {
        return deviceDescriptors;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public RoomDescriptor(int id, String name, List<DeviceDescriptor> deviceDescriptors) {
        this.id = id;
        this.name = name;
        this.deviceDescriptors = deviceDescriptors;
    }
}
