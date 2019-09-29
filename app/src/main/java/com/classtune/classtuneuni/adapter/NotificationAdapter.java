package com.classtune.classtuneuni.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.fragment.AssignmentDetailsFragment;
import com.classtune.classtuneuni.fragment.ExamDetailsFragment;
import com.classtune.classtuneuni.fragment.ExamListFragment;
import com.classtune.classtuneuni.fragment.ResourceViewFragment;
import com.classtune.classtuneuni.fragment.StudentAttendanceFragment;
import com.classtune.classtuneuni.fragment.SubjectResultFragment;
import com.classtune.classtuneuni.fragment.TeacherNoticeDetails;
import com.classtune.classtuneuni.model.CourseModel;
import com.classtune.classtuneuni.notification.Notification;
import com.classtune.classtuneuni.utils.PaginationAdapterCallback;

import java.util.ArrayList;
import java.util.List;


public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Notification> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int LOADING = 2;
    private static final int ITEM = 0;
    private String currentTime = "";
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private PaginationAdapterCallback mCallback;
    private String errorMsg;

    public NotificationAdapter(Context context, PaginationAdapterCallback mCallback) {
//        mValues = values;
        mContext = context;
        mValues = new ArrayList<>();
        this.mCallback = mCallback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item, parent, false);
//        return new ViewHolder(view);
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.home_notification_item, parent, false);
                viewHolder = new MovieVH(viewItem);
                break;

            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(viewLoading);
                break;
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final Notification result = mValues.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH itemHolder = (MovieVH) viewHolder;
                if (result.getTitle()!= null)
                    itemHolder.title.setText(result.getTitle());
//                if (result.getNotice().getCreatedAt() != null)
//                    itemHolder.timeBefore.setText(AppUtility.getTimeDifference(currentTime, result.getNotice().getCreatedAt()) + "ago");

                //"notice" => 1,//st
