package com.example.jack.hal;


import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.example.jack.hal.services.AsynTaskDevices;
import com.example.jack.hal.services.AsynTaskPatterns;
import com.example.jack.hal.services.HttpAsynTask;
import com.example.jack.hal.services.SocketService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class MainActivity extends BaseActivity implements TabRoomFragment.OnFragmentInteractionListener, TabSettingsFragment.OnFragmentInteractionListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Global global;


    @Override
    protected String getToolBarTitle() {
        return "HAL";
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        global = new Global();

        new AsynTaskDevices().execute();
        new AsynTaskPatterns().execute();


        startService(new Intent(MainActivity.this, SocketService.class));
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new TabRoomFragment(), "Rooms");
        viewPagerAdapter.addFragment(new TabSettingsFragment(), "Settings");
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String tabTitle) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(tabTitle);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
