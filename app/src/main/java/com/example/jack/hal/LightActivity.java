package com.example.jack.hal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

public class LightActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView_switch;
    private TextSwitchAdapter textSwitchAdapter;
    private TextSwitch[] textSwitches = { new TextSwitch("On/Off", R.id.light_switch, false) };
    private String appliance_name;
    private String room_name;
    private String light_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        room_name = getIntent().getStringExtra("room_name");
        appliance_name = getIntent().getStringExtra("appliance_name");
        light_num = getIntent().getStringExtra("light_num");


        toolbar = (Toolbar)findViewById(R.id.toolbar_light);
        toolbar.setTitle(room_name + "/" + appliance_name);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
