package com.classtune.classtuneuni.fragment;


import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.PagerAdapter;
import com.classtune.classtuneuni.assignment.AssignmentSection;
import com.classtune.classtuneuni.response.StCourseSection;
import com.classtune.classtuneuni.utils.AppSharedPreference;

import java.util.Objects;

import static com.classtune.classtuneuni.activity.MainActivity.GlobalCourseId;
import static com.classtune.classtuneuni.activity.MainActivity.GlobalOfferedCourseSectionId;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherAttendanceFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;

    public TeacherAttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_attendance, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) Objects.requireNonNull(getActivity())).tabRl.setVisibility(View.VISIBLE);



        viewPager = (ViewPager) view.findViewById(R.id.pager);
        pagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        View root = tabLayout.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.ash_b5));
            drawable.setSize(1, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }

        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.appColor));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                if(tab.getPosition()== 0) {
//                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
////                    tabLayout.setBackgroundColor(getResources().getColor(R.color.blue_color));
//                    tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#727272"));
//                }
//                else
                if(tab.getPosition()== 0) {
                    //tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#DFE1E2"));
//                    tabLayout.setBackgroundColor(getResources().getColor(R.color.blue_color));
                    //tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#727272"));

                    //tabLayout.setBackgroundColor(getResources().getColor(R.color.appColor));
                    tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.appColor));
                    tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
                }
                else if(tab.getPosition()== 1) {

                   // tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#1093b2"));
                    tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.appColor));
                    tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
                }
                else if(tab.getPosition()== 2) {

                    //tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#1093b2"));
                    tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.appColor));
                    tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
