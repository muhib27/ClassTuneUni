package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.classtune.classtuneuni.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssiDetailsViewFragment extends Fragment  implements View.OnClickListener {
    private TextView content, attachment, name, studentId;
    private EditText marks;


    public AssiDetailsViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assi_details_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        content = view.findViewById(R.id.content);
        attachment = view.findViewById(R.id.attachment);
        name = view.findViewById(R.id.name);
        studentId = view.findViewById(R.id.studentId);

        marks = view.findViewById(R.id.marks);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.content:
                content.setBackgroundColor(getResources().getColor(R.color.appColor));
                content.setTextColor(getResources().getColor(R.color.white));

                attachment.setBackgroundColor(getResources().getColor(R.color.menu_divider));
                attachment.setTextColor(getResources().getColor(R.color.ashTextColor));
                break;
            case R.id.attachment:
                attachment.setBackgroundColor(getResources().getColor(R.color.appColor));
                attachment.setTextColor(getResources().getColor(R.color.white));

                content.setBackgroundColor(getResources().getColor(R.color.menu_divider));
                content.setTextColor(getResources().getColor(R.color.ashTextColor));
                break;
        }

    }
}
