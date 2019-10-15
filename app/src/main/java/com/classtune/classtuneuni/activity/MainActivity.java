package com.classtune.classtuneuni.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.classtune.classtuneuni.fragment.ChatDetailsFragment;
import com.classtune.classtuneuni.fragment.EnrollStartFragment;
import com.classtune.classtuneuni.fragment.ExamDetailsFragment;
import com.classtune.classtuneuni.fragment.ExamListFragment;
import com.classtune.classtuneuni.fragment.HomeFragment;
import com.classtune.classtuneuni.fragment.MorePageFragment;
import com.classtune.classtuneuni.fragment.NotificationListFragment;
import com.classtune.classtuneuni.fragment.ResourceViewFragment;
import com.classtune.classtuneuni.fragment.ResourseFragment;
import com.classtune.classtuneuni.fragment.StudentAttendanceFragment;
import com.classtune.classtuneuni.fragment.StudentCourseListFragment;
import com.classtune.classtuneuni.fragment.SubjectResultFragment;
import com.classtune.classtuneuni.fragment.TeacherNoticeDetails;
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
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getName();
    public BottomBar bottomBar;
    TabLayout tabLayout;
    Fragment fragment;
    public TabHost mTabHost;
    public RelativeLayout tabRl;
    UIHelper uiHelper;
    private boolean enter = false;
    public GifImageView gifImageView;
    private View bg;


    public static String GlobalCourseId = "";
    public static String GlobalOfferedCourseSectionId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppSharedPreference.setUsingFirstTime(false);
        AppSharedPreference.setFirstTimeLogin(false);
//        gifImageView = findViewById(R.id.gif);
//        bg = findViewById(R.id.bg);
//        bg.setVisibility(View.GONE);
//        gifImageView.setVisibility(View.GONE);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setLogo(R.drawable.toolbar_icon);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.left_arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        View view = toolbar.getChildAt(1);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform actions
                //Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_LONG).show();
//                HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
//                if(homeFragment!=null && !homeFragment.isVisible()) {

                int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
                if (backStackCount > 0) {
                    while (backStackCount > 0) {
                        getSupportFragmentManager().popBackStack();
                        backStackCount--;
                    }
                }
                fragment = new HomeFragment();
                loadFragment(fragment, "homeFragment", true);
                bottomBar.selectTabAtPosition(0);
                // }
            }
        });


//                "notice" => 1,//st
//                "exam_schedule" => 2,
//                "exam_report" => 3/st,
//                "final_exam_report" => 4/st,
//                "assignment" => 5,/st
//                "assignment_submited" => 6,
//                "assignment_mark" => 7,/st
//                "resource_material" => 8,//st
//                "feedback_submitted" => 9,
//                "attendance" => 10,//st
//                "requested_for_resubmission" => 11,
//                "resubmission_accepted" => 12,
//                "interest" => 13,
//                "invitation" => 14


//        fab_main = findViewById(R.id.fab);
//        fab1_mail = findViewById(R.id.fab1);
//        fab2_share =findViewById(R.id.fab2);
//        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
//        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
//        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
//        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);
//
//        textview_mail = findViewById(R.id.textview_mail);
//        textview_share = findViewById(R.id.textview_share);
//
//        fab_main.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (isOpen) {
//
//                    textview_mail.setVisibility(View.INVISIBLE);
//                    textview_share.setVisibility(View.INVISIBLE);
//                    fab2_share.startAnimation(fab_close);
//                    fab1_mail.startAnimation(fab_close);
//                    fab_main.startAnimation(fab_anticlock);
//                    fab2_share.setClickable(false);
//                    fab1_mail.setClickable(false);
//                    isOpen = false;
//                } else {
//                    textview_mail.setVisibility(View.VISIBLE);
//                    textview_share.setVisibility(View.VISIBLE);
//                    fab2_share.startAnimation(fab_open);
//                    fab1_mail.startAnimation(fab_open);
//                    fab_main.startAnimation(fab_clock);
//                    fab2_share.setClickable(true);
//                    fab1_mail.setClickable(true);
//                    isOpen = true;
//                }
//
//            }
//        });
//
//
//        fab2_share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Toast.makeText(getApplicationContext(), "Share", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        fab1_mail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Email", Toast.LENGTH_SHORT).show();
//
//            }
//        });


        uiHelper = new UIHelper(this);
