package com.example.jack.hal;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jack on 2017-02-05.
 */

public class TextSwitchAdapter extends ArrayAdapter<TextSwitch> {

    private Context context;
    private int layoutResourceId;
    private TextSwitch[] data;



    public TextSwitchAdapter(Context context, int layoutResourceId, TextSwitch[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;

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

        return convertView;

    }
//    @NonNull
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View row = convertView;
//        TextSwitchHolder holder = null;
//
//
//        if (row == null) {
//            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
//            row = inflater.inflate(layoutResourceId, parent, false);
//
//            holder = new TextSwitchHolder();
//            holder.label = (TextView)row.findViewById(R.id.light_room_text);
//            holder.aSwitch = (Switch)row.findViewById(R.id.light_switch);
//
//            row.setTag(holder);
//        } else {
//            holder = (TextSwitchHolder) row.getTag();
//        }
//
//
//        TextSwitch textSwitch = data[position];
//        holder.label.setText(textSwitch.getLabel());
//        holder.aSwitch.setChecked(textSwitch.getisChecked());
//
//        return row;
//
//    }
//
//
//    static class TextSwitchHolder {
//        TextView label;
//        Switch aSwitch;
//    }


}
