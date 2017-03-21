package com.example.jack.hal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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
    public View getView(int position, View convertView, ViewGroup parent) {
//        return super.getView(position, convertView, parent);

        TextSwitch textSwitch = getItem(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.light_listview_content, parent, false);
        }

        TextView label = (TextView)convertView.findViewById(R.id.light_room_text);
        Switch aSwitch = (Switch)convertView.findViewById(R.id.light_switch);

        label.setText(textSwitch.getLabel());
        aSwitch.setChecked(textSwitch.getisChecked());


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // note isChecked here represents the new check state
                if (isChecked) {
                    new HttpAsynTask().execute("on", light_num);
                    Global.updateStates("Light " + light_num, Global.APPLIANCE_STATE.ON);
                } else {
                    new HttpAsynTask().execute("off", light_num);
                    Global.updateStates("Light " + light_num, Global.APPLIANCE_STATE.OFF);
                }
        }});

        return convertView;

    }



}
