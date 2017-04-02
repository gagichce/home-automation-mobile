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

    Map<Integer, List<Item>> expandableListDetail;
    List<Integer> patternIds;


    @Override
    protected String getToolBarTitle() {
        return "Patterns";
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_pattern_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        expandableListView = (ExpandableListView) findViewById(R.id.pattern_listview);


        patternIds = new ArrayList<>(Global.patterns.keySet());
        expandableListDetail = ExpandableDataPump.getData();

        expandableAdaptor = new ExpandableAdaptor(this, patternIds, expandableListDetail);

        expandableListView.setAdapter(expandableAdaptor);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                Log.d("ExpandGroup", "Position " + Integer.toString(groupPosition));

                if (parent.isGroupExpanded(groupPosition)) {
                    parent.collapseGroup(groupPosition);

                } else {
                    parent.expandGroup(groupPosition);

                }

                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();

        return super.onOptionsItemSelected(item);
    }

}
