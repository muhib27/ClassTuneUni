package com.classtune.classtuneuni.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.assignment.AssignmentSection;
import com.classtune.classtuneuni.assignment.AssignmentSectionResponse;
import com.classtune.classtuneuni.fragment.AssignmentDetailsFragment;
import com.classtune.classtuneuni.fragment.AssignmentFragment;
import com.classtune.classtuneuni.fragment.ChatDetailsFragment;
import com.classtune.classtuneuni.fragment.CombinedResultFragment;
import com.classtune.classtuneuni.fragment.EnrollStartFragment;
import com.classtune.classtuneuni.fragment.HomeFragment;
import com.classtune.classtuneuni.fragment.MorePageFragment;
import com.classtune.classtuneuni.fragment.NoticeListFragment;
import com.classtune.classtuneuni.fragment.ResourseFragment;
import com.classtune.classtuneuni.response.StCourseSection;
import com.classtune.classtuneuni.response.StSectionListResponse;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.TabMessage;
import com.classtune.classtuneuni.utils.UIHelper;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getName();
    BottomBar bottomBar;
    TabLayout tabLayout;
    Fragment fragment;
    public TabHost mTabHost;
    public RelativeLayout tabRl;
    UIHelper uiHelper;

    public static String GlobalCourseId = "";
    public static String GlobalOfferedCourseSectionId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppSharedPreference.setUsingFirstTime(false);
        AppSharedPreference.setFirstTimeLogin(false);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setLogo(R.drawable.toolbar_icon);

        uiHelper = new UIHelper(this);
        if (AppSharedPreference.getUserType().equals("3")) {
            callStudentSectionListApi();
        } else {

            callOfferedSectionListApi();
        }

        mTabHost = (TabHost)findViewById(android.R.id.tabhost);

        mTabHost.setup();


        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
              int pos = mTabHost.getCurrentTab();
              if(AppSharedPreference.getUserType().equals("3"))
              {
                  StCourseSection ss = AppSharedPreference.getStUserTab(tabId, pos);
                  GlobalCourseId = ss.getCourseCode();
                  GlobalOfferedCourseSectionId = ss.getCourseOfferSectionId();


              }
              else {
                  AssignmentSection ss = AppSharedPreference.getUserTab(tabId, pos);
                  GlobalCourseId = ss.getCourseId();
                  GlobalOfferedCourseSectionId = ss.getOfferedSectionId();
              }
 //               Toast.makeText(getApplicationContext(), "" + tabId, Toast.LENGTH_LONG).show();
//                if(TAB_1_TAG.equals(tabId)) {
//                    //destroy earth
//                }
//                if(TAB_2_TAG.equals(tabId)) {
//                    //destroy mars
//                }
            }
        });

        tabRl = findViewById(R.id.tab);
        // tabRl.setVisibility(View.GONE);

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);

