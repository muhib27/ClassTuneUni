package com.classtune.classtuneuni.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;


import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.adapter.TakeAttendanceAdapter;
import com.classtune.classtuneuni.model.STAttendanceModel;
import com.classtune.classtuneuni.utils.MyFragmentTabHost;
import com.classtune.classtuneuni.utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TakeAttendanceFragment extends Fragment implements TakeAttendanceAdapter.ItemListener{
    TabHost mTabHost, mTabHost1;
    RecyclerView recyclerView;
    private ArrayList<STAttendanceModel> studentList;
    LinearLayoutManager linearLayoutManager;
//    private MyFragmentTabHost mTabHostAttendanceTeacher;

    public TakeAttendanceFragment() {
        // Required empty public constructor
    }
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_take_attendance, container, false);
//        mTabHostAttendanceTeacher = rootView
//                .findViewById(R.id.tabhost_attendance_teacher);
//
//        initialiseTabHost(savedInstanceState);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        mTabHostAttendanceTeacher = view
//                .findViewById(R.id.tabhost_attendance_teacher);
//
//        initialiseTabHost(savedInstanceState);
        mTabHost = (TabHost) view.findViewById(android.R.id.tabhost);

        mTabHost.setup();
        setupTab(new TextView(getContext()), "All", "Summer 2019");
        setupTab(new TextView(getContext()), "Tab 2", "Summer 2018");
        setupTab(new TextView(getContext()), "Tab 3","Summer 2019");
        setupTab(new TextView(getContext()), "Tab 1","Summer 2019");
        setupTab(new TextView(getContext()), "Tab 2","Summer 2019");
        setupTab(new TextView(getContext()), "Tab 3","Summer 2019");

//        mTabHost1 = (TabHost) view.findViewById(R.id.tabHost);

//        mTabHost1.setup();
//        setupTab1(new TextView(getContext()), "All", "Summer 2019");
//        setupTab1(new TextView(getContext()), "Tab 2", "Summer 2018");
//        setupTab1(new TextView(getContext()), "Tab 3","Summer 2019");

        recyclerView = view.findViewById(R.id.recyclerView);

        studentList = new ArrayList<>();

        studentList.add(new STAttendanceModel( "1", "Md. Rahim", "1"));
        studentList.add(new STAttendanceModel( "2", "Md. Rahim", "1"));
        studentList.add(new STAttendanceModel( "3", "Md. Rahim", "1"));
        studentList.add(new STAttendanceModel( "4", "Md. Rahim", "1"));
        studentList.add(new STAttendanceModel( "1", "Md. Rahim", "1"));
        studentList.add(new STAttendanceModel( "2", "Md. Rahim", "1"));
        studentList.add(new STAttendanceModel( "3", "Md. Rahim", "1"));
        studentList.add(new STAttendanceModel( "4", "Md. Rahim", "1"));
        studentList.add(new STAttendanceModel( "1", "Md. Rahim", "1"));
        studentList.add(new STAttendanceModel( "2", "Md. Rahim", "1"));
        studentList.add(new STAttendanceModel( "3", "Md. Rahim", "1"));
        studentList.add(new STAttendanceModel( "4", "Md. Rahim", "1"));
        studentList.add(new STAttendanceModel( "1", "Md. Rahim", "1"));
        studentList.add(new STAttendanceModel( "2", "Md. Rahim", "1"));
        studentList.add(new STAttendanceModel( "3", "Md. Rahim", "1"));
        studentList.add(new STAttendanceModel( "4", "Md. Rahim", "1"));


        TakeAttendanceAdapter takeAttendanceAdapter = new TakeAttendanceAdapter(getActivity(), studentList, this);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(takeAttendanceAdapter);
    }

    private void setupTab(final View view, final String tag, String tag1) {
        View tabview = createTabView(mTabHost.getContext(), tag, tag1);
        TabHost.TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return view;
            }
        });
        mTabHost.addTab(setContent);
    }

    private static View createTabView(final Context context, final String text, final String text1) {
        View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
        TextView tv = (TextView) view.findViewById(R.id.tabsText);
        tv.setText(text);
        TextView tvsmall = (TextView) view.findViewById(R.id.tabsTextSmall);
        tvsmall.setText(text1);
        return view;
    }

    private void setupTab1(final View view, final String tag, String tag1) {
        View tabview = createTabView1(mTabHost1.getContext(), tag, tag1);
        TabHost.TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return view;
            }
        });
        mTabHost.addTab(setContent);
    }

    private static View createTabView1(final Context context, final String text, final String text1) {
        View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg1, null);
        TextView tv = (TextView) view.findViewById(R.id.tabsText);
        tv.setText(text);
        return view;
    }

    @Override
    public void onItemClick(STAttendanceModel item, int pos) {

    }


