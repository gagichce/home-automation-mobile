package com.example.jack.hal;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import com.example.jack.hal.descriptors.DeviceDescriptor;
import com.example.jack.hal.descriptors.RoomDescriptor;
import com.example.jack.hal.descriptors.Status;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static com.example.jack.hal.descriptors.Status.OFF;
import static com.example.jack.hal.descriptors.Status.ON;

/**
 * Created by Jack on 2017-02-05.
 */

public class Global extends Application {

    public static final HashMap<String, String> url = new HashMap<>();
    public static HashMap<String, Status> states = new HashMap<>();
    public static boolean isServerUp = true;
    public static final String IP = "192.168.254.2";
    public static final String URL = "http://" + IP;
    public static final String EMULATOR_LOCALHOST = "10.0.2.2";
    public static final String PORT = "3030";
//    public static final String EMULATOR_LOCALHOST = "localhost";
    public static final String STATE_API = "http://" + EMULATOR_LOCALHOST + ":3000/api/current-state";


    public static List<RoomDescriptor> rooms;
    public static List<DeviceDescriptor> devices;
    public static Map<Integer, Integer> id2position = new HashMap<>();


    public ActivityManager activityManager;
    public Context context;


    public static Socket mSocket;


    static {
        url.put("states", "http://" + EMULATOR_LOCALHOST + ":" + PORT + "/devices/");
        url.put("rooms", "http://" + EMULATOR_LOCALHOST + ":" + PORT + "/rooms/");
        url.put("16", "http://" + EMULATOR_LOCALHOST + ":" +  PORT + "/devices/16");
        url.put("17", "http://" + EMULATOR_LOCALHOST + ":" +  PORT + "/devices/17");
        url.put("32", "http://" + EMULATOR_LOCALHOST + ":" +  PORT + "/devices/32");
        url.put("33", "http://" + EMULATOR_LOCALHOST + ":" +  PORT + "/devices/33");


        states.put("16", OFF);
        states.put("17", OFF);
        states.put("32", OFF);
        states.put("33", OFF);

        devices = new ArrayList<>();
        rooms = new ArrayList<>();


    }


    public static String stateToString(Status state) {

        String ret = null;
        switch (state) {
            case ON:
                ret = "on";
                break;

            case OFF:
                ret = "off";
                break;
        }
        return ret;
    }

    public static void updateStates(String key, Status val) {
        Log.d("Updating state:", "key :" + key + ", " + val.toString().toLowerCase());

        for (DeviceDescriptor device : devices) {
            if (device.getId() == Integer.valueOf(key)) {
                device.setStatus(val);
            }
        }

//        states.put(key, val);
    }


    public static Status stateToStatus(int state) {
        switch (state) {
            case 0:
                return OFF;
            case 1:
                return ON;
            case 2:
                return Status.PENDING;
            case 3:
                return Status.ERROR;
        }

        return null;
    }

    public static int idToPosition(int id) {
        switch (id) {
            case 16:
                return 0;
            case 17:
                return 1;
            case 32:
                return 2;
            case 33:
                return 3;
        }
        return -1;
    }

    public static int[] parseResult(String msg) {
        try {

            JSONObject obj = new JSONObject(msg);
            int id = (Integer)obj.get("id");
            int state = (Integer)obj.get("state");
            return new int[] {id, state};
        } catch (JSONException e) {

        }
        return null;
    }


    public static List<DeviceDescriptor> getDevicesByRoomID(int roomId) {
        ArrayList<DeviceDescriptor> ret = new ArrayList<>();

        for (DeviceDescriptor device : devices) {
            if (device.getRoomId() == roomId) {
                ret.add(device);
            }
        }

        return ret;
    }

    public static Status[] parseStates(String msg) {

        Status[] ret = new Status[4];
        try {

            JSONObject all = new JSONObject(msg);
            JSONObject[] data = (JSONObject[]) all.get("data");

            for (int i = 0; i < data.length; i++) {
                JSONObject cell = data[i];

                int pos = idToPosition((Integer) cell.get("id"));

                ret[pos] = stateToStatus((Integer)cell.get("state"));

            }


        } catch (JSONException e) {

        }
        return ret;
    }


}
