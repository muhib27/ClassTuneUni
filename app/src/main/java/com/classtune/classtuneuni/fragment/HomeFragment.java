package com.classtune.classtuneuni.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.AssignmentAdapter;
import com.classtune.classtuneuni.adapter.HomeAssignmentAdapter;
import com.classtune.classtuneuni.adapter.HomeNoticeAdapter;
import com.classtune.classtuneuni.adapter.HomeResourceAdapter;
import com.classtune.classtuneuni.assignment.Assignment;
import com.classtune.classtuneuni.class_schedule.Routine;
import com.classtune.classtuneuni.home.StHomeRespons;
import com.classtune.classtuneuni.model.ExamInfoModel;
import com.classtune.classtuneuni.notice.Notices;
import com.classtune.classtuneuni.resource.Resource;
import com.classtune.classtuneuni.response.Notice;
import com.classtune.classtuneuni.response.StSectionListResponse;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.AppUtility;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;
import com.classtune.classtuneuni.utils.VerticalSpaceItemDecoration;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Response;


public class HomeFragment extends Fragment implements View.OnClickListener
{
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private TextView topDate, topTitle, chapter, subCode, author;
    private TextView notice1Date, notice1Title, notice2Date, notice2Title;
    private TextView nextSubject, nextClassTime, classInstructor, dayText, room;
    private TextView examDay, examDate, examMonthYear, examName, examTime, marks, examSubject;
    private View examDotView;


    private LinearLayout notice1Ll, notice2Ll;

    private ImageView imageView, courseImg, assignments;

    RecyclerView rvNotice, rvAssignmnet, rvResource;
    private List<Assignment> assignmentList;
    LinearLayoutManager noticelinearLayoutManager, assignmentLayoutManager;
    UIHelper uiHelper;
    AssignmentAdapter assignmentAdapter;

    HomeNoticeAdapter homeNoticeAdapter;

    HomeResourceAdapter homeResourceAdapter;
    HomeAssignmentAdapter homeAssignmentAdapter;

    GridLayoutManager manager;
    private static List<Resource> resourceList;
    private ImageView classSchedudle, resources, notices;
    CardView nextClass, upcomingExam;
    View bg;

    RelativeLayout latestResource;
    GifImageView gifImageView;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        uiHelper = new UIHelper(getActivity());
//        callStudentHome();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_fragment_new, container, false);

      //  AppSharedPreference.setUsingHomeFirstTime(true);
       // loaderDisble();
//        bg.setVisibility(View.VISIBLE);
        setHasOptionsMenu(true);

        if(((MainActivity)getActivity()).tabRl.getVisibility() == View.VISIBLE)
            ((MainActivity)getActivity()).tabRl.setVisibility(View.GONE);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);


        uiHelper = new UIHelper(getActivity());

        //  uiHelper.showLoadingDialog("Pddddd...");


        mSwipeRefreshLayout = view.findViewById(R.id.swipeToRefresh);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.appColor), getResources().getColor(R.color.black), Color.BLUE);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Log.e(getClass().getSimpleName(), "refresh");
                callStudentHome(true);

            }
        });

        resourceList = new ArrayList<>();
        // bg = view.findViewById(R.id.bg);
        latestResource = view.findViewById(R.id.latestResource);
        latestResource.setOnClickListener(this);
        classSchedudle = view.findViewById(R.id.classSchedudle);
        classSchedudle.setOnClickListener(this);
        resources = view.findViewById(R.id.resourceList);
        resources.setOnClickListener(this);
        nextClass = view.findViewById(R.id.nextClass);
        nextClass.setOnClickListener(this);
        upcomingExam = view.findViewById(R.id.upcomingExam);
        upcomingExam.setOnClickListener(this);
        notices = view.findViewById(R.id.notices);
        notices.setOnClickListener(this);

        assignments = view.findViewById(R.id.assignments);
        assignments.setOnClickListener(this);

        topDate = view.findViewById(R.id.topDate);
        topTitle = view.findViewById(R.id.topTitle);
