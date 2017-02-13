package com.example.jack.hal;

import java.util.HashMap;

/**
 * Created by Jack on 2017-02-05.
 */

public final class Global {

    public static final HashMap<String, String> on_url = new HashMap<>();
    public static final HashMap<String, String> off_url = new HashMap<>();
    public static HashMap<String, APPLIANCE_STATE> states = new HashMap<>();

    public enum APPLIANCE_STATE {
        ON,
        OFF;
    }


    static {
        on_url.put("1", "http://192.168.254.2:3000/api/1/2/1");
        on_url.put("2", "http://192.168.254.2:3000/api/1/1/1");
        on_url.put("3", "http://192.168.254.2:3000/api/2/2/1");
        on_url.put("4", "http://192.168.254.2:3000/api/2/1/1");

        off_url.put("1", "http://192.168.254.2:3000/api/1/2/0");
        off_url.put("2", "http://192.168.254.2:3000/api/1/1/0");
        off_url.put("3", "http://192.168.254.2:3000/api/2/2/0");
        off_url.put("4", "http://192.168.254.2:3000/api/2/1/0");

        states.put("Light 1", APPLIANCE_STATE.OFF);
        states.put("Light 2", APPLIANCE_STATE.OFF);
        states.put("Light 3", APPLIANCE_STATE.OFF);
        states.put("Light 4", APPLIANCE_STATE.OFF);
    }

    public static boolean stateToBool(APPLIANCE_STATE state) {

        boolean ret = false;
        switch (state) {
            case ON:
                ret = true;
                break;

            case OFF:
                ret = false;
                break;
        }
        return ret;
    }
}