//        fragment = new HomeFragment();
//        loadFragment(fragment);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
//                Fragment fragment;
                EnrollStartFragment enrollStartFragment = (EnrollStartFragment) getSupportFragmentManager().findFragmentByTag("enrollStartFragment");
                if(enrollStartFragment != null && enrollStartFragment.isVisible())
                    return;
                // messageView.setText(TabMessage.get(tabId, false));
                if (tabId == R.id.home) {
//                    HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
//                    if(homeFragment != null && homeFragment.isVisible())
//                        return;
//                    else {
//                        showHomeFragment();
//                    }
                    //Toast.makeText(getApplicationContext(), TabMessage.get(tabId, true), Toast.LENGTH_LONG).show();



                    int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
                    if(backStackCount>1)
                    {
                        while (backStackCount>1) {
                            getSupportFragmentManager().popBackStack();
                            backStackCount--;
                        }
                    }

                    fragment = new HomeFragment();
                    loadFragment(fragment, "homeFragment", true);

                } else if (tabId == R.id.assistant) {

                    int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
                    if(backStackCount>1)
                    {
                        while (backStackCount>1) {
                            getSupportFragmentManager().popBackStack();
                            backStackCount--;
                        }
                    }

                    fragment = new AssignmentFragment();
                    loadFragment(fragment, "assignmentFragment", false);

                } else if (tabId == R.id.news) {

                    int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
                    if(backStackCount>1)
                    {
                        while (backStackCount>1) {
                            getSupportFragmentManager().popBackStack();
                            backStackCount--;
                        }
                    }
                    fragment = new ResourseFragment();
                    loadFragment(fragment, "resourseFragment" , true);
                } else if (tabId == R.id.quiz) {

                    int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
                    if(backStackCount>1)
                    {
                        while (backStackCount>1) {
                            getSupportFragmentManager().popBackStack();
                            backStackCount--;
                        }
                    }
                    fragment = new NoticeListFragment();
                    loadFragment(fragment, "noticeListFragment", true);
                } else if (tabId == R.id.forum) {

                    int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
                    if(backStackCount>1)
                    {
                        while (backStackCount>1) {
                            getSupportFragmentManager().popBackStack();
                            backStackCount--;
                        }
                    }

                    fragment = new MorePageFragment();
                    loadFragment(fragment, "morePageFragment", false);
                    //  }
                }
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                String st = TabMessage.get(tabId, true);
                ChatDetailsFragment chatDetailsFragment = (ChatDetailsFragment) getSupportFragmentManager().findFragmentByTag("chatDetailsFragment");
                //  Toast.makeText(getApplicationContext(), TabMessage.get(tabId, true), Toast.LENGTH_LONG).show();
                MorePageFragment morePageFragment = (MorePageFragment) getSupportFragmentManager().findFragmentByTag("morePageFragment");
                if(chatDetailsFragment !=null && chatDetailsFragment.isVisible())
                {
                    getSupportFragmentManager().popBackStack();
                    if(st.contains("home"))
                    {
                        fragment = new HomeFragment();
                        loadFragment(fragment, "homeFragment", true);
                    }
                    else  if(st.contains("assignment"))
                    {
                        fragment = new AssignmentFragment();
                        loadFragment(fragment, "assignmentFragment", true);
                    }
                    else  if(st.contains("resource"))
                    {

                        fragment = new ResourseFragment();
                        loadFragment(fragment, "resourseFragment", true);
                    }
                    else  if(st.contains("notice"))
                    {
                        fragment = new NoticeListFragment();
                        loadFragment(fragment, "noticeListFragment", true);
                    }
                    else  if(st.contains("more"))
                    {
                        fragment = new MorePageFragment();
                        loadFragment(fragment, "morePageFragment", true);

                    }
                }
                else if (st.contains("more")) {
                    int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
                    if(backStackCount>0)
                    {
                        while (backStackCount>0) {
                            getSupportFragmentManager().popBackStack();
                            backStackCount--;
                        }
                    }
                    fragment = new MorePageFragment();
                    loadFragment(fragment, "morePageFragment", false);
                }

                else if(morePageFragment!=null && morePageFragment.isVisible())
                    return;

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
      //  super.onBackPressed();

        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();

        MorePageFragment morePageFragment = (MorePageFragment) getSupportFragmentManager().findFragmentByTag("morePageFragment");
//        if (morePageFragment != null && morePageFragment.isVisible()) {
//            fragment = new HomeFragment();
//            loadFragment(fragment, "homeFragment", true);
//            bottomBar.selectTabAtPosition(0);
//        }
        if(backStackCount>1)
        {
            getSupportFragmentManager().popBackStack();
        }
        else if(backStackCount == 1){
            getSupportFragmentManager().popBackStack();
            fragment = new HomeFragment();
            loadFragment(fragment, "homeFragment", true);

            bottomBar.selectTabAtPosition(0);
        }
        else if(backStackCount<=0)
            finish();
//        else
//            super.onBackPressed();

    }
    MenuItem item;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        item = menu.findItem(R.id.chat);
//        item.getActionView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //menu.performIdentifierAction(item.getItemId(), 0);
//                Toast.makeText(getApplicationContext(), "chat", Toast.LENGTH_LONG).show();
//            }
//        });
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.chat:

                EnrollStartFragment enrollStartFragment = (EnrollStartFragment) getSupportFragmentManager().findFragmentByTag("enrollStartFragment");
                if(enrollStartFragment != null && enrollStartFragment.isVisible())
                    return true;
              //  Toast.makeText(getApplicationContext(), "chat", Toast.LENGTH_LONG).show();
                fragment = new ChatDetailsFragment();
                loadFragment(fragment, "chatDetailsFragment", false);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
