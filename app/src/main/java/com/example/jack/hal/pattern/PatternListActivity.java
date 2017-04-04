package com.example.jack.hal.pattern;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.jack.hal.BaseActivity;
import com.example.jack.hal.Global;
import com.example.jack.hal.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatternListActivity extends BaseActivity {

    private ExpandableListView expandableListView;
    private ExpandableAdaptor expandableAdaptor;
    private ExpandableDataPump expandableDataPump;

    Map<Integer, List<Item>> expandableListDetail;
    List<Integer> patternIds;


    @Override
    protected String getToolBarTitle() {
        String deviceName = getIntent().getStringExtra("deviceName");
        return "Patterns - " + deviceName ;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_pattern_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int deviceId = getIntent().getIntExtra("deviceId", -1);

        expandableDataPump = new ExpandableDataPump(deviceId);

        expandableListView = (ExpandableListView) findViewById(R.id.pattern_listview);

        patternIds = expandableDataPump.getPatternIds();
        expandableListDetail = expandableDataPump.getData();

        expandableAdaptor = new ExpandableAdaptor(this, patternIds, expandableListDetail);

        expandableListView.setAdapter(expandableAdaptor);
//
//        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//
//                Log.d("ExpandGroup", "Position " + Integer.toString(groupPosition));
//
//                if (parent.isGroupExpanded(groupPosition)) {
//                    parent.collapseGroup(groupPosition);
//
//                } else {
//                    parent.expandGroup(groupPosition);
//
//                }
//
//                return false;
//            }
//        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();

        return super.onOptionsItemSelected(item);
    }

}
