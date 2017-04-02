package com.example.jack.hal.pattern;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.jack.hal.R;
import com.example.jack.hal.services.AsynTaskPatternState;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Jack on 2017-04-02.
 */

public class ExpandableAdaptor extends BaseExpandableListAdapter {


    private List<Integer> patternIds;
    private Map<Integer, List<Item>> patternCollections;
    private Context context;

    public ExpandableAdaptor(Context context, List<Integer> patternIds, Map<Integer, List<Item>> patternCollections) {
        this.context = context;
        this.patternCollections = patternCollections;
        this.patternIds = patternIds;


    }

    @Override
    public int getGroupCount() {
        return patternIds.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int count =  patternCollections.get(patternIds.get(groupPosition)).size();
        return count;
    }

    @Override
    public Object getGroup(int groupPosition) {

        int patternId = patternIds.get(groupPosition);
        return patternCollections.get(patternId).get(0);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return patternCollections.get(patternIds.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        Item item = (Item) getGroup(groupPosition);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.listtitle_pattern_list, null);
        }

        TextView textView = (TextView)convertView.findViewById(R.id.pattern_listview_title);
        Switch mSwitch = (Switch)convertView.findViewById(R.id.pattern_listview_switch);

        textView.setText(item.getHeader().getLabel());

        boolean isChecked = item.getHeader().getStatus() == PatternState.ACTIVE ? true : false;

        mSwitch.setChecked(isChecked);

        final String patternID = Integer.toString(item.getPatternId());

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    new AsynTaskPatternState().execute(patternID, "on");
                } else {
                    new AsynTaskPatternState().execute(patternID, "off");
                }
            }
        });

        return convertView;



    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Item item = (Item) getGroup(groupPosition);


        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.childs_pattern_list, parent, false);

//            LayoutInflater inflater = context.getLayoutInflater();
//            convertView = infalInflater.inflate(R.layout.childs_pattern_list, null);
        }

        TextView description = (TextView)convertView.findViewById(R.id.pattern_listview_dscp);
        ImageButton deleteButton = (ImageButton) convertView.findViewById(R.id.pattern_listview_btn);


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String patternId = Integer.toString(item.getPatternId());
                new AsynTaskPatternState().execute(patternId, "delete");

                removeFromPatternlist(patternIds, item.getPatternId());

                patternCollections.remove(item.getPatternId());

                Log.d("Pattern DELETE", patternId.toString());
                Log.d("Pattern DELETE", patternCollections.toString());

                notifyDataSetChanged();

            }
        });

        description.setText(item.getDescription());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    private void removeFromPatternlist(List list, int element) {

        for (Iterator<Integer> iter = list.listIterator(); iter.hasNext(); ) {
            int a = iter.next();
            if (a == element) {
                iter.remove();
            }
        }
    }
}
