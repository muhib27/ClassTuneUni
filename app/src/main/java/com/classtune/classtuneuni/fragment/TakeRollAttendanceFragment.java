//package com.classtune.classtuneuni.fragment;
//
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TabHost;
//import android.widget.TabWidget;
//import android.widget.TextView;
//
//import com.classtune.classtuneuni.R;
//import com.classtune.classtuneuni.adapter.TakeAttendanceAdapter;
//import com.classtune.classtuneuni.model.STAttendanceModel;
//import com.classtune.classtuneuni.utils.MyFragmentTabHost;
//import com.classtune.classtuneuni.utils.VerticalSpaceItemDecoration;
//
//import java.util.ArrayList;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class TakeRollAttendanceFragment extends Fragment implements TakeAttendanceAdapter.ItemListener{
//    TabHost mTabHost, mTabHost1;
//    RecyclerView recyclerView;
//    private ArrayList<STAttendanceModel> studentList;
//    LinearLayoutManager linearLayoutManager;
//
//
//    public TakeRollAttendanceFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_take_attendance, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        mTabHost = (TabHost) view.findViewById(android.R.id.tabhost);
//
//        mTabHost.setup();
//        setupTab(new TextView(getContext()), "All", "Summer 2019");
//        setupTab(new TextView(getContext()), "Tab 2", "Summer 2018");
//        setupTab(new TextView(getContext()), "Tab 3","Summer 2019");
//        setupTab(new TextView(getContext()), "Tab 1","Summer 2019");
//        setupTab(new TextView(getContext()), "Tab 2","Summer 2019");
//        setupTab(new TextView(getContext()), "Tab 3","Summer 2019");
//
////        mTabHost1 = (TabHost) view.findViewById(R.id.tabHost);
//
////        mTabHost1.setup();
////        setupTab1(new TextView(getContext()), "All", "Summer 2019");
////        setupTab1(new TextView(getContext()), "Tab 2", "Summer 2018");
////        setupTab1(new TextView(getContext()), "Tab 3","Summer 2019");
//
//        recyclerView = view.findViewById(R.id.recyclerView);
//
//        studentList = new ArrayList<>();
//
//        studentList.add(new STAttendanceModel( "1", "Md. Rahim", "1"));
//        studentList.add(new STAttendanceModel( "2", "Md. Rahim", "1"));
//        studentList.add(new STAttendanceModel( "3", "Md. Rahim", "1"));
//        studentList.add(new STAttendanceModel( "4", "Md. Rahim", "1"));
//        studentList.add(new STAttendanceModel( "1", "Md. Rahim", "1"));
//        studentList.add(new STAttendanceModel( "2", "Md. Rahim", "1"));
//        studentList.add(new STAttendanceModel( "3", "Md. Rahim", "1"));
//        studentList.add(new STAttendanceModel( "4", "Md. Rahim", "1"));
//        studentList.add(new STAttendanceModel( "1", "Md. Rahim", "1"));
//        studentList.add(new STAttendanceModel( "2", "Md. Rahim", "1"));
//        studentList.add(new STAttendanceModel( "3", "Md. Rahim", "1"));
//        studentList.add(new STAttendanceModel( "4", "Md. Rahim", "1"));
//        studentList.add(new STAttendanceModel( "1", "Md. Rahim", "1"));
//        studentList.add(new STAttendanceModel( "2", "Md. Rahim", "1"));
//        studentList.add(new STAttendanceModel( "3", "Md. Rahim", "1"));
//        studentList.add(new STAttendanceModel( "4", "Md. Rahim", "1"));
//
//
//        TakeAttendanceAdapter takeAttendanceAdapter = new TakeAttendanceAdapter(getActivity(), studentList, this);
//        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(takeAttendanceAdapter);
//    }
//
//    private void setupTab(final View view, final String tag, String tag1) {
//        View tabview = createTabView(mTabHost.getContext(), tag, tag1);
//        TabHost.TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(new TabHost.TabContentFactory() {
//            public View createTabContent(String tag) {
//                return view;
//            }
//        });
//        mTabHost.addTab(setContent);
//    }
//
//    private static View createTabView(final Context context, final String text, final String text1) {
//        View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
//        TextView tv = (TextView) view.findViewById(R.id.tabsText);
//        tv.setText(text);
//        TextView tvsmall = (TextView) view.findViewById(R.id.tabsTextSmall);
//        tvsmall.setText(text1);
//        return view;
//    }
//
//    private void setupTab1(final View view, final String tag, String tag1) {
//        View tabview = createTabView1(mTabHost1.getContext(), tag, tag1);
//        TabHost.TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(new TabHost.TabContentFactory() {
//            public View createTabContent(String tag) {
//                return view;
//            }
//        });
//        mTabHost.addTab(setContent);
//    }
//
//    private static View createTabView1(final Context context, final String text, final String text1) {
//        View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg1, null);
//        TextView tv = (TextView) view.findViewById(R.id.tabsText);
//        tv.setText(text);
//        return view;
//    }
//
//    @Override
//    public void onItemClick(STAttendanceModel item, int pos) {
//
//    }
//
//
//
//
//
//}