//        if (AppSharedPreference.getUserType().equals("3")) {
//            callStudentSectionListApi();
//        } else {
//
//            callOfferedSectionListApi();
//        }

        mTabHost = (TabHost) findViewById(android.R.id.tabhost);

        mTabHost.setup();


        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int pos = mTabHost.getCurrentTab();
                if (AppSharedPreference.getUserType().equals("3")) {
                    StCourseSection ss = AppSharedPreference.getStUserTab(tabId, pos);
                    GlobalCourseId = ss.getCourseCode();
                    GlobalOfferedCourseSectionId = ss.getCourseOfferSectionId();


                } else {
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

        if (AppSharedPreference.getUserStatus().equals("0"))
            bottomBar.setDefaultTabPosition(1);

        final Bundle extras = getIntent().getExtras();

        Bundle bundle;
        String orderId = "";
//        gotoPigeonholeFragment();
        if (extras != null && !extras.isEmpty()) {

            String type = "";
            String id = "";
            if (extras.getString("target_type") != null)
                type = extras.getString("target_type");
            if (extras.getString("target_id") != null)
                id = extras.getString("target_id");

            if (type.equals("1")) {
                fragment = new TeacherNoticeDetails();
                bundle = new Bundle();
                bundle.putString("noticeId", id);
                gotoFragment(fragment, "teacherNoticeDetails", bundle);
            } else if (type.equals("2")) {
                // gotoCMSubmitTaskDetailsFragment(id);
                Fragment fragment = new ExamListFragment();
                bundle = new Bundle();
                bundle.putString("id", id);
                fragment.setArguments(bundle);
                gotoFragment(fragment, "examListFragment", bundle);
            } else if (type.equals("3")) {
                //gotoReadingPackageFragmentNotify(id);
                Fragment fragment = new ExamDetailsFragment();
                bundle = new Bundle();
                bundle.putString("id", id);
                fragment.setArguments(bundle);
                gotoFragment(fragment, "examDetailsFragment", bundle);
            } else if (type.equals("4")) {
                //gotoReadingPackageFragmentNotify(id);
                Fragment fragment = new SubjectResultFragment();
                bundle = new Bundle();
                bundle.putString("id", id);
                fragment.setArguments(bundle);
                gotoFragment(fragment, "subjectResultFragment", bundle);
            } else if (type.equals("8")) {
                //gotoReadingPackageFragmentNotify(id);
                Fragment fragment = new ResourceViewFragment();
                bundle = new Bundle();
                bundle.putString("id", id);
                fragment.setArguments(bundle);
                gotoFragment(fragment, "resourceViewFragment", bundle);
            } else if (type.equals("5") || type.equals("7") || type.equals("12")) {
                //gotoReadingPackageFragmentNotify(id);
                Fragment fragment = new AssignmentDetailsFragment();
                bundle = new Bundle();
                bundle.putString("assignmentId", id);
                fragment.setArguments(bundle);
                gotoFragment(fragment, "assignmentDetailsFragment", bundle);
            } else if (type.equals("10")) {
                //gotoReadingPackageFragmentNotify(id);
                Fragment fragment = new StudentAttendanceFragment();
                bundle = new Bundle();
                bundle.putString("id", id);
                fragment.setArguments(bundle);
                gotoFragment(fragment, "studentAttendanceFragment", bundle);
            }
        }

//        fragment = new HomeFragment();
//        loadFragment(fragment);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                if (extras != null)
                    return;
                StudentCourseListFragment studentCourseListFragment = (StudentCourseListFragment) getSupportFragmentManager().findFragmentByTag("studentCourseListFragment");

                // messageView.setText(TabMessage.get(tabId, false));
                if (tabId == R.id.home) {
                    if (AppSharedPreference.getUserStatus().equals("0"))
                        return;
//                    HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
//                    if(homeFragment != null && homeFragment.isVisible())
//                        return;
//                    else {
//                        showHomeFragment();
//                    }
                    //Toast.makeText(getApplicationContext(), TabMessage.get(tabId, true), Toast.LENGTH_LONG).show();


                    int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
                    if (backStackCount > 1) {
                        while (backStackCount > 1) {
                            getSupportFragmentManager().popBackStack();
                            backStackCount--;
                        }
                    }

                    if (enter) {
                        fragment = new HomeFragment();
                        loadFragment(fragment, "homeFragment", true);
                    } else {
                        if (AppSharedPreference.getUserType().equals("3")) {
                            callStudentSectionListApi(true);
                        } else {

                            callOfferedSectionListApi();
                        }
                    }

                } else if (tabId == R.id.course) {

                    int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
                    if (backStackCount > 1) {
                        while (backStackCount > 1) {
                            getSupportFragmentManager().popBackStack();
                            backStackCount--;
                        }
                    }

                    fragment = new StudentCourseListFragment();
                    loadFragment(fragment, "studentCourseListFragment", false);

                } else if (tabId == R.id.news) {
                    if (AppSharedPreference.getUserStatus().equals("0"))
                        return;
                    int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
                    if (backStackCount > 1) {
                        while (backStackCount > 1) {
                            getSupportFragmentManager().popBackStack();
                            backStackCount--;
                        }
                    }
                    fragment = new ResourseFragment();
                    loadFragment(fragment, "resourseFragment", false);
                } else if (tabId == R.id.result) {
                    if (AppSharedPreference.getUserStatus().equals("0"))
                        return;

                    int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
                    if (backStackCount > 1) {
                        while (backStackCount > 1) {
                            getSupportFragmentManager().popBackStack();
                            backStackCount--;
                        }
                    }
                    fragment = new SubjectResultFragment();
                    loadFragment(fragment, "subjectResultFragment", false);
                } else if (tabId == R.id.forum) {

//                    int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
//                    if(backStackCount>0)
//                    {
//                        while (backStackCount>0) {
//                            getSupportFragmentManager().popBackStack();
//                            backStackCount--;
//                        }
//                    }
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragment = new MorePageFragment();
                    loadFragment(fragment, "morePageFragment", false);
                    //  }
                }
            }
            //  }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                boolean home = AppSharedPreference.getUsingHomeFirstTime();
                String st = TabMessage.get(tabId, true);
                ChatDetailsFragment chatDetailsFragment = (ChatDetailsFragment) getSupportFragmentManager().findFragmentByTag("chatDetailsFragment");
                //  Toast.makeText(getApplicationContext(), TabMessage.get(tabId, true), Toast.LENGTH_LONG).show();
                MorePageFragment morePageFragment = (MorePageFragment) getSupportFragmentManager().findFragmentByTag("morePageFragment");
                if (chatDetailsFragment != null && chatDetailsFragment.isVisible()) {
                    getSupportFragmentManager().popBackStack();
                    if (st.contains("home")) {
                        fragment = new HomeFragment();
                        loadFragment(fragment, "homeFragment", true);
                    } else if (st.contains("course")) {
                        fragment = new StudentCourseListFragment();
                        loadFragment(fragment, "studentCourseListFragment", true);
                    } else if (st.contains("resource")) {

                        fragment = new ResourseFragment();
                        loadFragment(fragment, "resourseFragment", true);
                    } else if (st.contains("result")) {
                        fragment = new SubjectResultFragment();
                        loadFragment(fragment, "subjectResultFragment", true);
                    } else if (st.contains("more")) {
                        fragment = new MorePageFragment();
                        loadFragment(fragment, "morePageFragment", true);

                    }
                }
                //AppSharedPreference.getUsingHomeFirstTime() &&
                else if (st.contains("home")) {
                    HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
                    if (homeFragment != null && !homeFragment.isVisible()) {

                        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
                        if (backStackCount > 0) {
                            while (backStackCount > 0) {
                                getSupportFragmentManager().popBackStack();
                                backStackCount--;
                            }
                        }
                        fragment = new HomeFragment();
                        loadFragment(fragment, "homeFragment", true);
                    }

                } else if (st.contains("more")) {
                    int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
                    if (backStackCount > 0) {
                        while (backStackCount > 0) {
                            getSupportFragmentManager().popBackStack();
                            backStackCount--;
                        }
                    }
                    fragment = new MorePageFragment();
                    loadFragment(fragment, "morePageFragment", false);
                } else if (morePageFragment != null && morePageFragment.isVisible())
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
        if (morePageFragment != null && morePageFragment.isVisible()) {
//            fragment = new HomeFragment();
//            loadFragment(fragment, "homeFragment", true);
            bottomBar.selectTabAtPosition(0);
        }
        if (backStackCount >= 1) {
            getSupportFragmentManager().popBackStack();
            int backs = getSupportFragmentManager().getBackStackEntryCount();
            if (backStackCount == 1) {
                if (AppSharedPreference.getUserStatus().equals("0"))
                    finish();
                else
                    bottomBar.selectTabAtPosition(0);
            }
//            if(backs == 0){
//                //getSupportFragmentManager().popBackStack();
//                HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
//                if(homeFragment!=null && homeFragment.isVisible()) {
//                    finish();
//                }
//                else {
//                    fragment = new HomeFragment();
//                    loadFragment(fragment, "homeFragment", true);
//                    bottomBar.selectTabAtPosition(0);
//                }
//            }
        } else if (backStackCount == 0) {
            //getSupportFragmentManager().popBackStack();
            HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
            if (homeFragment != null && homeFragment.isVisible()) {
                AppSharedPreference.setHomeData("");
                finish();
            } else {
                fragment = new HomeFragment();
                loadFragment(fragment, "homeFragment", true);
                bottomBar.selectTabAtPosition(0);
            }
        }
