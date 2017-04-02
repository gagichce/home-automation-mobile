package com.example.jack.hal.services;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.toolbox.HttpClientStack;
import com.example.jack.hal.Global;
import com.example.jack.hal.descriptors.DeviceDescriptor;

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

public class AsynTaskDevices extends AsyncTask<String, Void, String> {


    @Override
    protected void onPostExecute(String data) {

        if (data == null) {
            return;
        }

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
    }

    @Override
    protected String doInBackground(String... params) {
        HttpGet httpGet;
        HttpResponse response;
        HttpClient httpclient = new DefaultHttpClient();

        try {
            httpGet = new HttpGet(Global.url.get("states"));
            response = httpclient.execute(httpGet);

            int status = response.getStatusLine().getStatusCode();
            if (status == 200) {
                Log.d("Httpget call", "success");
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

