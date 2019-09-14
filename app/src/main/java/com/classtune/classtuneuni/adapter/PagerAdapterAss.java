package com.classtune.classtuneuni.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.classtune.classtuneuni.fragment.AssignmentPagerFragment;
import com.classtune.classtuneuni.fragment.StudentReportFragment;
import com.classtune.classtuneuni.fragment.TakeAttendanceFragment;
import com.classtune.classtuneuni.fragment.TeacherClassReportFragment;


public class PagerAdapterAss extends FragmentStatePagerAdapter {

    public PagerAdapterAss(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
//        if (position == 0)
//        {
//            fragment = new EventsFragment();
//        }
//        else
            fragment = new AssignmentPagerFragment();

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
//        if (position == 0)
//        {
//            title = "Event";
//        }
//        else
        if (position == 0)
        {
            title = "Roll Call";
        }
        else if (position == 1)
        {
            title = "Class Report";
        }
        else if (position == 2)
        {
            title = "Student Report";
        }
        return title;
    }
}