//        chapter = view.findViewById(R.id.chapter);
        subCode = view.findViewById(R.id.subCode);
        author = view.findViewById(R.id.authorName);

        notice1Date = view.findViewById(R.id.notice1Date);
        notice2Date = view.findViewById(R.id.notice2Date);
        notice1Title = view.findViewById(R.id.notice1Title);
        notice2Title = view.findViewById(R.id.notice2Title);

        notice1Ll = view.findViewById(R.id.notice1Ll);
        notice1Ll.setOnClickListener(this);
        notice2Ll = view.findViewById(R.id.notice2Ll);
        notice2Ll.setOnClickListener(this);


        nextSubject = view.findViewById(R.id.next_subject);
        nextClassTime = view.findViewById(R.id.next_time);
        classInstructor = view.findViewById(R.id.next_instructor);
        dayText = view.findViewById(R.id.dayText);
        room = view.findViewById(R.id.room);

        examDay = view.findViewById(R.id.upcomingExamDay);
        examDate = view.findViewById(R.id.upcomingExamDate);
        examMonthYear = view.findViewById(R.id.upcomingExamMY);
        examName = view.findViewById(R.id.examName);
        marks = view.findViewById(R.id.marks);
        examTime = view.findViewById(R.id.examTime);
        examDotView = view.findViewById(R.id.examDotView);
        examSubject = view.findViewById(R.id.examSubject);

        imageView = view.findViewById(R.id.newsPoster);
        courseImg = view.findViewById(R.id.courseImg);





        rvNotice = view.findViewById(R.id.rvNotice);
        rvAssignmnet = view.findViewById(R.id.rvAssignment);
        rvResource = view.findViewById(R.id.rvResources);

        resourceList = new ArrayList<>();
        List<Notice> noticeList;



        homeResourceAdapter = new HomeResourceAdapter(getActivity());
        rvResource.setAdapter(homeResourceAdapter);
        rvResource.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        rvResource.setLayoutManager(manager);
        rvResource.setItemAnimator(new DefaultItemAnimator());

//        noticelinearLayoutManager = new LinearLayoutManager(getActivity());
//        homeNoticeAdapter = new HomeNoticeAdapter(getActivity());
//        //rvNotice.setAdapter(homeNoticeAdapter);
//       // rvNotice.setLayoutManager(noticelinearLayoutManager);
//
//        rvNotice.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
//        rvNotice.setLayoutManager(noticelinearLayoutManager);
//        rvNotice.setItemAnimator(new DefaultItemAnimator());
//        rvNotice.setAdapter(homeNoticeAdapter);



        homeNoticeAdapter = new HomeNoticeAdapter(getActivity());
        noticelinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvNotice.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        rvNotice.setLayoutManager(noticelinearLayoutManager);
        rvNotice.setItemAnimator(new DefaultItemAnimator());
        rvNotice.setAdapter(homeNoticeAdapter);


        assignmentLayoutManager = new LinearLayoutManager(getActivity());
        homeAssignmentAdapter = new HomeAssignmentAdapter(getActivity());
        rvAssignmnet.setLayoutManager(assignmentLayoutManager);
        rvAssignmnet.setAdapter(homeAssignmentAdapter);

        String tabs = AppSharedPreference.getStTabString();
