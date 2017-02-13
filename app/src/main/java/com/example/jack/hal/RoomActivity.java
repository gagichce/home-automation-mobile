package com.example.jack.hal;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class RoomActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private String[] applianceStr = {"Light 1", "Light 2", "Light 3", "Light 4"};
    private ApplianceItem[] appliances;
    private ArrayAdapter<String> arrayAdapter;
    private ApplianceItemAdapter applianceItemAdapter;
    private String room_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        String[] rooms = new String []{
                "Light 1", "Light 2",
                "Light 3", "Light 4"
        };

        appliances = new ApplianceItem[4];

        for (int i = 0; i < rooms.length; i++) {
            String state = Global.states.get(rooms[i]).toString().toLowerCase();
            appliances[i] = new ApplianceItem(rooms[i], state);
        }

        room_name = getIntent().getStringExtra("room_name");

        toolbar = (Toolbar)findViewById(R.id.toolbar_room);
        toolbar.setTitle(room_name);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = (ListView)findViewById(R.id.sampleroom_listview);

//        arrayAdapter = new ArrayAdapter<>(this, R.layout.layout_single_textview, applianceStr);
        applianceItemAdapter = new ApplianceItemAdapter(this, R.layout.layout_single_textview, appliances);
        listView.setAdapter(applianceItemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    default:



                        ApplianceItem item = (ApplianceItem)parent.getItemAtPosition(position);
                        String appliance_name = item.getApplianceName();
                        String appliance_state = item.getApplianceState();

                        Intent lightIntent = new Intent(getApplicationContext(), LightActivity.class);
                        lightIntent.putExtra("appliance_name", appliance_name);
                        lightIntent.putExtra("room_name", room_name);
                        lightIntent.putExtra("light_num", appliance_name);
                        lightIntent.putExtra("light_state", appliance_state);
                        startActivity(lightIntent);
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


    private class ApplianceItem {
        private String applianceName;

        public void setApplianceState(String applianceState) {
            this.applianceState = applianceState;
        }

        private String applianceState;

        public ApplianceItem(String applianceName, String applianceState) {
            this.applianceName = applianceName;
            this.applianceState = applianceState;
        }

        public String getApplianceName() {
            return applianceName;
        }

        public String getApplianceState() {
            return applianceState;
        }
    }

    private class ApplianceItemAdapter extends ArrayAdapter<ApplianceItem> {

        private Context context;
        private int resource;
        private ApplianceItem[] data;

        public ApplianceItemAdapter(Context context, int resource, ApplianceItem[] data) {
            super(context, resource, data);
            this.context = context;
            this.resource = resource;
            this.data = data;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.room_listview_content, parent, false);
            }

            ApplianceItem item = getItem(position);

            TextView applianceName = (TextView)convertView.findViewById(R.id.room_appliance_name);
            TextView applianceState = (TextView)convertView.findViewById(R.id.room_appliance_state);

            applianceName.setText(item.getApplianceName());
            applianceState.setText(item.getApplianceState());

            return convertView;
        }
    }

}
