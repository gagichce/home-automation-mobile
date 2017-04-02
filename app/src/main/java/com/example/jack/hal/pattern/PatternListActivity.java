package com.example.jack.hal.pattern;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.jack.hal.BaseActivity;
import com.example.jack.hal.R;

import java.util.HashMap;
import java.util.List;

public class PatternListActivity extends BaseActivity {

    private ExpandableListView expandableListView;
    private ExpandableAdaptor expandableAdaptor;

    HashMap<TextSwitch, List<Item>> expandableListDetail;


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


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();

        return super.onOptionsItemSelected(item);
    }

}