//                "exam_schedule" => 2,
//                "exam_report" => 3/st,
//                "final_exam_report" => 4/st,
//                "assignment" => 5,/st
//                "assignment_submited" => 6,
//                "assignment_mark" => 7,/st
//                "resource_material" => 8,//st
//                "feedback_submitted" => 9,
//                "attendance" => 10,//st
//                "requested_for_resubmission" => 11,
//                "resubmission_accepted" => 12,
//                "interest" => 13,
//                "invitation" => 14
                if (result.getTargetType()!=null) {
                    if(result.getTargetType().equalsIgnoreCase("1")) {
                        itemHolder.subject.setText("Notice");
                        Glide.with(mContext)
                                .load(R.drawable.notice_m)
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.notice_m)
                                        .fitCenter())
                                .into(itemHolder.img);
                    }
                    if(result.getTargetType().equalsIgnoreCase("2")) {
                        itemHolder.subject.setText("Exam Schedule");
                        Glide.with(mContext)
                                .load(R.drawable.exam_icon)
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.notice_m)
                                        .fitCenter())
                                .into(itemHolder.img);
                    }
                    if(result.getTargetType().equalsIgnoreCase("3")) {
                        itemHolder.subject.setText("Exam Report");
                        Glide.with(mContext)
                                .load(R.drawable.exam_icon)
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.notice_m)
                                        .fitCenter())
                                .into(itemHolder.img);
                    }
                    if(result.getTargetType().equalsIgnoreCase("4")) {
                        itemHolder.subject.setText("Exam Result");
                        Glide.with(mContext)
                                .load(R.drawable.result)
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.notice_m)
                                        .fitCenter())
                                .into(itemHolder.img);
                    }
                    if(result.getTargetType().equalsIgnoreCase("5")) {
                        itemHolder.subject.setText("Assignment");
                        Glide.with(mContext)
                                .load(R.drawable.assignment_m)
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.notice_m)
                                        .fitCenter())
                                .into(itemHolder.img);
                    }
                    if(result.getTargetType().equalsIgnoreCase("7")) {
                        itemHolder.subject.setText("Assignment Mark");
                        Glide.with(mContext)
                                .load(R.drawable.assignment_m)
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.notice_m)
                                        .fitCenter())
                                .into(itemHolder.img);
                    }
                    if(result.getTargetType().equalsIgnoreCase("8")) {
                        itemHolder.subject.setText("Resource");
                        Glide.with(mContext)
                                .load(R.drawable.resource_l)
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.notice_m)
                                        .fitCenter())
                                .into(itemHolder.img);
                    }
                    if(result.getTargetType().equalsIgnoreCase("10")) {
                        itemHolder.subject.setText("Attendance");
                        Glide.with(mContext)
                                .load(R.drawable.attendance_icon)
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.notice_m)
                                        .fitCenter())
                                .into(itemHolder.img);
                    }
                }



                //itemHolder.download.setText(result.getUrl());
                itemHolder.notificationLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle;
                        Fragment fragment;
                        if (result.getTargetType().equalsIgnoreCase("1")) {
                            fragment = new TeacherNoticeDetails();
                            bundle = new Bundle();
                            bundle.putString("noticeId",result.getTargetId());
                            gotoFragment(fragment, "teacherNoticeDetails", bundle);
                        } else if (result.getTargetType().equalsIgnoreCase("2")) {
                            // gotoCMSubmitTaskDetailsFragment(id);
                            fragment = new ExamListFragment();
                            bundle = new Bundle();
                            bundle.putString("id", result.getTargetId());
                            fragment.setArguments(bundle);
                            gotoFragment(fragment, "examListFragment", bundle);
                        }
                        else if (result.getTargetType().equalsIgnoreCase("3")) {
                            //gotoReadingPackageFragmentNotify(id);
                            fragment = new ExamDetailsFragment();
                            bundle = new Bundle();
                            bundle.putString("id", result.getTargetId());
                            fragment.setArguments(bundle);
                            gotoFragment(fragment, "examDetailsFragment", bundle);
                        }else if (result.getTargetType().equalsIgnoreCase("4")) {
                            //gotoReadingPackageFragmentNotify(id);
                            fragment = new SubjectResultFragment();
                            bundle = new Bundle();
                            bundle.putString("id", result.getTargetId());
                            fragment.setArguments(bundle);
                            gotoFragment(fragment, "subjectResultFragment", bundle);
                        }
                        else if (result.getTargetType().equalsIgnoreCase("8")) {
                            //gotoReadingPackageFragmentNotify(id);
                            fragment = new ResourceViewFragment();
                            bundle = new Bundle();
                            bundle.putString("id", result.getTargetId());
                            fragment.setArguments(bundle);
                            gotoFragment(fragment, "resourceViewFragment", bundle);
                        }
                        else if (result.getTargetType().equalsIgnoreCase("5") || result.getTargetType().equalsIgnoreCase("7")) {
                            //gotoReadingPackageFragmentNotify(id);
                            fragment = new AssignmentDetailsFragment();
                            bundle = new Bundle();
                            bundle.putString("assignmentId", result.getTargetId());
                            fragment.setArguments(bundle);
                            gotoFragment(fragment, "assignmentDetailsFragment", bundle);
                        }
                        else if (result.getTargetType().equalsIgnoreCase("10")) {
                            //gotoReadingPackageFragmentNotify(id);
                            fragment = new StudentAttendanceFragment();
                            bundle = new Bundle();
                            bundle.putString("id", result.getTargetId());
                            fragment.setArguments(bundle);
                            gotoFragment(fragment, "studentAttendanceFragment", bundle);
                        }

                    }
                });


                break;
            case LOADING:
                LoadingVH loadingVH = (LoadingVH) viewHolder;

                if (retryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);

                    loadingVH.mErrorTxt.setText(
                            errorMsg != null ?
                                    errorMsg :
                                    mContext.getString(R.string.error_msg_unknown));

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }


    @Override
    public int getItemCount() {
        return mValues == null ? 0 : mValues.size();
    }

    @Override
    public int getItemViewType(int position) {
//        if (position == 0) {
//            return HERO;
//        } else
//        if (position == mValues.size() - 1 && isLoadingAdded)
//            return LOADING;
//        else
            return ITEM;
    }

    public interface ItemListener {
        void onItemClick(CourseModel item, int pos);
    }


    protected class MovieVH extends RecyclerView.ViewHolder {
        private TextView title, subject, timeBefore;
        private TextView download;
        private TextView grade; // displays "year | language"
        private ImageView img;
        private ProgressBar mProgress;
        private TextView menuOption;
        private LinearLayout viewLl;
        CardView cardView;
        private RelativeLayout notificationLl;
        View verticalLine, dotView;

        public MovieVH(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            img = itemView.findViewById(R.id.img);
            subject = itemView.findViewById(R.id.subject);
            notificationLl = itemView.findViewById(R.id.notificationLl);

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
            title = itemView.findViewById(R.id.title);
        }
    }

    public void add(Notification r) {
        mValues.add(r);
        notifyItemInserted(mValues.size() - 1);
    }

    public void addAllData(List<Notification> moveResults) {
        for (Notification result : moveResults) {
            add(result);
        }

    }

    public void remove(Notification r) {
        int position = mValues.indexOf(r);
        if (position > -1) {
            mValues.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Notification());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mValues.size() - 1;
        Notification result = getItem(position);

        if (result != null) {
            mValues.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Notification getItem(int position) {
        return mValues.get(position);
    }


    private void gotoFragment(Fragment fragment, String tag, Bundle bundle) {
        // load fragment
        fragment.setArguments(bundle);
        FragmentTransaction transaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar mProgressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);

            mProgressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
            mRetryBtn = (ImageButton) itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = (TextView) itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = (LinearLayout) itemView.findViewById(R.id.loadmore_errorlayout);

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:

                    showRetry(false, null);
                    mCallback.retryPageLoad();

                    break;
            }
        }
    }

    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(mValues.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;


    }
}