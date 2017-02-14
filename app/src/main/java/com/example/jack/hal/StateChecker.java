package com.example.jack.hal;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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

    private Global.APPLIANCE_STATE statecodeToState(int statecode) {
        if (statecode != 0 || statecode != 1) {
            // todo error
        }

        return statecode == 0 ? Global.APPLIANCE_STATE.OFF : Global.APPLIANCE_STATE.ON;
    }



    @Override
    protected void onPostExecute(JSONArray jsonArray) {

        if (jsonArray == null) {
            Global.isServerUp = false;
            return;
        }

        Global.isServerUp = true;

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject obj = jsonArray.getJSONObject(i);
                if ((Integer)obj.get("id") == 1) {
//                    Global.states.put("Light 1", statecodeToState((Integer)obj.get("relay_one")));
//                    Global.states.put("Light 2", statecodeToState((Integer)obj.get("relay_two")));
                    Global.updateStates("Light 1", statecodeToState((Integer)obj.get("relay_one")));
                    Global.updateStates("Light 2", statecodeToState((Integer)obj.get("relay_two")));
                } else if ((Integer)obj.get("id") == 2) {
//                    Global.states.put("Light 3", statecodeToState((Integer)obj.get("relay_one")));
//                    Global.states.put("Light 4", statecodeToState((Integer)obj.get("relay_two")));
                    Global.updateStates("Light 3", statecodeToState((Integer)obj.get("relay_one")));
                    Global.updateStates("Light 4", statecodeToState((Integer)obj.get("relay_two")));
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
