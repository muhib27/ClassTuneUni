package com.classtune.classtuneuni.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseOfferFragment extends Fragment implements View.OnClickListener {

    FloatingActionButton fab;
    View v;
    ViewGroup main;
    private List<String> sectionList;
    Button addcourse;

    public CourseOfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_course_offer, container, false);

        main = (ViewGroup) v.findViewById(R.id.insert_point);
        addcourse = v.findViewById(R.id.addCourse);
        addcourse.setOnClickListener(this);
        sectionList = new ArrayList<>();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = view.findViewById(R.id.addBtn);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
            switch (view.getId()){

            case R.id.addBtn:
//                addSection();
                showDialog();
                break;

            case R.id.addCourse:
                sectionList = getTotalSection();
                break;
        }
    }

    private List<String> getTotalSection() {
        String text = "";
        List<String> list = new ArrayList<>();
        for(int i=0; i<main.getChildCount(); i++){
            View nextChild = main.getChildAt(i);
            //if(nextChild.getId() == i){
                text = ((TextView) nextChild).getText().toString();
          //  }
            list.add(text);

        }

        return list;
    }

    static int count = 0;
    TextView textView;

    private void addSection(String sectionName){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.section_view, null);

        textView = view.findViewById(R.id.i_am_id);
        textView.setText(sectionName);
        textView.setId(count);

        main.addView(textView);
        count++;

      //  Toast.makeText(getActivity(), ""+ main.getChildCount(), Toast.LENGTH_LONG).show();
    }

    private void showDialog(){
        final Dialog dialog = new Dialog(getActivity(), R.style.Theme_Dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
// Set the title
        // dialog.setTitle("Dialog Title");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

// inflate the layout
        dialog.setContentView(R.layout.new_section_create_dialog);

// Set the dialog text -- this is better done in the XML
        final EditText text = dialog.findViewById(R.id.sectionName);
        Button add = dialog.findViewById(R.id.addBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(text.getText()!=null && text.getText().length()>0)
                addSection(text.getText().toString());
                dialog.dismiss();
            }
        });


// Display the dialog
        dialog.show();

    }

}