//        if (AppSharedPreference.getUserType().equals("3")) {
//            callStudentSectionListApi();
//        } else {
//
//            //callOfferedSectionListApi();
//        }
        if(!tabs.isEmpty()) {
//            ((MainActivity)getActivity()).bottomBar.selectTabAtPosition(0);
            //((MainActivity)getActivity()).item.setEnabled(true);
            //  bg.setVisibility(View.VISIBLE);
            if (!AppSharedPreference.getHomeData().isEmpty()) {
                Gson gson = new Gson();
                StHomeRespons stHomeRespons = gson.fromJson(AppSharedPreference.getHomeData(), StHomeRespons.class);
                homeResourceAdapter.clear();
                homeNoticeAdapter.clear();
                homeAssignmentAdapter.clear();
                resourceList = stHomeRespons.getResources();
                homeResourceAdapter.addAllData(stHomeRespons.getResources());
                homeNoticeAdapter.addAllData(stHomeRespons.getNotices(), stHomeRespons.getCurrentTime());
                homeAssignmentAdapter.addAllData(stHomeRespons.getAssignments(),  stHomeRespons.getCurrentTime());
                populateLatest(stHomeRespons.getResourceSingle());
                populateLatestNotice(stHomeRespons.getNotice());
                populateNextClass(stHomeRespons.getNextClass(), stHomeRespons.getWeekday());
                populateNextExam(stHomeRespons.getExam(), stHomeRespons.getWeekday());
            } else {
                callStudentHome(false);
            }

        }
        else {
            ((MainActivity)getActivity()).bottomBar.selectTabAtPosition(1);
            // ((MainActivity)getActivity()).item.setEnabled(false);
            fragment = new StudentCourseListFragment();
            loadFragment(fragment, "studentCourseListFragment", true);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void callStudentSectionListApi() {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
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
                            //stAddSection(stSectionListResponse.getData().getCourseSection());
                            callStudentHome(false);
                        } else if(stSectionListResponse!= null && stSectionListResponse.getStatus().getCode() == 204)
                        {

                            fragment = new StudentCourseListFragment();
                            loadFragment(fragment, "studentCourseListFragment", true);
                        }
                        else {
                            //  Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        uiHelper.dismissLoadingDialog();
                    }
                });


    }


    private void callStudentHome(boolean b) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }

//        if(!uiHelper.isDialogActive())
        if (!b)
            //loaderEnable();
        uiHelper.showLoadingDialog("Please wait...");

        RetrofitApiClient.getApiInterfaceWithId().getStHome(AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<StHomeRespons>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<StHomeRespons> value) {
                        //uiHelper.dismissLoadingDialog();

                        StHomeRespons stHomeRespons = value.body();
                        if (stHomeRespons.getStatus().getCode() == 200) {
                            //  stAddSection(stSectionListResponse.getData().getCourseSection());
//                            stCourseAssessmentList = stProfileRsponse.getData().getCourseAssessment();
//                            stProfileInfoAdapter.addAllData(stCourseAssessmentList);
                            homeResourceAdapter.clear();
                            homeNoticeAdapter.clear();
                            homeAssignmentAdapter.clear();
                            resourceList = stHomeRespons.getResources();
                            homeResourceAdapter.addAllData(stHomeRespons.getResources());
                            homeNoticeAdapter.addAllData(stHomeRespons.getNotices(), stHomeRespons.getCurrentTime());
                            homeAssignmentAdapter.addAllData(stHomeRespons.getAssignments(),  stHomeRespons.getCurrentTime());
                            populateLatest(stHomeRespons.getResourceSingle());
                            populateLatestNotice(stHomeRespons.getNotice());
                            populateNextClass(stHomeRespons.getNextClass(), stHomeRespons.getWeekday());
                            populateNextExam(stHomeRespons.getExam(), stHomeRespons.getWeekday());

                            Gson gson = new Gson();
                            String json = gson.toJson(stHomeRespons);
                            AppSharedPreference.setHomeData(json);


                        } else {
                           // Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        //Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        uiHelper.dismissLoadingDialog();
                        //((MainActivity)getActivity()).gifImageView.setVisibility(View.GONE);
                        //loaderDisble();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
//                        Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//
//                            @Override
//                            public void run() {
//
//                                uiHelper.dismissLoadingDialog();
//                            }
//                        }, 5000);
                        mSwipeRefreshLayout.setRefreshing(false);
                        uiHelper.dismissLoadingDialog();
                        //((MainActivity)getActivity()).gifImageView.setVisibility(View.GONE);
                        //loaderDisble();
                    }
                });


    }

    private void populateNextExam(ExamInfoModel exam, String weekday) {
        //  bg.setVisibility(View.GONE);
        if(exam.getExamName()!=null)
        examName.setText(exam.getExamName());
        if(exam.getCourseName()!=null)
            examSubject.setText(exam.getCourseName());
        if(exam.getExamMark()!=null) {
            String mark = "";
            if(exam.getExamMark().contains("."))
            {
                String[] marks = exam.getExamMark().split("\\.");
                if(marks.length>0)
                mark = marks[0];
            }
            else
                mark = exam.getExamMark();
            marks.setText("" + mark);
        }

        if(exam.getDayName()!=null)
            examDay.setText(exam.getDayName());

        if(exam.getExamDate() !=null && exam.getExamDate().contains("-"))
        {
            String[] parts = exam.getExamDate().split("-");
            if(parts.length>=2)
                examDate.setText(parts[2]);
        }
        if(exam.getExamDate() !=null && exam.getExamDate().contains("-"))
            examMonthYear.setText(AppUtility.getDateString(exam.getExamDate(), AppUtility.DATE_FORMAT_APP_M_Y, AppUtility.DATE_FORMAT_SERVER));

        if(exam.getStartTime()!=null && !exam.getStartTime().isEmpty()) {
            examTime.setVisibility(View.VISIBLE);
            examDotView.setVisibility(View.VISIBLE);
            examTime.setText(exam.getStartTime());
        }
        else {
            examTime.setVisibility(View.GONE);
            examDotView.setVisibility(View.GONE);
        }

    }

    private void populateNextClass(Routine nextClass, String weekday) {
        if(getActivity()!=null) {
            if (nextClass.getThumbnail() != null && !nextClass.getThumbnail().isEmpty())
                // if(resourceSi)
                Glide.with(getActivity())
                        .load(nextClass.getThumbnail())
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.demo_img)
                                .fitCenter())
                        .into(courseImg);
        }

        if(nextClass.getName() !=null)
            nextSubject.setText(nextClass.getName());

        if(nextClass.getRoom() !=null)
            room.setText("" + nextClass.getRoom());
