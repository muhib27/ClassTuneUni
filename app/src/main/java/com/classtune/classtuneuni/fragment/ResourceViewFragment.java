package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResourceViewFragment extends Fragment {
    private TextView content, attachment, title, courseName, chapter;

    private WebView webView;
    private ImageView img;

    String titleSt = "", contentSt = "", courseNameSt = "", chapterSt= "", thumbnailSt = "";


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
        if (getArguments().getString("course_name") != null)
            courseNameSt = getArguments().getString("course_name");
        if (getArguments().getString("content") != null)
            contentSt = getArguments().getString("content");
        if (getArguments().getString("chapter") != null)
            chapterSt = getArguments().getString("chapter");
        if (getArguments().getString("thumbnail") != null)
            thumbnailSt = getArguments().getString("thumbnail");


        webView = view.findViewById(R.id.webview);

        title = view.findViewById(R.id.title);
        courseName = view.findViewById(R.id.courseName);
        chapter = view.findViewById(R.id.chapter);
        img = view.findViewById(R.id.newsPoster);

        populateData();


    }


    private void populateData() {
        if (contentSt != null) {
//            webView.loadData(contentSt, "text/html; charset=utf-8", "UTF-8");
            String youtContentStr = String.valueOf(Html
                    .fromHtml("<![CDATA[<body style=\"text-align:justify;color:#222222; \">"
                            + contentSt
                            + "</body>]]>"));
            webView.loadDataWithBaseURL(null, "<style>figure{height: auto;width: 100% !important; padding:0px !important;margin:0px !important;} img{height: auto;width: 100% !important;} iframe{display: inline;height: auto;max-width: 100%;}</style>" + youtContentStr, "text/html", "UTF-8", null);

        }
            if (courseNameSt != null)
            courseName.setText(courseNameSt);
        if (titleSt != null)
            title.setText(titleSt);
        if (chapterSt != null)
            chapter.setText(chapterSt);

        if(thumbnailSt!= null && !thumbnailSt.isEmpty()) {
            Glide.with(getActivity())
                    .load(thumbnailSt)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.news_poster)
                            .fitCenter())
                    .into(img);
        }
        else {
            img.setVisibility(View.GONE);
        }
    }
}