//    private void initialiseTabHost(Bundle args) {
//
//        TabInfo tabInfo = null;
//        mTabHostAttendanceTeacher.setup(getActivity(),
//                getChildFragmentManager(),
//                R.id.realtabcontent_attendance_teacher);
//        MyFragmentTabHost.TabSpec spec = mTabHostAttendanceTeacher
//                .newTabSpec("Tab");
//        spec.setIndicator(getIndicatorView("Tab"));
//        addTab(this.mTabHostAttendanceTeacher, spec, (tabInfo = new TabInfo(
//                "Tab",
//                TakeRollAttendanceFragment.class, args)));
//
//        spec = mTabHostAttendanceTeacher
//                .newTabSpec("Tab2");
//        spec.setIndicator(getIndicatorView(getString(R.string.title_roll_call_tab)));
//        addTab(this.mTabHostAttendanceTeacher, spec, (tabInfo = new TabInfo(
//                "Tab2",
//                ExamListFragment.class, args)));
//
//        Log.e("SURRENT_TAB", "is: " + mTabHostAttendanceTeacher.getCurrentTab());
//
//        // Default to first tab
//        // this.onTabChanged(AppConstant.TAB_ROLLCALL_TEACHER);
//        //
//        // mTabHostAttendanceTeacher.setOnTabChangedListener(this);
//
//        mTabHostAttendanceTeacher.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
//
//            @Override
//            public void onTabChanged(String s) {
//
//                DisplayMetrics displaymetrics = new DisplayMetrics();
//                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//                int width = displaymetrics.widthPixels;
//
//                final TabWidget tabWidget = mTabHostAttendanceTeacher.getTabWidget();
//                final int screenWidth = width;
//                final int leftX = tabWidget.getChildAt(mTabHostAttendanceTeacher.getCurrentTab()).getLeft();
//                int newX = 0;
//
//                newX = leftX + (tabWidget.getChildAt(mTabHostAttendanceTeacher.getCurrentTab()).getWidth() / 2) - (screenWidth / 2);
//                if (newX < 0) {
//                    newX = 0;
//                }
//                //horizontalScrollView.smoothScrollTo(newX, 0);
//
//            }
//        });
//
//
//    }
//
//    /**
//     *
//     */
//    private class TabInfo {
//        private String tag;
//        private Class<?> clss;
//        private Bundle args;
//        private Fragment fragment;
//
//        TabInfo(String tag, Class<?> clazz, Bundle args) {
//            this.tag = tag;
//            this.clss = clazz;
//            this.args = args;
//        }
//    }
//
//    /**
//     *
//     *
//     */
//    class TabFactory implements TabHost.TabContentFactory {
//
//        private final Context mContext;
//
//        /**
//         * @param context
//         */
//        public TabFactory(Context context) {
//            mContext = context;
//        }
//
//        /**
//         * (non-Javadoc)
//         *
//         * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
//         */
//        public View createTabContent(String tag) {
//            View v = new View(mContext);
//            v.setMinimumWidth(0);
//            v.setMinimumHeight(0);
//            return v;
//        }
//
//    }
//
//    /**
//     * @param activity
//     * @param tabHost
//     * @param tabSpec
//     * @param clss
//     * @param args
//     */
//    private void addTab(MyFragmentTabHost tabHost,
//                        MyFragmentTabHost.TabSpec tabSpec, TabInfo tabInfo) {
//        // Attach a Tab view factory to the spec
//        tabSpec.setContent(new TabFactory(getActivity()));
//        String tag = tabSpec.getTag();
//
//        // Check to see if we already have a fragment for this tab, probably
//        // from a previously saved state. If so, deactivate it, because our
//        // initial state is that a tab isn't shown.
//        tabInfo.fragment = getChildFragmentManager().findFragmentByTag(tag);
//        if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
//            FragmentTransaction ft = getChildFragmentManager()
//                    .beginTransaction();
//            ft.detach(tabInfo.fragment);
//            ft.commit();
//            getChildFragmentManager().executePendingTransactions();
//        }
//
//        tabHost.addTab(tabSpec, tabInfo.clss, null);
//    }
//
//    private View getIndicatorView(String text) {
//        View tabIndicator = LayoutInflater.from(getActivity()).inflate(
//                R.layout.tab_indicator_attendance,
//                this.mTabHostAttendanceTeacher.getTabWidget(), false);
//        TextView title = (TextView) tabIndicator.findViewById(R.id.title);
//        title.setText(text);
//        return tabIndicator;
//    }

}
