package com.example.jack.hal.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.jack.hal.Global;
import com.example.jack.hal.descriptors.Status;

import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Jack on 2017-03-22.
 */

public class SocketService extends Service {
    private Socket mSocket;
    private LocalBroadcastManager broadcaster;

    public final static String SOCKET_RESULT = "com.example.jack.hal.services.REQUEST_PROCESSED";
    public final static String SOCKET_MESSAGE = "com.example.jack.hal.services.REQUEST_MSG";

    public void sendResult(String message) {
        Intent intent = new Intent(SOCKET_RESULT);
        if(message != null)
            intent.putExtra(SOCKET_MESSAGE, message);
        broadcaster.sendBroadcast(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        broadcaster = LocalBroadcastManager.getInstance(this);
        Log.d("SocketService", "onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("SockService", "onStartCommand");
        try {
            Log.d("socket", "connecting");
            mSocket = IO.socket("http://10.0.2.2:3030");
            mSocket.on("devices patched", this.onStateChange).
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
        return super.onStartCommand(intent, flags, startId);
    }

    private Emitter.Listener onStateChange = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            Log.d("socket io", "an event has been triggered ----------------------------------");
            JSONObject data = (JSONObject)args[0];
            Log.d("scoket io", data.toString());

            sendResult(data.toString());

            int[] updates = Global.parseResult(data.toString());

            if (updates != null) {

                int id = updates[0];
                Status status = Global.stateToStatus(updates[1]);
                Global.updateStates(Integer.toString(id), status);
            }

        }
    };
}
