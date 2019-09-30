package com.classtune.classtuneuni.adapter;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.assignment.Assignment;
import com.classtune.classtuneuni.assignment.AssinmentAttachment;
import com.classtune.classtuneuni.fragment.AssignmentDetailsFragment;
import com.classtune.classtuneuni.fragment.AssignmentViewFragment;
import com.classtune.classtuneuni.model.CourseModel;
import com.classtune.classtuneuni.utils.AppUtility;

import java.util.ArrayList;
import java.util.List;


public class HomeAssignmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Assignment> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int HERO = 2;
    private static final int ITEM = 0;
    private String currentTime = "";

    public HomeAssignmentAdapter(Context context) {
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
                View viewItem = inflater.inflate(R.layout.home_assignment_item, parent, false);
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
        final Assignment result = mValues.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH itemHolder = (MovieVH) viewHolder;
                itemHolder.si.setText((position + 1) + ".");
                if(result.getTitle()!=null)
                itemHolder.title.setText(result.getTitle());
                if(result.getCourseName()!=null)
                    itemHolder.subject.setText(result.getCourseName());
                if(result.getDueDate()!=null)
                    itemHolder.date.setText(AppUtility.getDateString(result.getDueDate(), AppUtility.DATE_FORMAT_D_M, AppUtility.DATE_FORMAT_SERVER));
                if(result.getDueDate()!=null ) {
                    String[] parts = currentTime.split(" ");
                    itemHolder.daysLeft.setText(AppUtility.getTimeDue(result.getDueDate(), parts[0]));
                }
                if(result.getDue())
                    itemHolder.dueAssignment.setVisibility(View.VISIBLE);
                else
                    itemHolder.dueAssignment.setVisibility(View.INVISIBLE);

                itemHolder.assignmentLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fragment fragment = new AssignmentDetailsFragment();
                        gotoFragment(fragment, "assignmentDetailsFragment", result.getId());
                    }
                });
                //itemHolder.download.setText(result.getUrl());
//                itemHolder.viewLl.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri
//                                .parse(result.getUrl())));
//                        String url = "https://appharbor.com/assets/images/stackoverflow-logo.png";
//                        Intent i = new Intent(Intent.ACTION_VIEW);
//                        i.setData(Uri.parse(url));
//                        mContext.startActivity(i);

//                        dm = (DownloadManager) mContext.getSystemService(DOWNLOAD_SERVICE);
//                        DownloadManager.Request request = new DownloadManager.Request(
//                                Uri.parse(result.getUrl()));
//                        enqueue = dm.enqueue(request);
//                    }
//                });


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
            private TextView title, subject, instructor, date, daysLeft;
            private TextView si;
            private TextView grade; // displays "year | language"
            private ImageView mPosterImg;
            private ProgressBar mProgress;
            private TextView menuOption;
            private LinearLayout viewLl;
            private RelativeLayout assignmentLl;
            private View dueAssignment;
            CardView cardView;

            public MovieVH(View itemView) {
                super(itemView);

                title = itemView.findViewById(R.id.title);
                subject = itemView.findViewById(R.id.subject);
               // instructor = itemView.findViewById(R.id.instructor);
                date = itemView.findViewById(R.id.date);
                daysLeft = itemView.findViewById(R.id.dayLeft);
                si = itemView.findViewById(R.id.si);
                dueAssignment = itemView.findViewById(R.id.dueAssignment);
                assignmentLl = itemView.findViewById(R.id.assignmentLl);
//                download = itemView.findViewById(R.id.download);
//
//                viewLl = itemView.findViewById(R.id.viewLl);

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

    public void add(Assignment r) {
        mValues.add(r);
        notifyItemInserted(mValues.size() - 1);
    }

    public void addAllData(List<Assignment> moveResults, String currentTime) {
        for (Assignment result : moveResults) {
            add(result);
        }
        this.currentTime = currentTime;
    }

    public void remove(Assignment r) {
        int position = mValues.indexOf(r);
        if (position > -1) {
            mValues.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        //  isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }
    public Assignment getItem(int position) {
        return mValues.get(position);
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    private void gotoFragment(Fragment fragment, String tag, String assignmentId) {
        // load fragment
        Bundle bundle = new Bundle();
        bundle.putString("assignmentId", assignmentId);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}