//        else if(backStackCount<=0)
//            finish();
//        else
//            super.onBackPressed();

    }

    public MenuItem item, notification;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        item = menu.findItem(R.id.chat);
        notification = menu.findItem(R.id.notification);
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
                if (enrollStartFragment != null && enrollStartFragment.isVisible())
                    return true;
                //  Toast.makeText(getApplicationContext(), "chat", Toast.LENGTH_LONG).show();
                fragment = new ChatDetailsFragment();
                loadFragment(fragment, "chatDetailsFragment", false);
                return true;
            case R.id.notification:
                //Toast.makeText(getApplicationContext(), "notification", Toast.LENGTH_LONG).show();
                fragment = new NotificationListFragment();
                loadFragment(fragment, "notificationListFragment", true);
            default:
                return super.onOptionsItemSelected(item);
        }
//

    }


    private void loadFragment(Fragment fragment, String tag, boolean backstack) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        if (!backstack)
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
        // gifImageView.setVisibility(View.VISIBLE);
        //uiHelper.loadGif(true);

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
                        //uiHelper.dismissLoadingDialog();
                        //gifImageView.setVisibility(View.GONE);

                        AssignmentSectionResponse assignmentSectionResponse = value.body();
                        if (assignmentSectionResponse != null && assignmentSectionResponse.getStatus().getCode() != null && assignmentSectionResponse.getStatus().getCode() == 200) {
                            addSection(assignmentSectionResponse.getData().getSections());

                        } else
                            Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                        uiHelper.dismissLoadingDialog();
                        //gifImageView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        uiHelper.dismissLoadingDialog();
                        //gifImageView.setVisibility(View.GONE);
                    }
                });


    }

    public void callStudentSectionListApi(final boolean loader) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getApplicationContext(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        if (loader)
            uiHelper.showLoadingDialog("Please wait...");
//        bg.setVisibility(View.VISIBLE);
//        gifImageView.setVisibility(View.VISIBLE);

        //uiHelper.loadGif(true);


        RetrofitApiClient.getApiInterfaceWithId().getStSectionList(AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<StSectionListResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<StSectionListResponse> value) {
                        // uiHelper.dismissLoadingDialog();

                        StSectionListResponse stSectionListResponse = value.body();
                        if (stSectionListResponse != null && stSectionListResponse.getStatus().getCode() == 200) {
                            stAddSection(stSectionListResponse.getData().getCourseSection());
                            enter = true;
                            fragment = new HomeFragment();
                            loadFragment(fragment, "homeFragment", true);

                        } else if (stSectionListResponse != null && stSectionListResponse.getStatus().getCode() == 204) {

                            fragment = new StudentCourseListFragment();
                            loadFragment(fragment, "studentCourseListFragment", true);
                        } else {
                            //  Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        //Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                        if(loader)
                        uiHelper.dismissLoadingDialog();
//                        gifImageView.setVisibility(View.GONE);
//                        bg.setVisibility(View.GONE);
                        //uiHelper.loadGif(false);
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        if(loader)
                        uiHelper.dismissLoadingDialog();
//                        gifImageView.setVisibility(View.GONE);
//                        bg.setVisibility(View.GONE);
                        //uiHelper.loadGif(false);
                    }
                });

    }

    private String ST_TAB_STRING = "";
    private String ST_COURSE_STRING = "";

    private void addSection(List<AssignmentSection> sections) {
        for (int i = 0; i < sections.size(); i++) {
            setupTab(new TextView(this), sections.get(i));
//            ST_TAB_STRING = ST_TAB_STRING + sections.get(i).getCourseCode() + " " + sections.get(i).getCourseId() + " " + sections.get(i).getSectionName() + " " + sections.get(i).getOfferedSectionId();
//            if(i < (sections.size() -1))
//                ST_TAB_STRING = ST_TAB_STRING + "|";
            AppSharedPreference.setUserTab(sections.get(i), i);
        }


        AppSharedPreference.setUserTab(sections.get(0), 0);
        tabRl.setVisibility(View.GONE);
        if (sections.size() > 0)
            GlobalOfferedCourseSectionId = sections.get(0).getOfferedSectionId();


    }

    private void stAddSection(List<StCourseSection> sections) {
        mTabHost.getTabWidget().removeAllViews();
        ST_TAB_STRING = "";
        ST_COURSE_STRING = "";
        for (int i = 0; i < sections.size(); i++) {
            if (sections.get(i).getIsFinished().equals("0")) {
                setupStTab(new TextView(this), sections.get(i));
                ST_TAB_STRING = ST_TAB_STRING + sections.get(i).getCourseCode() + "/" + sections.get(i).getInstructor() + "/" + sections.get(i).getCourseOfferSectionId() + "/" + sections.get(i).getCourseName();
                if (i < (sections.size() - 1))
                    ST_TAB_STRING = ST_TAB_STRING + "|";
                AppSharedPreference.setStUserTab(sections.get(i), i);
            }
            ST_COURSE_STRING = ST_COURSE_STRING + sections.get(i).getCourseOfferSectionId() + "/" + sections.get(i).getCourseName();
            if (i < (sections.size() - 1))
                ST_COURSE_STRING = ST_COURSE_STRING + "|";
        }
        AppSharedPreference.setStTabString(ST_TAB_STRING);
        AppSharedPreference.setStAllCourseString(ST_COURSE_STRING);
        AppSharedPreference.setStUserTab(sections.get(0), 0);
        tabRl.setVisibility(View.GONE);
        if (sections.size() > 0)
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
        view.setTag(stCourseSection.getCourseOfferSectionId());
        return view;
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void gotoFragment(Fragment fragment, String tag, Bundle bundle) {
        // load fragment
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        // transaction.addToBackStack(null);
        transaction.commit();
    }
}
