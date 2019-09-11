package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.adapter.AttachmentAdapter;
import com.classtune.classtuneuni.assignment.AssinmentAttachment;
import com.classtune.classtuneuni.assignment.TeacherAssignmentResponse;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacAssiDetailsViewFragment extends Fragment  implements View.OnClickListener {
    private TextView content, attachment, name, studentId;
    private EditText marks;
    private WebView webView;
    RecyclerView recyclerView;
    private List<AssinmentAttachment> attachmentModelList;
    LinearLayoutManager linearLayoutManager;
    UIHelper uiHelper;
    AttachmentAdapter attachmentAdapter;
    LinearLayout stInfo;


    private String current ="";

    String assignmentId = "", attachmentId = "";


    public TeacAssiDetailsViewFragment() {
        // Required empty public constructor
    }

    ViewPager viewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_assignment_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uiHelper = new UIHelper(getActivity());
        viewPager = view.findViewById(R.id.pager);


//        assignmentId = getArguments().getString("assignmentId");
//        attachmentId = getArguments().getString("id");
//        attachmentModelList = new ArrayList<>();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });



    }



    @Override
    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.content:
//                if(!current.equals("content")) {
//                    content.setBackgroundColor(getResources().getColor(R.color.appColor));
//                    content.setTextColor(getResources().getColor(R.color.white));
//
//                    attachment.setBackgroundColor(getResources().getColor(R.color.menu_divider));
//                    attachment.setTextColor(getResources().getColor(R.color.ashTextColor));
//
//                    current = "content";
//                    recyclerView.setVisibility(View.GONE);
//                    webView.setVisibility(View.VISIBLE);
//
//                  //  callStAssignmentViewApi(assignmentId);
//                }
//
//
//                break;
//            case R.id.attachment:
//                if(!current.equals("attachment")) {
//                    webView.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
//
//                    attachment.setBackgroundColor(getResources().getColor(R.color.appColor));
//                    attachment.setTextColor(getResources().getColor(R.color.white));
//
//                    content.setBackgroundColor(getResources().getColor(R.color.menu_divider));
//                    content.setTextColor(getResources().getColor(R.color.ashTextColor));
//                    current = "attachment";
//                }
//                break;
//        }

    }



}
