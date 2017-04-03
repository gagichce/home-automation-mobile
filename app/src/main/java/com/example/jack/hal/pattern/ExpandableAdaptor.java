package com.example.jack.hal.pattern;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.jack.hal.Global;
import com.example.jack.hal.R;
import com.example.jack.hal.services.AsynTaskPatternState;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.view.FrameMetrics.ANIMATION_DURATION;

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
        final int patternId = item.getPatternId();

        mSwitch.setChecked(isChecked);

        final String patternID = Integer.toString(item.getPatternId());

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    new AsynTaskPatternState().execute(patternID, "on");
                    Global.updatePatterns(patternId, 1);
                } else {
                    new AsynTaskPatternState().execute(patternID, "off");
                    Global.updatePatterns(patternId, 0);

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
                final String patternId = Integer.toString(item.getPatternId());
                new AsynTaskPatternState().execute(patternId, "delete");
                removeFromPatternlist(patternIds, item.getPatternId());
                patternCollections.remove(item.getPatternId());
                Log.d("Remove pattern", "Id: " + patternId);
                Global.patterns.remove(Integer.valueOf(patternId));
                Log.d("Global patterns", "After removing: " + Global.patterns.toString());


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

    private void collapse(final View v, Animation.AnimationListener al) {
        final int initialHeight = v.getMeasuredHeight();

        Animation anim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                }
                else {
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        if (al!=null) {
            anim.setAnimationListener(al);
        }
        anim.setDuration(ANIMATION_DURATION);
        v.startAnimation(anim);
    }
}
