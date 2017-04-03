package com.example.jack.hal;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jack.hal.descriptors.DeviceDescriptor;
import com.example.jack.hal.descriptors.PatternDescriptor;
import com.example.jack.hal.descriptors.Status;
import com.example.jack.hal.pattern.PatternListActivity;
import com.example.jack.hal.services.AsynDelegate;
import com.example.jack.hal.services.HttpAsynTask;
import com.example.jack.hal.services.SocketService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomActivity extends BaseActivity implements AsynDelegate {


    private ListView listView;
    private ApplianceItemAdapter applianceItemAdapter;
    private String room_name;
    public ApplianceItem[] appliances;
    private BroadcastReceiver receiver;


    @Override
    public void asyncComplete(boolean success) {
        applianceItemAdapter.notifyDataSetChanged();
    }

    @Override
    protected String getToolBarTitle() {
        room_name = getIntent().getStringExtra("room_name");
        return room_name;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_room;
    }


    private void constructApplianceItem(int roomId) {
        List<String> applianceNames = new ArrayList<>();
        List<Status> applianceStatus = new ArrayList<>();
        List<Integer> applianceIds = new ArrayList<>();

        ArrayList<DeviceDescriptor> deviceDescriptors = new ArrayList<>(Global.devices.values());

        for (int i = 0; i < deviceDescriptors.size(); i++) {
            DeviceDescriptor device =deviceDescriptors.get(i);
            if (device.getRoomId() == roomId) {
                applianceNames.add(device.getName());
                applianceStatus.add(device.getStatus());
                applianceIds.add(device.getId());
            }
        }

        appliances = new ApplianceItem[applianceNames.size()];

        for (int i = 0; i < applianceNames.size(); i++) {
            int id = applianceIds.get(i);
            String name = applianceNames.get(i);
            appliances[i] = new ApplianceItem(name, R.id.room_button, applianceStatus.get(i), id);
            Global.id2position.put(id, i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int roomId = getIntent().getIntExtra("roomId", -1);

        constructApplianceItem(roomId);

        listView = (ListView)findViewById(R.id.sampleroom_listview);

        applianceItemAdapter = new ApplianceItemAdapter(this, R.layout.layout_single_textview, appliances);
        listView.setAdapter(applianceItemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView =  (TextView) view.findViewById(R.id.room_appliance_name);
                String deviceName = textView.getText().toString();

                switch (position) {

                    default:
                        Log.d("Room listview", "Position " + Integer.toString(position) + " is pressed");
                        Intent intent = new Intent(RoomActivity.this, PatternListActivity.class);
                        int deviceId = (Integer)view.getTag();
                        intent.putExtra("deviceId", deviceId);
                        intent.putExtra("deviceName", deviceName);
                        startActivity(intent);
                        break;
                }
            }
        });

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String s = intent.getStringExtra(SocketService.SOCKET_MESSAGE);
                Log.d("Receiver", s);
                int[] updates = Global.parseResult(s);



                if (updates != null) {

                    Log.d("UPDATES", Arrays.toString(updates));

                    int id = updates[0];

                    Integer position = Global.id2position.get(id);

                    if (position == null) {
                        return;
                    }


                    View v = getViewByPosition(position, listView);

                    ApplianceItem item = (ApplianceItem) listView.getItemAtPosition(position);
                    ImageButton button = (ImageButton) v.findViewById(item.getButtonId());

                    Status status = Global.stateToStatus(updates[1]);
                    item.setStatus(status);
                    switch (status) {
                        case OFF:
                            button.setBackgroundColor(Color.TRANSPARENT);
                            break;
                        case ON:
                            button.setBackgroundColor(Color.GREEN);
                            break;
                        case ERROR:
                            button.setBackgroundColor(Color.RED);
                            break;
                        case PENDING:
                            button.setBackgroundColor(Color.YELLOW);
                            break;
                    }
                }

            }
        };

    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ROOM ACTIVITY", "OnResume called");


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }


    private class ApplianceItem {
        private String applianceName;
        private int buttonId;
        private Status status;
        private int id;

        public ApplianceItem(String applianceName, int buttonId, Status status, int id) {
            this.applianceName = applianceName;
            this.buttonId = buttonId;
            this.status = status;
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setApplianceName(String applianceName) {
            this.applianceName = applianceName;
        }

        public void setButtonId(int buttonId) {
            this.buttonId = buttonId;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public int getButtonId() {
            return buttonId;
        }

        public Status getStatus() {
            return status;
        }

        public String getApplianceName() {
            return applianceName;
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
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.room_listview_content, parent, false);
            }

            final ApplianceItem item = getItem(position);

            TextView applianceName = (TextView)convertView.findViewById(R.id.room_appliance_name);
            final ImageButton button = (ImageButton)convertView.findViewById(R.id.room_button);

            applianceName.setText(item.getApplianceName());

            convertView.setTag(item.getId());

            switch (item.getStatus()) {
                case OFF:
                    button.setBackgroundColor(Color.TRANSPARENT);
                    break;
                case ON:
                    button.setBackgroundColor(Color.GREEN);
                    break;
                case ERROR:
                    button.setBackgroundColor(Color.RED);
                    break;
                case PENDING:
                    button.setBackgroundColor(Color.YELLOW);
                    break;
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Status current_status = item.getStatus();
                    String pos_str = Integer.toString(position);
                    String id = Integer.toString(item.getId());
                    switch (current_status) {
                        case OFF:
                            new HttpAsynTask().execute("on", id);
                            item.setStatus(Status.PENDING);
                            break;
                        case ON:
                            new HttpAsynTask().execute("off", id);
                            item.setStatus(Status.PENDING);
                            break;
                        case ERROR:
                            new HttpAsynTask().execute("off", id);
                            item.setStatus(Status.PENDING);
                            break;
                        case PENDING:
                            break;
                    }
                }
            });

            return convertView;
        }
    }

}
