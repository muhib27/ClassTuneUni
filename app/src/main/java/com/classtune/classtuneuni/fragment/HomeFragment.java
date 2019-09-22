package com.classtune.classtuneuni.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.adapter.AssignmentAdapter;
import com.classtune.classtuneuni.adapter.HomeAssignmentAdapter;
import com.classtune.classtuneuni.adapter.HomeNoticeAdapter;
import com.classtune.classtuneuni.adapter.HomeResourceAdapter;
import com.classtune.classtuneuni.adapter.ItemAdapter;
import com.classtune.classtuneuni.adapter.ResourceAdapter;
import com.classtune.classtuneuni.assignment.Assignment;
import com.classtune.classtuneuni.class_schedule.Routine;
import com.classtune.classtuneuni.home.StHomeRespons;
import com.classtune.classtuneuni.model.ExamInfoModel;
import com.classtune.classtuneuni.notice.Notices;
import com.classtune.classtuneuni.profile.StProfileRsponse;
import com.classtune.classtuneuni.resource.Resource;
import com.classtune.classtuneuni.response.Notice;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.AppUtility;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;
import com.classtune.classtuneuni.utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class HomeFragment extends Fragment implements View.OnClickListener
{

    private TextView topDate, topTitle, chapter, subCode, author;
    private TextView notice1Date, notice1Title, notice2Date, notice2Title;
    private TextView nextSubject, nextClassTime, classInstructor, dayText, room;
    private TextView examDay, examDate, examMonthYear, examName, examTime, marks, examSubject;
    private View examDotView;

    private ImageView imageView, courseImg;

    RecyclerView rvNotice, rvAssignmnet, rvResource;
    private List<Assignment> assignmentList;
    LinearLayoutManager noticelinearLayoutManager, assignmentLayoutManager;
    UIHelper uiHelper;
    AssignmentAdapter assignmentAdapter;

    HomeNoticeAdapter homeNoticeAdapter;

    HomeResourceAdapter homeResourceAdapter;
    HomeAssignmentAdapter homeAssignmentAdapter;

    GridLayoutManager manager;
    private List<Resource> resourceList;
    private ImageView classSchedudle, resources, notices;
    CardView nextClass, upcomingExam;


    RelativeLayout latestResource;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_fragment_new, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uiHelper = new UIHelper(getActivity());

        latestResource = view.findViewById(R.id.latestResource);
        latestResource.setOnClickListener(this);
//        classSchedudle = view.findViewById(R.id.classSchedudle);
//        classSchedudle.setOnClickListener(this);
        resources = view.findViewById(R.id.resourceList);
        resources.setOnClickListener(this);
        nextClass = view.findViewById(R.id.nextClass);
        nextClass.setOnClickListener(this);
        upcomingExam = view.findViewById(R.id.upcomingExam);
        upcomingExam.setOnClickListener(this);
        notices = view.findViewById(R.id.notices);
        notices.setOnClickListener(this);



        topDate = view.findViewById(R.id.topDate);
        topTitle = view.findViewById(R.id.topTitle);
        chapter = view.findViewById(R.id.chapter);
        subCode = view.findViewById(R.id.subCode);
        author = view.findViewById(R.id.authorName);

        notice1Date = view.findViewById(R.id.notice1Date);
        notice2Date = view.findViewById(R.id.notice2Date);
        notice1Title = view.findViewById(R.id.notice1Title);
        notice2Title = view.findViewById(R.id.notice2Title);

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

        callStudentHome();
        

    }


    private void callStudentHome() {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
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
                        uiHelper.dismissLoadingDialog();

                        StHomeRespons stHomeRespons = value.body();
                        if (stHomeRespons.getStatus().getCode() == 200) {
                            //  stAddSection(stSectionListResponse.getData().getCourseSection());
//                            stCourseAssessmentList = stProfileRsponse.getData().getCourseAssessment();
//                            stProfileInfoAdapter.addAllData(stCourseAssessmentList);
                            homeResourceAdapter.addAllData(stHomeRespons.getResources());
                            homeNoticeAdapter.addAllData(stHomeRespons.getNotices(), stHomeRespons.getCurrentTime());
                            homeAssignmentAdapter.addAllData(stHomeRespons.getAssignments(),  stHomeRespons.getCurrentTime());
                            populateLatest(stHomeRespons.getResourceSingle());
                            populateLatestNotice(stHomeRespons.getNotice());
                            populateNextClass(stHomeRespons.getNextClass(), stHomeRespons.getWeekday());
                            populateNextExam(stHomeRespons.getExam(), stHomeRespons.getWeekday());

                        } else
                            Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
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

    private void populateNextExam(ExamInfoModel exam, String weekday) {
        if(exam.getExamName()!=null)
        examName.setText(exam.getExamName());
        if(exam.getCourseName()!=null)
            examSubject.setText(exam.getCourseName());
        if(exam.getExamMark()!=null)
            marks.setText("" + exam.getExamMark());

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
        if(nextClass.getThumbnail() !=null && !nextClass.getThumbnail().isEmpty())
            // if(resourceSi)
            Glide.with(this)
                    .load(nextClass.getThumbnail())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.demo_img)
                            .fitCenter())
                    .into(courseImg);

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

    private void populateLatestNotice(List<Notices> notice) {
        if(notice.get(0).getCreatedAt()!= null && notice.get(0).getCreatedAt().contains(" ")) {
            String[] parts = notice.get(0).getCreatedAt().split(" ");
            notice1Date.setText(AppUtility.getDateString(parts[0], AppUtility.DATE_FORMAT_APP_, AppUtility.DATE_FORMAT_SERVER));
        }
        if(notice.get(1).getCreatedAt()!= null && notice.get(1).getCreatedAt().contains(" ")) {
            String[] parts = notice.get(1).getCreatedAt().split(" ");
            notice2Date.setText(AppUtility.getDateString(parts[0], AppUtility.DATE_FORMAT_APP_, AppUtility.DATE_FORMAT_SERVER));
        }
        if(notice.get(0).getTitle() !=null)
            notice1Title.setText(notice.get(0).getTitle());
        if(notice.get(1).getTitle() !=null)
            notice2Title.setText(notice.get(1).getTitle());
    }

    private void populateLatest(Resource resourceSingle) {
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

        if(resourceSingle.getChapterTitle() !=null)
        chapter.setText(resourceSingle.getChapterTitle());

        if(resourceSingle.getCourseName() !=null)
        subCode.setText(resourceSingle.getCourseName());

        if(resourceSingle.getInstructor() !=null)
        author.setText(resourceSingle.getInstructor());

        if(resourceSingle.getCreatedAt()!= null && resourceSingle.getCreatedAt().contains(" ")) {
            String[] parts = resourceSingle.getCreatedAt().split(" ");
            topDate.setText(AppUtility.getDateString(parts[0], AppUtility.DATE_FORMAT_APP_, AppUtility.DATE_FORMAT_SERVER));
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.latestResource:
                break;
            case R.id.classSchedudle:
                break;
            case R.id.resourceList:
                break;
            case R.id.nextClass:
                break;
            case R.id.upcomingExam:
                break;
            case R.id.notices:
                break;
        }
    }
}
