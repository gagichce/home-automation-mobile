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
import com.example.jack.hal.interfaces.AsyncResponse;

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

    private View v;
    private TextSwitch textSwitch;
    private com.example.jack.hal.descriptors.Status status;

    public HttpAsynTask(View v, TextSwitch textSwitch, com.example.jack.hal.descriptors.Status status) {
        this.v = v;
        this.textSwitch = textSwitch;
        this.status = status;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        Button button = (Button)this.v.findViewById(R.id.light_button);

        if (success) {
            if (status == com.example.jack.hal.descriptors.Status.ON) {
                button.setBackgroundColor(Color.GRAY);
                textSwitch.setStatus(com.example.jack.hal.descriptors.Status.OFF);
            } else {
                button.setBackgroundColor(Color.GREEN);
                textSwitch.setStatus(com.example.jack.hal.descriptors.Status.ON);
            }
        } else {
            button.setBackgroundColor(Color.RED);
            textSwitch.setStatus(status);
        }
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {

            System.out.print("asdfsfs");

            HttpClientStack.HttpPatch httpPatch;
            httpPatch = new HttpClientStack.HttpPatch("http://10.0.2.2:3030/devices/32");



            String light_num = params[1];

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

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httpPatch);

            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();
            Log.d("http call status", Integer.toString(status));

            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);

                Log.d("response data", data);

//                JSONObject jsono = new JSONObject(data);
                Global.isServerUp = true;

                return true;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
