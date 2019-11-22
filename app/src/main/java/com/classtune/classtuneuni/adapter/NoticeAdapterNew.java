package com.classtune.classtuneuni.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.assignment.Assignment;
import com.classtune.classtuneuni.fragment.TeacherNoticeDetails;
import com.classtune.classtuneuni.model.CourseModel;
import com.classtune.classtuneuni.response.Notice;
import com.classtune.classtuneuni.response.NoticeInfo;
import com.classtune.classtuneuni.utils.AppUtility;
import com.classtune.classtuneuni.utils.PaginationAdapterCallback;

import java.util.ArrayList;
import java.util.List;


public class NoticeAdapterNew extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Notice> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int LOADING = 2;
    private static final int ITEM = 0;
    private String currentTime = "";
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private PaginationAdapterCallback mCallback;
    private String errorMsg;

    public NoticeAdapterNew(Context context,  PaginationAdapterCallback mCallback) {
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
                View viewItem = inflater.inflate(R.layout.home_notice_item, parent, false);
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
        final Notice result = mValues.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH itemHolder = (MovieVH) viewHolder;
                if(result.getNotice().getTitle()!=null)
                itemHolder.title.setText(result.getNotice().getTitle());
                if(result.getNotice().getCreatedAt()!=null)
                    itemHolder.timeBefore.setText(AppUtility.getTimeDifference(currentTime, result.getNotice().getCreatedAt()) + "ago");
                if(result.getNotice().getCourseName()!=null)
                    itemHolder.subject.setText(result.getNotice().getCourseName());
                if(position>= (mValues.size()-1))
                    itemHolder.verticalLine.setVisibility(View.INVISIBLE);
                else
                    itemHolder.verticalLine.setVisibility(View.VISIBLE);

                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(position==0) {
                    if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        itemHolder.dotView.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.filled_circle_app_color));
                    } else {
                        itemHolder.dotView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.filled_circle_app_color));
                    }
                }
                //itemHolder.download.setText(result.getUrl());
                itemHolder.noticeLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri
//                                .parse(result.getUrl())));
                        Fragment fragment = new TeacherNoticeDetails();
                        gotoFragment(fragment, "teacherNoticeDetails", result.getNotice().getId());

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
        public int getItemCount () {
            return mValues == null ? 0 : mValues.size();
        }

        @Override
        public int getItemViewType ( int position){
//        if (position == 0) {
//            return HERO;
//        } else
            if (position == mValues.size() - 1 && isLoadingAdded)
                return LOADING;
            else
                return ITEM;
        }

        public interface ItemListener {
            void onItemClick(CourseModel item, int pos);
        }


        protected class MovieVH extends RecyclerView.ViewHolder {
            private TextView title, subject, timeBefore;
            private TextView download;
            private TextView grade; // displays "year | language"
            private ImageView mPosterImg;
            private ProgressBar mProgress;
            private TextView menuOption;
            private LinearLayout viewLl;
            CardView cardView;
            private FrameLayout noticeLl;
            View verticalLine, dotView;

            public MovieVH(View itemView) {
                super(itemView);

                title = itemView.findViewById(R.id.title);
                dotView = itemView.findViewById(R.id.dotView);
                subject = itemView.findViewById(R.id.subject);
                timeBefore = itemView.findViewById(R.id.timeBefore);
                verticalLine = itemView.findViewById(R.id.verticalLine);
                noticeLl = itemView.findViewById(R.id.noticeLl);

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

    public void add(Notice r) {
        mValues.add(r);
        notifyItemInserted(mValues.size() - 1);
    }

    public void addAllData(List<Notice> moveResults, String currentTime) {
        for (Notice result : moveResults) {
            add(result);
        }
        this.currentTime = currentTime;
    }

    public void remove(Notice r) {
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
        add(new Notice());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mValues.size() - 1;
        Notice result = getItem(position);

        if (result != null) {
            mValues.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Notice getItem(int position) {
        return mValues.get(position);
    }


    private void gotoFragment(Fragment fragment, String tag, String noticeId) {
        // load fragment
        Bundle bundle = new Bundle();
        bundle.putString("noticeId",noticeId);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = ((MainActivity)mContext).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}