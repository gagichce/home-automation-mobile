package com.example.jack.hal.services;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.toolbox.HttpClientStack;
import com.example.jack.hal.Global;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by Jack on 2017-04-02.
 */

public class AsynTaskPatternState extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {

        String patternId = params[0];
        Log.d("Pattern device id", params[0]);


        try {
            HttpClientStack.HttpPatch httpPatch;
            HttpDelete httpDelete;
            HttpResponse response;
            HttpClient httpclient = new DefaultHttpClient();

            String uri = Global.url.get("patterns") + patternId;
            Log.d("Pattern uri", uri);

            httpPatch = new HttpClientStack.HttpPatch(uri);
            httpDelete = new HttpDelete(uri);


            if (params[1].equals("on")) {

                String patchString = "{\"status\" : 1 }";
                StringEntity entity = new StringEntity(patchString, "UTF-8");
                entity.setContentType("application/json");
                httpPatch.setEntity(entity);
            } else if (params[1].equals("off")) {
                httpPatch = new HttpClientStack.HttpPatch(uri);
                String patchString = "{\"status\" : 0 }";
                StringEntity entity = new StringEntity(patchString, "UTF-8");
                entity.setContentType("application/json");
                httpPatch.setEntity(entity);
            }


            if (params[1].equals("delete")) {
                response = httpclient.execute(httpDelete);
            } else {
                response = httpclient.execute(httpPatch);
            }


            int status = response.getStatusLine().getStatusCode();
            Log.d("http call status", Integer.toString(status));

            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);

                Log.d("response data", data);

                return data;
            }

        } catch (Exception e) {

        }

        return null;
    }
}
