package com.example.jack.hal;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Jack on 2017-02-12.
 */

public class StateChecker extends AsyncTask<String, Void, JSONArray> {

    private static final String stateAPI = "http://10.0.2.2:3000/api/current-state";

    private boolean statecodeToBool(String statecode) {
        if (statecode != "0" || statecode != "1") {
            // todo error
        }

        int code = Integer.parseInt(statecode);
        return code == 0 ? false : true;
    }


    @Override
    protected void onPostExecute(JSONArray jsonArray) {
//        super.onPostExecute(jsonArray);
//
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject obj = jsonArray.getJSONObject(i);
                if (obj.get("id") == "1") {
                    Global.states.put("1", statecodeToBool((String)obj.get("relay_one")));
                    Global.states.put("2", statecodeToBool((String)obj.get("relay_two")));
                } else if (obj.get("id") == "2") {
                    Global.states.put("3", statecodeToBool((String)obj.get("relay_three")));
                    Global.states.put("4", statecodeToBool((String)obj.get("relay_four")));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected JSONArray doInBackground(String... params) {

        try {
            HttpGet httpPost = new HttpGet(stateAPI);

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httpPost);

            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);


                JSONArray jsono = new JSONArray(data);
                Log.w("json infomation", jsono.toString());

                return jsono;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return null;
    }

}
