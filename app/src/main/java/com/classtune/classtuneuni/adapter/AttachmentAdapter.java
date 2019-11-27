package com.classtune.classtuneuni.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.assignment.AssinmentAttachment;
import com.classtune.classtuneuni.course_resonse.Course;
import com.classtune.classtuneuni.fragment.CourseOfferFragment;
import com.classtune.classtuneuni.fragment.OfferedCourseDetailsFragment;
import com.classtune.classtuneuni.model.AttachmentModel;
import com.classtune.classtuneuni.model.CourseModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.DOWNLOAD_SERVICE;


public class AttachmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AssinmentAttachment> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int HERO = 2;
    private static final int ITEM = 0;

    public AttachmentAdapter(Context context) {
//        mValues = values;
        mContext = context;
        mValues = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item, parent, false);
//        return new ViewHolder(view);
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.attachment_list_item_row, parent, false);
                viewHolder = new MovieVH(viewItem);
                break;

//            case HERO:
//                View viewHero = inflater.inflate(R.layout.item_hero, parent, false);
//                viewHolder = new HeroVH(viewHero);
//                break;
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final AssinmentAttachment result = mValues.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH itemHolder = (MovieVH) viewHolder;
                if(result.getUrl()!=null && result.getUrl().contains("/")){
                    String token[] =result.getUrl().split("/");
                    if(token.length>0){
                        itemHolder.name.setText(token[token.length -1]);
                    }
                }

                //itemHolder.download.setText(result.getUrl());
                itemHolder.viewLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                                .parse(result.getUrl())));
//                        String url = "https://appharbor.com/assets/images/stackoverflow-logo.png";
//                        Intent i = new Intent(Intent.ACTION_VIEW);
//                        i.setData(Uri.parse(url));
//                        mContext.startActivity(i);

//                        dm = (DownloadManager) mContext.getSystemService(DOWNLOAD_SERVICE);
//                        DownloadManager.Request request = new DownloadManager.Request(
//                                Uri.parse(result.getUrl()));
//                        enqueue = dm.enqueue(request);
                    }
                });


                break;
        }
    }



        @Override
        public int getItemCount () {
            return mValues == null ? 0 : mValues.size();
        }

        @Override
        public int getItemViewType ( int position){
//        if (position == 0) {
//            return HERO;
//        } else
            return ITEM;
        }

        public interface ItemListener {
            void onItemClick(CourseModel item, int pos);
        }


        protected class MovieVH extends RecyclerView.ViewHolder {
            private TextView name;
            private TextView download;
            private TextView grade; // displays "year | language"
            private ImageView mPosterImg;
            private ProgressBar mProgress;
            private TextView menuOption;
            private LinearLayout viewLl;
            CardView cardView;

            public MovieVH(View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.name);
                download = itemView.findViewById(R.id.download);

                viewLl = itemView.findViewById(R.id.viewLl);

            }
        }

        protected class HeroVH extends RecyclerView.ViewHolder {
            private TextView title;
            private TextView mMovieDesc;
            private TextView mYear; // displays "year | language"
            private ImageView mPosterImg;
            private CardView cardView;

            public HeroVH(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.textView);
            }
        }

    public void add(AssinmentAttachment r) {
        mValues.add(r);
        notifyItemInserted(mValues.size() - 1);
    }

    public void addAllData(List<AssinmentAttachment> moveResults) {
        for (AssinmentAttachment result : moveResults) {
            add(result);
        }
    }

    private void gotoFragment(Fragment fragment, String tag, String courseId) {
        // load fragment

        FragmentTransaction transaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}