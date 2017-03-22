package com.example.jack.hal;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.toolbox.HttpClientStack;
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
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import com.example.jack.hal.descriptors.Status;

import java.io.IOException;

/**
 * Created by Jack on 2017-02-05.
 */

public class HttpAsynTask extends AsyncTask<String, Void, Boolean> {


    @Override
    protected void onPostExecute(Boolean success) {

    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {

            HttpGet httpGet;
            HttpClientStack.HttpPatch httpPatch;
            HttpResponse response;
            HttpClient httpclient = new DefaultHttpClient();

            if (params.length == 1 && params[0].equals("get-states")) {
                httpGet = new HttpGet(Global.url.get("states"));
                response = httpclient.execute(httpGet);

                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    Log.d("Httpget call", "success");
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.d("GET response data", data);
                }

            } else {


                String light_num = params[1];
                Log.d("Light number", light_num);
                String uri = Global.url.get(light_num);


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

                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
