package com.example.jack.hal;

import java.util.HashMap;

/**
 * Created by Jack on 2017-02-05.
 */

public class Constant {

    public static final String light_on_1 = "http://192.168.254.2:3000/api/2/1/1";
    public static final String light_off_1 = "http://192.168.254.2:3000/api/2/1/0";

    public static final String light_on_2 = "http://192.168.254.2:3000/api/2/1/1";
    public static final String light_off_2 = "http://192.168.254.2:3000/api/2/1/0";

    public static final String light_on_3 = "http://192.168.254.2:3000/api/2/1/1";
    public static final String light_off_3 = "http://192.168.254.2:3000/api/2/1/0";

    public static final String light_on_4 = "http://192.168.254.2:3000/api/2/1/1";
    public static final String light_off_4 = "http://192.168.254.2:3000/api/2/1/0";

    public static final HashMap<String, String> on_url = new HashMap<>();
    public static final HashMap<String, String> off_url = new HashMap<>();


    static {
        on_url.put("1", "http://192.168.254.2:3000/api/1/2/1");
        on_url.put("2", "http://192.168.254.2:3000/api/1/1/1");
        on_url.put("3", "http://192.168.254.2:3000/api/2/2/1");
        on_url.put("4", "http://192.168.254.2:3000/api/2/1/1");

        off_url.put("1", "http://192.168.254.2:3000/api/1/2/0");
        off_url.put("2", "http://192.168.254.2:3000/api/1/1/0");
        off_url.put("3", "http://192.168.254.2:3000/api/2/2/0");
        off_url.put("4", "http://192.168.254.2:3000/api/2/1/0");



    }
}
