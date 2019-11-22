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
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.resource.Resource;
import com.classtune.classtuneuni.resource.ResourceResponse;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResourceViewFragment extends Fragment {
    private TextView content, attachment, title, courseName, chapter;

    private WebView webView;
    private ImageView img;
    UIHelper uiHelper;

    String titleSt = "", contentSt = "", courseNameSt = "", chapterSt= "", thumbnailSt = "";

    String resourceId = "";


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
        uiHelper = new UIHelper(getActivity());

        if(((MainActivity)getActivity()).tabRl.getVisibility() == View.VISIBLE)
        ((MainActivity)getActivity()).tabRl.setVisibility(View.GONE);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getArguments() !=null && getArguments().getString("id") != null) {
            resourceId = getArguments().getString("id");
            callResourceApi(resourceId);
        }
        else {

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
        }


        webView = view.findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

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


    private void callResourceApi(String id) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getResourceView(AppSharedPreference.getApiKey(), id)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResourceResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<ResourceResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        ResourceResponse resourceResponse = value.body();
                       // resourceAdapter.clear();
                        if (resourceResponse.getStatus().getCode() == 200) {
                            populateApiData(resourceResponse.getData().getCourseMaterial());
//

//                            Log.v("tt", noticeList.toString());
                            //  Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
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

    private void populateApiData(Resource courseMaterial) {
        if (courseMaterial.getContent() != null) {
//            webView.loadData(contentSt, "text/html; charset=utf-8", "UTF-8");
            String youtContentStr = String.valueOf(Html
                    .fromHtml("<![CDATA[<body style=\"text-align:justify;color:#222222; \">"
                            + courseMaterial.getContent()
                            + "</body>]]>"));
            webView.loadDataWithBaseURL(null, "<style>figure{height: auto;width: 100% !important; padding:0px !important;margin:0px !important;} img{height: auto;width: 100% !important;} iframe{display: inline;height: auto;max-width: 100%;}</style>" + youtContentStr, "text/html", "UTF-8", null);

        }
        if (courseMaterial.getCourseName() != null)
            courseName.setText(courseMaterial.getCourseName());
        if (courseMaterial.getTitle() != null)
            title.setText(courseMaterial.getTitle());
        if (courseMaterial.getChapterTitle() != null)
            chapter.setText(courseMaterial.getChapterTitle());

        if(courseMaterial.getThumbnail()!= null && !courseMaterial.getThumbnail().isEmpty()) {
            Glide.with(getActivity())
                    .load(courseMaterial.getThumbnail())
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
