package com.example.jack.hal.services;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.toolbox.HttpClientStack;
import com.example.jack.hal.Global;
import com.example.jack.hal.descriptors.RoomDescriptor;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jack on 2017-04-01.
 */

public class AsynTaskRooms extends AsyncTask<String, Void, String> {

    private AsynDelegate asynDelegate;

    public AsynTaskRooms(AsynDelegate asynDelegate) {
        this.asynDelegate = asynDelegate;
    }

    @Override
    protected void onPostExecute(String data) {

        if (data == null) {
            return;
        }

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

            this.asynDelegate.asyncComplete(true);



        } catch (JSONException e) {

        }
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            HttpGet httpGet;
            HttpClientStack.HttpPatch httpPatch;
            HttpResponse response;
            HttpClient httpclient = new DefaultHttpClient();

            httpGet = new HttpGet(Global.url.get("rooms"));
            response = httpclient.execute(httpGet);

            int status = response.getStatusLine().getStatusCode();
            if (status == 200) {
                Log.d("Httpget call ROOM", "success");
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);
                Log.d("GET response data", data);
                return data;

            }

        } catch (Exception e) {

        }

        return null;

    }
}
