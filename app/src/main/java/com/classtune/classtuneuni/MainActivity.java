package com.classtune.classtuneuni;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.view.Gravity;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class MainActivity extends AppCompatActivity
        {

    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_login_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        tabLayout = (TabLayout) findViewById(R.id.tabs);
//
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.pigeon_hole_tab_).setTag("pigeonhole"));
////        if (userPermission.isUserTasksSubmitTask())
////            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.submission_tray_tab).setTag("submission_tray"));
////        else
////            hideItem();
////        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.cm_box).setTag("cm_box"));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.pigeon_hole_tab_).setTag("reading_package"));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.pigeon_hole_tab_).setTag("notice"));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.pigeon_hole_tab_).setTag("course_calendar"));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.pigeon_hole_tab_).setTag("events"));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.pigeon_hole_tab_).setTag("research_icon"));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.pigeon_hole_tab_).setTag("profile"));
////        tabLayout.addTab(tabLayout.newTab().setTag("tab"));
//
//
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            if (tab != null) tab.setCustomView(R.layout.view_home_tab);
//        }
//        tabLayout.setSupportActionBarelectedTabIndicatorColor(Color.parseColor("#74af27"));


    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//

        return super.onOptionsItemSelected(item);
    }


}
