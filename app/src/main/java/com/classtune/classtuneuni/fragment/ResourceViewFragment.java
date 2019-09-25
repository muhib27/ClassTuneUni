package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResourceViewFragment extends Fragment {
    private TextView content, attachment, title, subCode;

    private WebView webView;

    String titleSt = "", contentSt = "", subjectName = "";


    public ResourceViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resource_details_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(((MainActivity)getActivity()).tabRl.getVisibility() == View.VISIBLE)
        ((MainActivity)getActivity()).tabRl.setVisibility(View.GONE);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getArguments().getString("title") != null)
            titleSt = getArguments().getString("title");
        if (getArguments().getString("subCode") != null)
            subjectName = getArguments().getString("subCode");
        if (getArguments().getString("content") != null)
            contentSt = getArguments().getString("content");

        webView = view.findViewById(R.id.webview);

        title = view.findViewById(R.id.title);
        subCode = view.findViewById(R.id.subCode);

        populateData();


    }


    private void populateData() {
        if (contentSt != null)
            webView.loadData(contentSt, "text/html; charset=utf-8", "UTF-8");
        if (subjectName != null)
            title.setText(subjectName);
        if (titleSt != null)
            subCode.setText(titleSt);
    }
}
