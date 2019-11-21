package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.classtune.classtuneuni.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizScoreFragment extends Fragment implements View.OnClickListener {
    private CircleImageView profile_image;
    private TextView message, marks, quizNo, subject;
    private Button viewResult, retake;
    private int obtained = 0;
    private String quizName = "";
    private String subjectName = "";
    private String id = "";

    public QuizScoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_score, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null)
            obtained = getArguments().getInt("marks");
        if (getArguments() != null && getArguments().getString("quizName") != null)
            quizName = getArguments().getString("quizName");
        if (getArguments() != null && getArguments().getString("subject") != null)
            subjectName = getArguments().getString("subject");
        if (getArguments() != null && getArguments().getString("id") != null)
            id = getArguments().getString("id");
        initView(view);
    }

    private void initView(View view) {
        profile_image = view.findViewById(R.id.profile_image);
        message = view.findViewById(R.id.message);
        marks = view.findViewById(R.id.marks);
        quizNo = view.findViewById(R.id.quizNo);
        subject = view.findViewById(R.id.subject);

        viewResult = view.findViewById(R.id.view);
        viewResult.setOnClickListener(this);

        retake = view.findViewById(R.id.retake);
        retake.setOnClickListener(this);

        marks.setText(String.valueOf(obtained));
        quizNo.setText(quizName);
        subject.setText(subjectName);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view:
                Fragment fragment = new QuizOverviewFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                gotoFragment(fragment, "quizFragment", bundle);
                break;
            case R.id.retake:
                break;
        }
    }

    private void gotoFragment(Fragment fragment, String tag, Bundle bundle) {

        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.quizContainer, fragment, tag);
//            transaction.addToBackStack(null);
        transaction.commit();


    }
}
