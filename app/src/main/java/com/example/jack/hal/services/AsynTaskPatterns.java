package com.example.jack.hal.services;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.toolbox.HttpClientStack;
import com.example.jack.hal.Global;
import com.example.jack.hal.descriptors.PatternDescriptor;
import com.example.jack.hal.descriptors.RoomDescriptor;
import com.example.jack.hal.pattern.PatternState;

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
 * Created by Jack on 2017-04-02.
 */

public class AsynTaskPatterns extends AsyncTask<String, Void, String> {

    @Override
    protected void onPostExecute(String data) {
        if (data == null) {
            return;
        }

        try {
            JSONObject wholeObj = new JSONObject(data);
            JSONArray patternArray = wholeObj.getJSONArray("data");

            for (int i = 0; i < patternArray.length(); i++) {
                JSONObject patternObj = patternArray.getJSONObject(i);
                int id = (Integer) patternObj.get("id");
                int deviceId = (Integer) patternObj.get("deviceId");
                String description = (String) patternObj.get("pattern_text");
                String patternRule = (String) patternObj.get("pattern_rule");

                Global.patterns.put(id ,new PatternDescriptor(
                        id,
                        deviceId,
                        description,
                        patternRule,
                        PatternState.int2State((Integer)patternObj.get("status"))
                ));
            }


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

            httpGet = new HttpGet(Global.url.get("patterns"));
            response = httpclient.execute(httpGet);

            int status = response.getStatusLine().getStatusCode();
            if (status == 200) {
                Log.d("Httpget call patterns", "success");
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
