package com.example.jack.hal;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Jack on 2017-02-05.
 */

public class HttpAsynTask extends AsyncTask<String, Void, Boolean> {


    @Override
    protected Boolean doInBackground(String... params) {
        try {

            System.out.print("asdfsfs");

            HttpGet httppost;
            String light_num = params[1];
            if (params[0] == "on") {
                httppost  = new HttpGet(Global.on_url.get(light_num));
            } else {
                httppost = new HttpGet(Global.off_url.get(light_num));
            }
            //------------------>>

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httppost);

            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);


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
