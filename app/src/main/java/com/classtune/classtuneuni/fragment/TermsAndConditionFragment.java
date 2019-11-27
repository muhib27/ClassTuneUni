package com.classtune.classtuneuni.fragment;


import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.Button;
import android.widget.RelativeLayout;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.LoginActivity;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.model.TermsConditionModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class TermsAndConditionFragment extends Fragment implements View.OnClickListener {

    Button rlback;
    private WebView webView;
    private String fragmentKey = "";
    public TermsAndConditionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_terms_condition, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rlback = view.findViewById(R.id.rlback);
        rlback.setOnClickListener(this);
        if (getArguments() != null && getArguments().getString("key") != null)
            fragmentKey = getArguments().getString("key");
        if(fragmentKey.equals("reg")) {
            rlback.setVisibility(View.VISIBLE);
        }
        else {
            rlback.setVisibility(View.GONE);
            if (((MainActivity) getActivity()).tabRl.getVisibility() == View.VISIBLE)
                ((MainActivity) getActivity()).tabRl.setVisibility(View.GONE);
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        webView = view.findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);


        String contentSt = getActivity().getResources().getString(R.string.terms_condition);
        if (contentSt != null) {
//            webView.loadData(contentSt, "text/html; charset=utf-8", "UTF-8");
//            String youtContentStr = String.valueOf(Html
//                    .fromHtml("<![CDATA[<body style=\"text-align:justify;color:#222222; \">"
//                            + contentSt
//                            + "</body>]]>"));
            webView.loadDataWithBaseURL(null, "<style>figure{height: auto;width: 100% !important; padding:0px !important;margin:0px !important;} img{height: auto;width: 100% !important;} iframe{display: inline;height: auto;max-width: 100%;}</style>" + contentSt, "text/html", "UTF-8", null);

        }
    }
    TermsConditionModel termsConditionModel;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rlback:
//                TermsConditionModel termsConditionModel = new TermsConditionModel();
                termsConditionModel = ViewModelProviders.of(getActivity()).get(TermsConditionModel.class);
                termsConditionModel.setData("1");
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }
}
