package com.example.jack.hal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class LightActivity extends BaseActivity {

    private ListView listView_switch;
    private TextSwitchAdapter textSwitchAdapter;
    private TextSwitch[] textSwitches ;
    private String appliance_name;
    private String room_name;
    private String light_num;
    private boolean light_state;


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
//        setContentView(R.layout.activity_light);

        room_name = getIntent().getStringExtra("room_name");
        appliance_name = getIntent().getStringExtra("appliance_name");
        light_num = getIntent().getStringExtra("light_num");
        String light_state_str = getIntent().getStringExtra("light_state");
        light_state = light_state_str.equals("on") ? true : false;

        Toast.makeText(this, "Light_State is " + Boolean.toString(light_state), Toast.LENGTH_SHORT).show();

        textSwitches = new TextSwitch[] { new TextSwitch("On/Off", R.id.light_switch, light_state) };

        listView_switch = (ListView)findViewById(R.id.light_listview);

        textSwitchAdapter = new TextSwitchAdapter(this, R.layout.layout_single_textview, textSwitches, light_num);
        listView_switch.setAdapter(textSwitchAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