//
        if(nextClass.getDay() !=null) {
            if(nextClass.getDay().equals(weekday))
            dayText.setText("Today");
            else
                dayText.setText(nextClass.getDay());
        }
        nextClassTime.setText(AppUtility.getDuration(nextClass.getEndTime().substring(0, 5), nextClass.getStartTime().substring(0, 5)));
//
        if(nextClass.getInstructor() !=null)
            classInstructor.setText(nextClass.getInstructor());
    }

    private String n1 = "", n2="";

    private void populateLatestNotice(List<Notices> notice) {
        if(notice.get(0).getCreatedAt()!= null && notice.get(0).getCreatedAt().contains(" ")) {
            n1 = notice.get(0).getId();
            String[] parts = notice.get(0).getCreatedAt().split(" ");
            notice1Date.setText(AppUtility.getDateString(parts[0], AppUtility.DATE_FORMAT_APP_, AppUtility.DATE_FORMAT_SERVER));
        }
        if(notice.get(1).getCreatedAt()!= null && notice.get(1).getCreatedAt().contains(" ")) {
            n2 = notice.get(1).getId();
            String[] parts = notice.get(1).getCreatedAt().split(" ");
            notice2Date.setText(AppUtility.getDateString(parts[0], AppUtility.DATE_FORMAT_APP_, AppUtility.DATE_FORMAT_SERVER));
        }
        if(notice.get(0).getTitle() !=null)
            notice1Title.setText(notice.get(0).getTitle());
        if(notice.get(1).getTitle() !=null)
            notice2Title.setText(notice.get(1).getTitle());
    }

    private Resource Topresource;
    private void populateLatest(Resource resourceSingle) {
        Topresource = resourceSingle;
        if(getActivity()!=null && resourceSingle.getThumbnail() !=null && !resourceSingle.getThumbnail().isEmpty())
       // if(resourceSi)
        Glide.with(getActivity())
                .load(resourceSingle.getThumbnail())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.news_poster)
                        .fitCenter())
                .into(imageView);

        if(resourceSingle.getTitle() !=null)
        topTitle.setText(resourceSingle.getTitle());

