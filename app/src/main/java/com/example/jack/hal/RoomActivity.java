package com.example.jack.hal;

import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class RoomActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private String[] appliance = {"Light", "Curtains"};
    private ArrayAdapter<String> arrayAdapter;
    private String room_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        room_name = getIntent().getStringExtra("room_name");

        toolbar = (Toolbar)findViewById(R.id.toolbar_room);
        toolbar.setTitle(room_name);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = (ListView)findViewById(R.id.sampleroom_listview);

        arrayAdapter = new ArrayAdapter<>(this, R.layout.layout_tab, appliance);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String appliance_name = parent.getItemAtPosition(position).toString();

                switch (position) {
                    case 0:
                        Intent lightIntent = new Intent(getApplicationContext(), LightActivity.class);
                        lightIntent.putExtra("appliance_name", appliance_name);
                        lightIntent.putExtra("room_name", room_name);
                        startActivity(lightIntent);
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "Curtains is pressed", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}