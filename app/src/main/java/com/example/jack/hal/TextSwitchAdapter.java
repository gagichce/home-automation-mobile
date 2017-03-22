package com.example.jack.hal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jack.hal.descriptors.Status;
import com.example.jack.hal.interfaces.AsyncResponse;
import com.example.jack.hal.services.SocketService;

import java.util.Map;


/**
 * Created by Jack on 2017-02-05.
 */

public class TextSwitchAdapter extends ArrayAdapter<TextSwitch> {

    private Context context;
    private int layoutResourceId;
    private TextSwitch[] data;
    private String light_num;


    public TextSwitchAdapter(Context context, int layoutResourceId, TextSwitch[] data, String light_num) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.light_num = light_num.substring(light_num.length() - 1);


    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
//        return super.getView(position, convertView, parent);

        final TextSwitch textSwitch = getItem(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.light_listview_content, parent, false);
        }

        TextView label = (TextView)convertView.findViewById(R.id.light_room_text);
        final Button aSwitch = (Button)convertView.findViewById(R.id.light_button);

        label.setText(textSwitch.getLabel());
        aSwitch.setBackgroundColor(Color.GRAY);


//        aSwitch.setOnClicVkListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Status current_status = textSwitch.getStatus();
//                switch (current_status) {
//                    case OFF:
//                        new HttpAsynTask(v, textSwitch, current_status).execute("on", light_num);
//                        aSwitch.setBackgroundColor(Color.YELLOW);
//                        textSwitch.setStatus(Status.PENDING);
//
//
////                        Global.updateStates("Light " + light_num, Global.APPLIANCE_STATE.ON);
//                        break;
//                    case ON:
//                        new HttpAsynTask(v, textSwitch, current_status).execute("off", light_num);
//                        aSwitch.setBackgroundColor(Color.GRAY);
//                        textSwitch.setStatus(Status.PENDING);
////                        Global.updateStates("Light " + light_num, Global.APPLIANCE_STATE.OFF);
//                        break;
//                }
//            }
//        });


        return convertView;

    }



}