//

    }


    private void loadFragment(Fragment fragment, String tag, boolean backstack) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
            if(!backstack)
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void loadEnroll(Fragment fragment, String tag, boolean backstack) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.containerLl, fragment, tag);
//        if(!backstack)
//            transaction.addToBackStack(null);
        transaction.commit();
    }


    private void setupTab(final View view, AssignmentSection assignmentSection) {
        View tabview = createTabView(mTabHost.getContext(), assignmentSection.getCourseCode(), assignmentSection.getSectionName(), assignmentSection);
        TabHost.TabSpec setContent = mTabHost.newTabSpec(assignmentSection.getCourseCode()).setIndicator(tabview).setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return view;
            }
        });
        mTabHost.addTab(setContent);
    }

    private static View createTabView(final Context context, final String text, final String text1, AssignmentSection assignmentSection) {
        View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
        TextView tv = (TextView) view.findViewById(R.id.tabsText);
        tv.setText(text);
        tv.setTag(assignmentSection.getCourseId());
        TextView tvsmall = (TextView) view.findViewById(R.id.tabsTextSmall);
        tvsmall.setText(text1);
        tvsmall.setTag(assignmentSection.getOfferedSectionId());
        view.setTag(assignmentSection.getCourseCode());
        return view;
    }


    private void callOfferedSectionListApi() {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getApplicationContext(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getOfferedSectionList(AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<AssignmentSectionResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<AssignmentSectionResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        AssignmentSectionResponse assignmentSectionResponse = value.body();
                        if (assignmentSectionResponse!= null && assignmentSectionResponse.getStatus().getCode()!=null && assignmentSectionResponse.getStatus().getCode() == 200) {
                            addSection(assignmentSectionResponse.getData().getSections());

                        } else
                            Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        uiHelper.dismissLoadingDialog();
                    }
                });


    }

    public void callStudentSectionListApi() {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getApplicationContext(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        RetrofitApiClient.getApiInterfaceWithId().getStSectionList(AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<StSectionListResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<StSectionListResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        StSectionListResponse stSectionListResponse = value.body();
                        if (stSectionListResponse!= null && stSectionListResponse.getStatus().getCode() == 200) {
                            stAddSection(stSectionListResponse.getData().getCourseSection());

                        } else if(stSectionListResponse!= null && stSectionListResponse.getStatus().getCode() == 204)
                        {

                            fragment = new EnrollStartFragment();
                            loadFragment(fragment, "enrollStartFragment", true);
                        }
                        else
                            Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        uiHelper.dismissLoadingDialog();
                    }
                });


    }

    private void addSection(List<AssignmentSection> sections) {
        for (int i = 0; i < sections.size(); i++) {
            setupTab(new TextView(this), sections.get(i));
            AppSharedPreference.setUserTab(sections.get(i), i);
        }

        AppSharedPreference.setUserTab(sections.get(0), 0);
        tabRl.setVisibility(View.GONE);
        if(sections.size()>0)
            GlobalOfferedCourseSectionId = sections.get(0).getOfferedSectionId();


    }

    private void stAddSection(List<StCourseSection> sections) {
        for (int i = 0; i < sections.size(); i++) {
            setupStTab(new TextView(this), sections.get(i));
            AppSharedPreference.setStUserTab(sections.get(i), i);
        }

        AppSharedPreference.setStUserTab(sections.get(0), 0);
        tabRl.setVisibility(View.GONE);
        if(sections.size()>0)
        GlobalOfferedCourseSectionId = sections.get(0).getCourseOfferSectionId();


    }

    private void setupStTab(final View view, StCourseSection stCourseSection) {
        View tabview = createStTabView(mTabHost.getContext(), stCourseSection.getCourseCode(), stCourseSection.getInstructor(), stCourseSection);
        TabHost.TabSpec setContent = mTabHost.newTabSpec(stCourseSection.getCourseCode()).setIndicator(tabview).setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return view;
            }
        });
        mTabHost.addTab(setContent);
    }

    private static View createStTabView(final Context context, final String text, final String text1, StCourseSection stCourseSection) {
        View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
        TextView tv = (TextView) view.findViewById(R.id.tabsText);
        tv.setText(text);
        tv.setTag(stCourseSection.getCourseOfferSectionId());
        TextView tvsmall = (TextView) view.findViewById(R.id.tabsTextSmall);
        tvsmall.setText(text1);
        tvsmall.setTag(stCourseSection.getCourseOfferSectionId());
        view.setTag(stCourseSection.getCourseCode());
        return view;
    }

}
