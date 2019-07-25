package com.classtune.classtuneuni.fragment;


import android.app.Dialog;
import android.content.Context;
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
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.adapter.ExamListAdapter;
import com.classtune.classtuneuni.adapter.TeacherExamListAdapter;
import com.classtune.classtuneuni.model.ExamInfoModel;
import com.classtune.classtuneuni.utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherExamListFragment extends Fragment implements TeacherExamListAdapter.ItemListener {

    TabHost mTabHost;
    RecyclerView recyclerView;
    private ArrayList<ExamInfoModel> examList;
    LinearLayoutManager linearLayoutManager;
    public TeacherExamListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_exam_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabHost = (TabHost) view.findViewById(android.R.id.tabhost);

        mTabHost.setup();
        setupTab(new TextView(getContext()), "All", "Summer 2019");
        setupTab(new TextView(getContext()), "Tab 2", "Summer 2018");
        setupTab(new TextView(getContext()), "Tab 3","Summer 2019");
        setupTab(new TextView(getContext()), "Tab 1","Summer 2019");
        setupTab(new TextView(getContext()), "Tab 2","Summer 2019");
        setupTab(new TextView(getContext()), "Tab 3","Summer 2019");

        recyclerView = view.findViewById(R.id.recyclerView);

        examList = new ArrayList<>();

        examList = new ArrayList<>();

        examList.add(new ExamInfoModel( "Class Test 2","Md. Rahim", "CSE 101", "10 July, 2019", "8", "10"));
        examList.add(new ExamInfoModel( "Class Test 3","Md. Rahim", "CSE 101", "10 July, 2019", "8", "10"));
        examList.add(new ExamInfoModel( "Class Test 4","Md. Rahim", "CSE 101", "10 July, 2019", "8", "10"));
        examList.add(new ExamInfoModel( "Class Test 5","Md. Rahim", "CSE 101", "10 July, 2019", "8", "10"));


        TeacherExamListAdapter teacherExamListAdapter = new TeacherExamListAdapter(getActivity(), examList, this);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(teacherExamListAdapter);
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

    @Override
    public void onItemClick(ExamInfoModel item, int pos) {
        // Toast.makeText(getActivity(), " " + pos, Toast.LENGTH_LONG).show();
       // loadFragment();
        showDialog();
    }

    private void loadFragment() {
        // load fragment
        ExamDetailsFragment examDetailsFragment = new ExamDetailsFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, examDetailsFragment, "examDetailsFragment");
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showDialog(){
        Dialog dialog = new Dialog(getActivity(), R.style.Theme_Dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
// Set the title
       // dialog.setTitle("Dialog Title");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

// inflate the layout
        dialog.setContentView(R.layout.dialog_view);

// Set the dialog text -- this is better done in the XML
//        TextView text = (TextView)dialog.findViewById(R.id.dialog_text_view);
//        text.setText("This is the text that does in the dialog box");

// Display the dialog
        dialog.show();

    }
}
