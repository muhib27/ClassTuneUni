package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.RelatedCourseAdapter;
import com.classtune.classtuneuni.adapter.StCourseAdapter;
import com.classtune.classtuneuni.course_resonse.Course;
import com.classtune.classtuneuni.utils.PaginationAdapterCallback;
import com.classtune.classtuneuni.utils.UIHelper;
import com.classtune.classtuneuni.utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseDetailsFragment extends Fragment implements View.OnClickListener, PaginationAdapterCallback {
    private LinearLayout allCourseLayout;
    RecyclerView recyclerView;
    private List<Course> courseList;
//    LinearLayoutManager linearLayoutManager;
    UIHelper uiHelper;
    RelatedCourseAdapter relatedCourseAdapter;

    String contentSt = "কেন্দ্রীয় ব্যাংকে রক্ষিত বৈদেশিক মুদ্রার মজুত বা রিজার্ভ চুরির ক্ষেত্র প্রস্তুত করে রেখেছিল বাংলাদেশ ব্যাংক নিজেই। নিরাপত্তাব্যবস্থা ছিল অরক্ষিত, সংশ্লিষ্ট কর্মকর্তারা ছিলেন দায়িত্বহীন। আর চূড়ান্ত সর্বনাশ ঘটানো হয় সুইফট সার্ভারের সঙ্গে স্থানীয় নেটওয়ার্ক জুড়ে দিয়ে। এর ছয় মাসের মধ্যেই গোপন সংকেত বা পাসওয়ার্ড জেনে নিয়ে চুরি হয় ৮ কোটি ১০ লাখ ১ হাজার ৬২৩ মার্কিন ডলার (বাংলাদেশি মুদ্রায় ৮১০ কোটি টাকা)। \n" +
            "\n" +
            "বাংলাদেশ ব্যাংকের রিজার্ভ থেকে অর্থ চুরি হয় প্রায় সাড়ে তিন বছর আগে, ২০১৬ সালের ৪ ফেব্রুয়ারি রাতে। বাংলাদেশ ব্যাংক এক দিন পরে চুরির তথ্য জানতে পারলেও তা গোপন রাখে আরও ২৪ দিন। আর বিষয়টি অর্থমন্ত্রীকে বাংলাদেশ ব্যাংক আনুষ্ঠানিকভাবে জানায় ৩৩তম দিনে।";

    private WebView webView;

    LinearLayoutManager layoutManager;
    public CourseDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        uiHelper = new UIHelper(getActivity());

        recyclerView = view.findViewById(R.id.recyclerView);
        webView = view.findViewById(R.id.webView);
        allCourseLayout = view.findViewById(R.id.allCourseLayout);
        allCourseLayout.setOnClickListener(this);

        courseList = new ArrayList<>();

        courseList.add(new Course("Title"));
        courseList.add(new Course("Title"));
        courseList.add(new Course("Title"));

        relatedCourseAdapter = new RelatedCourseAdapter(getActivity(), this);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(relatedCourseAdapter);

        relatedCourseAdapter.addAllData(courseList);
        populateData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.allCourseLayout:
                Fragment fragment = new TeacherAllCourseFragment();
                gotoFragment(fragment, "teacherAllCourseFragment", "1");
        }
    }

    @Override
    public void retryPageLoad() {

    }

    private void populateData() {
        if (contentSt != null)
            webView.loadData(contentSt, "text/html; charset=utf-8", "UTF-8");
//        if (subjectName != null)
//            title.setText(subjectName);
//        if (titleSt != null)
//            subCode.setText(titleSt);
    }

    private void gotoFragment(Fragment fragment, String tag, String courseId) {
        // load fragment
        Bundle bundle = new Bundle();
        bundle.putString("courseId", courseId);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
