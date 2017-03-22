package com.example.jack.hal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jack.hal.descriptors.Status;
import com.example.jack.hal.services.SocketService;

import org.json.JSONException;
import org.json.JSONObject;

public class RoomActivity extends BaseActivity {

    private ListView listView;
    private String[] applianceStr = {"Light 1", "Light 2", "Light 3", "Light 4"};
    private ApplianceItemAdapter applianceItemAdapter;
    private String room_name;
    public ApplianceItem[] appliances;
    private BroadcastReceiver receiver;

    @Override
    protected String getToolBarTitle() {
        room_name = getIntent().getStringExtra("room_name");
        return room_name;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_room;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] room_appliances = new String []{
                "Light 1",
                "Light 2",
                "Light 3",
                "Light 4"
        };

        appliances = new ApplianceItem[4];

        for (int i = 0; i < room_appliances.length; i++) {
            Status status = Global.states.get(room_appliances[i].toString().toLowerCase());
            appliances[i] = new ApplianceItem(room_appliances[i], R.id.room_button, status);
        }



        listView = (ListView)findViewById(R.id.sampleroom_listview);

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
                int position = Global.idToPosition(updates[0]);
                View v = getViewByPosition(position, listView);

                ApplianceItem item = (ApplianceItem) listView.getItemAtPosition(position);
                Button button = (Button) v.findViewById(item.getButtonId());

                if (updates != null) {
                    Status status = Global.stateToStatus(updates[1]);
                    item.setStatus(status);
                    switch (status) {
                        case OFF:
                            button.setBackgroundColor(Color.GRAY);
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

        public ApplianceItem(String applianceName, int buttonId, Status status) {
            this.applianceName = applianceName;
            this.buttonId = buttonId;
            this.status = status;
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
            final Button button = (Button)convertView.findViewById(R.id.room_button);

            applianceName.setText(item.getApplianceName());

            switch (item.getStatus()) {
                case OFF:
//                    button.setBackgroundColor(Color.GRAY);
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
                    switch (current_status) {
                        case OFF:
                            new HttpAsynTask().execute("on", pos_str);
                            item.setStatus(Status.PENDING);
                            button.setBackgroundColor(Color.YELLOW);
                            break;
                        case ON:
                            new HttpAsynTask().execute("off", pos_str);
                            item.setStatus(Status.PENDING);
                            button.setBackgroundColor(Color.GREEN);
                            break;
                        case ERROR:
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
