package com.example.jack.hal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jack.hal.descriptors.Status;
import com.example.jack.hal.services.SocketService;

import org.json.JSONException;
import org.json.JSONObject;

public class LightActivity extends BaseActivity {

    private ListView listView_switch;
    private TextSwitchAdapter textSwitchAdapter;
    private TextSwitch[] textSwitches ;
    private String appliance_name;
    private String room_name;
    private String light_num;
    private boolean light_state;
    private BroadcastReceiver receiver;


    @Override
    protected String getToolBarTitle() {
        room_name = getIntent().getStringExtra("room_name");
        appliance_name = getIntent().getStringExtra("appliance_name");

        return room_name + "/" + appliance_name;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_light;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        room_name = getIntent().getStringExtra("room_name");
        appliance_name = getIntent().getStringExtra("appliance_name");
        light_num = getIntent().getStringExtra("light_num");
        String light_state_str = getIntent().getStringExtra("light_state");
        light_state = light_state_str.equals("on") ? true : false;

        textSwitches = new TextSwitch[] { new TextSwitch("On/Off", R.id.light_button, light_state, Status.OFF) };

        listView_switch = (ListView)findViewById(R.id.light_listview);

        textSwitchAdapter = new TextSwitchAdapter(this, R.layout.layout_single_textview, textSwitches, light_num);
        listView_switch.setAdapter(textSwitchAdapter);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String s = intent.getStringExtra(SocketService.SOCKET_MESSAGE);
                int[] updates = parseResult(s);
                Log.d("adaptor message: ", s);
                TextSwitch textSwitch = (TextSwitch) listView_switch.getItemAtPosition(0);
                Button button = (Button) findViewById(textSwitch.getaSwitchId());

                if (updates != null) {
                    int position = idToPosition(updates[0]);
                    Status status = stateToStatus(updates[1]);
                }

            }
        };
    }

    private int idToPosition(int id) {
        switch (id) {
            case 16:
                return 0;
            case 17:
                return 1;
            case 32:
                return 2;
        }
    }

    private int[] parseResult(String msg) {
        try {

            JSONObject obj = new JSONObject(msg);
            int id = (Integer)obj.get("id");
            int state = (Integer)obj.get("state");
            return new int[] {id, state};
        } catch (JSONException e) {

        }
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(SocketService.SOCKET_RESULT)
        );
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();
    }
}
