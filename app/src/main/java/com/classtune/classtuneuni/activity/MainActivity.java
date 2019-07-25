package com.classtune.classtuneuni.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.classtune.classtuneuni.fragment.AssignmentFragment;
import com.classtune.classtuneuni.fragment.AttendanceSummaryFragment;
import com.classtune.classtuneuni.fragment.ClassScheduleFragment;
import com.classtune.classtuneuni.fragment.CombinedResultFragment;
import com.classtune.classtuneuni.fragment.ExamListFragment;
import com.classtune.classtuneuni.fragment.HomeFragment;
import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.fragment.NoticeListFragment;
import com.classtune.classtuneuni.fragment.StudentsSummaryFragment;
import com.classtune.classtuneuni.fragment.TakeAttendanceFragment;
import com.classtune.classtuneuni.fragment.TeacherExamListFragment;
import com.classtune.classtuneuni.fragment.TeacherResultEntryFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getName();
    BottomBar bottomBar;
    TabLayout tabLayout;
    Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        bottomBar = (BottomBar) findViewById(R.id.bottomBar);

//        fragment = new HomeFragment();
//        loadFragment(fragment);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
//                Fragment fragment;

                // messageView.setText(TabMessage.get(tabId, false));
                if (tabId == R.id.home) {
//                    HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
//                    if(homeFragment != null && homeFragment.isVisible())
//                        return;
//                    else {
//                        showHomeFragment();
//                    }
                    //Toast.makeText(getApplicationContext(), TabMessage.get(tabId, true), Toast.LENGTH_LONG).show();

//                    fragment = new HomeFragment();
//                    loadFragment(fragment, "homeFragment");

//                    fragment = new AttendanceSummaryFragment();
//                    loadFragment(fragment, "attendanceSummaryFragment");

//                    fragment = new TakeAttendanceFragment();
//                    loadFragment(fragment, "takeAttendanceFragment");
//
//                    fragment = new TeacherResultEntryFragment();
//                    loadFragment(fragment, "teacherResultEntryFragment");


//                    fragment = new StudentsSummaryFragment();
//                    loadFragment(fragment, "studentsSummaryFragment");
                    fragment = new NoticeListFragment();
                    loadFragment(fragment, "noticeListFragment");
                    //return true;
                } else if (tabId == R.id.assistant) {
                    //Toast.makeText(getApplicationContext(), TabMessage.get(tabId, true), Toast.LENGTH_LONG).show();
//                    SafeInternetAssitantFragment safeInternetAssitantFragment = (SafeInternetAssitantFragment) getSupportFragmentManager().findFragmentByTag("safeInternetAssitantFragment");
//                    if(safeInternetAssitantFragment != null && safeInternetAssitantFragment.isVisible())
//                        return;
//                    else {
//                        showAssistant();
//                    }

                    fragment = new AssignmentFragment();
                    loadFragment(fragment, "assignmentFragment");

                } else if (tabId == R.id.news) {
                    //Toast.makeText(getApplicationContext(), TabMessage.get(tabId, true), Toast.LENGTH_LONG).show();
//                    NewsFragment newsFragment = (NewsFragment) getSupportFragmentManager().findFragmentByTag("newsFragment");
//                    if(newsFragment != null && newsFragment.isVisible())
//                        return;
//                    else {
//                        showNews();
//                    }
                    fragment = new CombinedResultFragment();
                    loadFragment(fragment, "combinedResultFragment");
                } else if (tabId == R.id.quiz) {
                    //Toast.makeText(getApplicationContext(), TabMessage.get(tabId, true), Toast.LENGTH_LONG).show();
//                    QuizFragment quizFragment = (QuizFragment) getSupportFragmentManager().findFragmentByTag("quizFragment");
//                    if(quizFragment != null && quizFragment.isVisible())
//                        return;
//                    else {
//                        showQuiz();
//                    }
//                    fragment = new ExamListFragment();
//                    loadFragment(fragment, "examListFragment");
                    fragment = new TeacherExamListFragment();
                    loadFragment(fragment, "teacherExamListFragment");
                } else if (tabId == R.id.forum) {
                    //Toast.makeText(getApplicationContext(), TabMessage.get(tabId, true), Toast.LENGTH_LONG).show();
//                    ForumFragment forumFragment = (ForumFragment) getSupportFragmentManager().findFragmentByTag("forumFragment");
//                    if(forumFragment != null && forumFragment.isVisible())
//                        return;
//                    else {
//                        showForum();
//                    }

                    fragment = new ClassScheduleFragment();
                    loadFragment(fragment, "classScheduleFragment");
                }
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                //Toast.makeText(getApplicationContext(), TabMessage.get(tabId, true), Toast.LENGTH_LONG).show();
            }
        });


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


    private void loadFragment(Fragment fragment, String tag) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        //transaction.addToBackStack(null);
        transaction.commit();
    }


}
