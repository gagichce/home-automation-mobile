package com.example.jack.hal;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Jack on 2017-02-05.
 */

public class Global extends Application {

    public static final HashMap<String, String> on_url = new HashMap<>();
    public static final HashMap<String, String> off_url = new HashMap<>();
    public static HashMap<String, APPLIANCE_STATE> states = new HashMap<>();
    public static boolean isServerUp = true;
    public static final String IP = "192.168.254.2";
    public static final String URL = "http://" + IP;
    public static final String EMULATOR_LOCALHOST = "10.0.2.2";
//    public static final String EMULATOR_LOCALHOST = "localhost";
    public static final String STATE_API = "http://" + EMULATOR_LOCALHOST + ":3000/api/current-state";
    public static StateChecker stateChecker;


    private ActivityManager activityManager;
    private Context context;

    public enum APPLIANCE_STATE {
        ON,
        OFF
    }

    public static Socket mSocket;


    public Global() {
//        on_url.put("1", "http://" + IP + ":3000/api/1/1/1");
//        on_url.put("2", "http://" + IP + ":3000/api/1/2/1");
//        on_url.put("3", "http://" + IP + ":3000/api/2/1/1");
//        on_url.put("4", "http://" + IP + ":3000/api/2/2/1");
//
//        off_url.put("1", "http://" + IP + ":3000/api/1/1/0");
//        off_url.put("2", "http://" + IP + ":3000/api/1/2/0");
//        off_url.put("3", "http://" + IP + ":3000/api/2/1/0");
//        off_url.put("4", "http://" + IP + ":3000/api/2/2/0");


        on_url.put("1", "http://" + EMULATOR_LOCALHOST + ":3000/api/1/1/1");
        on_url.put("2", "http://" + EMULATOR_LOCALHOST + ":3000/api/1/2/1");
        on_url.put("3", "http://" + EMULATOR_LOCALHOST + ":3000/api/2/1/1");
        on_url.put("4", "http://" + EMULATOR_LOCALHOST + ":3000/api/2/2/1");

        off_url.put("1", "http://" + EMULATOR_LOCALHOST + ":3000/api/1/1/0");
        off_url.put("2", "http://" + EMULATOR_LOCALHOST + ":3000/api/1/2/0");
        off_url.put("3", "http://" + EMULATOR_LOCALHOST + ":3000/api/2/1/0");
        off_url.put("4", "http://" + EMULATOR_LOCALHOST + ":3000/api/2/2/0");

        states.put("Light 1", APPLIANCE_STATE.OFF);
        states.put("Light 2", APPLIANCE_STATE.OFF);
        states.put("Light 3", APPLIANCE_STATE.OFF);
        states.put("Light 4", APPLIANCE_STATE.OFF);



        try {
            Log.d("socket", "connecting");
            mSocket = IO.socket("http://10.0.2.2:3030");
            mSocket.on("devices updated", this.onStateChange).
                    on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    Log.d("socket", "Socket connection is successful");
                }

            })
                    .on(Socket.EVENT_ERROR, new Emitter.Listener() {

                        @Override
                        public void call(Object... args) {
                            Log.d("socket", "error");
                        }

                    });



            mSocket.connect();
        } catch (URISyntaxException e) {
            Log.d("socket", "crash");
            throw new RuntimeException(e);
        }
    }

    static {

    }

    public static String stateToString(APPLIANCE_STATE state) {

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

    public static void updateStates(String key, APPLIANCE_STATE val) {
        Log.d("Updating state:", "Old: key :" + key + ", " + states.get(key).toString().toLowerCase() + ",\t" +
                                 "New: key :" + key + ", " + val.toString().toLowerCase());
        states.put(key, val);
    }


    private Emitter.Listener onStateChange = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            Log.d("socket io", "an event has been triggered ----------------------------------");
            JSONObject data = (JSONObject)args[0];
            Log.d("scoket io", data.toString());

        }
    };
}
