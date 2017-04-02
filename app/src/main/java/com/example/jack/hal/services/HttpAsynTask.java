package com.example.jack.hal.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.toolbox.HttpClientStack;
import com.example.jack.hal.Global;
import com.example.jack.hal.descriptors.DeviceDescriptor;
import com.example.jack.hal.descriptors.RoomDescriptor;
import com.example.jack.hal.descriptors.Status;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import com.example.jack.hal.descriptors.Status;

import java.io.IOException;

/**
 * Created by Jack on 2017-02-05.
 */

public class HttpAsynTask extends AsyncTask<String, Void, Response> {


    @Override
    protected void onPostExecute(Response response) {

        if (response == null) {
            return;
        }

        String data;

        switch (response.getType()) {
            case ROOMS:
                 data = response.getData();
                try {
                    JSONObject wholeObj = new JSONObject(data);
                    JSONArray roomArray = wholeObj.getJSONArray("data");

                    for (int i = 0; i < roomArray.length(); i++) {
                        JSONObject roomObj = roomArray.getJSONObject(i);
                        int id = (Integer) roomObj.get("id");
                        Global.rooms.add(new RoomDescriptor(
                                id,
                                (String) roomObj.get("name"),
                                Global.getDevicesByRoomID(id)
                        ));
                    }



                } catch (JSONException e) {

                }

                break;

            case DEVICES:
                data = response.getData();
                try {
                    JSONObject wholeObj = new JSONObject(data);
                    JSONArray deviceArray = wholeObj.getJSONArray("data");

                    for (int i = 0; i < deviceArray.length(); i++) {
                        JSONObject deviceObj = deviceArray.getJSONObject(i);
                        Global.devices.add(new DeviceDescriptor(
                                (Integer) deviceObj.get("id"),
                                (String) deviceObj.get("name"),
                                (Integer) deviceObj.get("roomId"),
                                Global.stateToStatus((Integer)deviceObj.get("state"))
                        ));
                    }
                } catch (JSONException e) {

                }


                break;
            case ACTIONS:
                break;


        }



    }

    @Override
    protected Response doInBackground(String... params) {
        try {

            HttpGet httpGet;
            HttpClientStack.HttpPatch httpPatch;
            HttpResponse response;
            HttpClient httpclient = new DefaultHttpClient();



                String id = params[1];
                Log.d("Light id", id);
                String uri = Global.url.get(id);


                httpPatch = new HttpClientStack.HttpPatch(uri);


                if (params[0] == "on") {
                    String patchString = "{\"state\" : 2 }";
                    StringEntity entity = new StringEntity(patchString, "UTF-8");
                    entity.setContentType("application/json");
                    httpPatch.setEntity(entity);
                } else {
                    String patchString = "{\"state\" : 0 }";
                    StringEntity entity = new StringEntity(patchString, "UTF-8");
                    entity.setContentType("application/json");
                    httpPatch.setEntity(entity);
                }

                response = httpclient.execute(httpPatch);

                int status = response.getStatusLine().getStatusCode();
                Log.d("http call status", Integer.toString(status));

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);

                    Log.d("response data", data);

                    return new Response(data, InstType.ACTIONS);
                }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}





