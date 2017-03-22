package com.example.jack.hal;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {
    private Toolbar toolbar;

    protected abstract String getToolBarTitle();
    protected abstract int getLayoutResource();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        configureToolbar();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void configureToolbar() {
//        if(android.os.Debug.isDebuggerConnected()) {
//            android.os.Debug.waitForDebugger();
//
//        }

        toolbar = (Toolbar)findViewById(R.id.toolbar_base);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            setTitle(getToolBarTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }


}