//        if(resourceSingle.getChapterTitle() !=null)
//        chapter.setText(resourceSingle.getChapterTitle());

        if(resourceSingle.getCourseName() !=null)
        subCode.setText(resourceSingle.getCourseName());

        if(resourceSingle.getInstructor() !=null)
        author.setText(resourceSingle.getInstructor());

        if(resourceSingle.getCreatedAt()!= null && resourceSingle.getCreatedAt().contains(" ")) {
            String[] parts = resourceSingle.getCreatedAt().split(" ");
            topDate.setText(AppUtility.getDateString(parts[0], AppUtility.DATE_FORMAT_APP_, AppUtility.DATE_FORMAT_SERVER));
        }

    }

    Fragment fragment;
    Bundle bundle;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.latestResource:
                fragment =new ResourceViewFragment();
                bundle = new Bundle();
                bundle.putString("title", Topresource.getTitle());
                bundle.putString("course_name", Topresource.getCourseName());
                bundle.putString("chapter", Topresource.getChapterTitle());
                bundle.putString("content", Topresource.getContent());
                bundle.putString("thumbnail", Topresource.getThumbnail());
                gotoFragment(fragment, "resourceViewFragment", bundle);
                break;
            case R.id.classSchedudle:
                fragment = new ClassScheduleFragment();
                bundle = new Bundle();
                gotoFragment(fragment, "classScheduleFragment", bundle);
                break;
            case R.id.resourceList:
                fragment = new ResourseFragment();
                bundle = new Bundle();
                gotoFragment(fragment, "resourseFragment" , bundle);
                break;
            case R.id.nextClass:
                break;
            case R.id.upcomingExam:
                fragment = new ExamListFragment();
                bundle = new Bundle();
                gotoFragment(fragment, "examListFragment", bundle);
                break;
            case R.id.notices:
                fragment = new NoticeListFragment();
                bundle = new Bundle();
                gotoFragment(fragment, "noticeListFragment" , bundle);
                break;
            case R.id.assignments:
                fragment = new AssignmentFragment();
                bundle = new Bundle();
                gotoFragment(fragment, "assignmentFragment", bundle);
                break;
            case R.id.notice1Ll:
                fragment = new TeacherNoticeDetails();
                bundle = new Bundle();
                bundle.putString("noticeId",n1);
                gotoFragment(fragment, "teacherNoticeDetails", bundle);
                break;
            case R.id.notice2Ll:
                fragment = new TeacherNoticeDetails();
                bundle = new Bundle();
                bundle.putString("noticeId",n1);
                gotoFragment(fragment, "teacherNoticeDetails", bundle);
                break;
        }
    }

    private void gotoFragment(Fragment fragment, String tag, Bundle bundle) {
        Slide slideTransition = new Slide(Gravity.BOTTOM);
        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
//
//        Explode changeBoundsTransition = new Explode();
//        changeBoundsTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
////

//        fragment.setEnterTransition(slideTransition);
//        fragment.setAllowEnterTransitionOverlap(false);
//        fragment.setAllowReturnTransitionOverlap(false);
//        fragment.setExitTransition(null);
//        fragment.setSharedElementEnterTransition(changeBoundsTransition);

        // load fragment
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_to_down, R.anim.slide_in_down);
        transaction.replace(R.id.mainContainer, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadFragment(Fragment fragment, String tag, boolean backstack) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        if(!backstack)
            transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.activity_menu_item:
//                // Not implemented here
//                return false;
            case R.id.notification:
                // Do Fragment menu item stuff here
                return true;
            default:
                break;
        }

        return false;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
//        if (AppSharedPreference.getStTabString().isEmpty())
//            menu.findItem(R.id.chat).setEnabled(false);
//        else
//            menu.findItem(R.id.chat).setEnabled(true);
        if (uiHelper.callNotificationCountApi().equals("0"))
            menu.findItem(R.id.notification).setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.notification));
        else
            menu.findItem(R.id.notification).setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.bell_icon));
        super.onPrepareOptionsMenu(menu);
    }



    private void loaderEnable(){
        bg.setVisibility(View.VISIBLE);
        gifImageView.setVisibility(View.VISIBLE);
    }
    private void loaderDisble(){
        bg.setVisibility(View.GONE);
        gifImageView.setVisibility(View.GONE);
    }
}
