package com.example.jack.hal.pattern;

import com.example.jack.hal.Global;
import com.example.jack.hal.R;
import com.example.jack.hal.descriptors.PatternDescriptor;
import com.example.jack.hal.descriptors.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jack on 2017-04-02.
 */

public class ExpandableDataPump {

    private int deviceId;

    public ExpandableDataPump(int deviceId) {
        this.deviceId = deviceId;
    }

    public  ArrayList<Integer> getPatternIds() {
        ArrayList<Integer> ids;

        if (this.deviceId == -1) {
            ids = new ArrayList<>(Global.patterns.keySet());
        } else {
            ids = new ArrayList<>();

            for (Map.Entry<Integer, PatternDescriptor> entry : Global.patterns.entrySet()) {

                if (entry.getValue().getDeviceId() == this.deviceId) {
                    ids.add(entry.getKey());
                }
            }
        }

        return ids;

    }

    public Map<Integer, List<Item>> getData () {
        HashMap<Integer, List<Item>> expandableListDetail = new
                HashMap<>();


        ArrayList<PatternDescriptor> patternDescriptors;


        if (this.deviceId == -1) {
            patternDescriptors = new ArrayList<>(Global.patterns.values());
        } else {
            patternDescriptors = new ArrayList<>();

            for (Map.Entry<Integer, PatternDescriptor> entry : Global.patterns.entrySet()) {

                if (entry.getValue().getDeviceId() == this.deviceId) {
                    patternDescriptors.add(entry.getValue());
                }
            }
        }

        for (PatternDescriptor pattern : patternDescriptors) {
            int id = pattern.getId();
            int deviceId = pattern.getDeviceId();
            int roomId = Global.devices.get(deviceId).getRoomId();
            String deviceName = Global.devices.get(deviceId).getName();
            String roomName = Global.rooms.get(roomId).getName();
            String description = pattern.getDescription();
            PatternState state = pattern.getStatus();

            TextSwitch textSwitch = new TextSwitch(roomName + ", " + deviceName, R.id.pattern_listview_switch, state);
            Item item = new Item(id, textSwitch, description, R.id.pattern_listview_btn);

            ArrayList<Item> items = new ArrayList<>();
            items.add(item);

            expandableListDetail.put(id, items);
        }

        return expandableListDetail;
    }
}
