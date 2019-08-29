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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.home.StHomeFeed;
import com.classtune.classtuneuni.model.AssignmentModel;
import com.classtune.classtuneuni.utils.PaginationAdapterCallback;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class StHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<StHomeFeed> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int HEADER = 0;
    private static final int NOTICE = 1;
    private static final int ASSIGNMENT = 5;
    private static final int ASSIGNMENT_MARK = 7;
    private static final int RESOURCE = 8;
    private static final int EXAM_SCHEDULE = 2;
    private static final int EXAM_REPORT = 3;

    private static final int LOADING = 10;


    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private PaginationAdapterCallback mCallback;

    private String errorMsg;


    public StHomeAdapter(Context context, PaginationAdapterCallback mCallback) {
        mValues = new ArrayList<>();
        mContext = context;
        this.mCallback = mCallback;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item, parent, false);
//        return new ViewHolder(view);
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ASSIGNMENT:
                View viewAssignment = inflater.inflate(R.layout.home_assignment_item_row, parent, false);
                viewHolder = new AssignmentView(viewAssignment);
                break;
            case ASSIGNMENT_MARK:
                View viewAssignmentMark = inflater.inflate(R.layout.home_assignment_mark_item_row, parent, false);
                viewHolder = new AssignmentMarkView(viewAssignmentMark);
                break;

            case NOTICE:
                View noticeView = inflater.inflate(R.layout.home_notice_item_row, parent, false);
                viewHolder = new NoticeView(noticeView);
                break;
            case EXAM_SCHEDULE:
                View viewExam = inflater.inflate(R.layout.home_exam_item_row, parent, false);
                viewHolder = new ExamView(viewExam);
                break;
            case EXAM_REPORT:
                View viewExamResult = inflater.inflate(R.layout.home_exam_result_item_row, parent, false);
                viewHolder = new ExamResultView(viewExamResult);
                break;
            case RESOURCE:
                View viewResource = inflater.inflate(R.layout.home_exam_result_item_row, parent, false);
                viewHolder = new ResourceView(viewResource);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(viewLoading);
                break;
//            case HEADER:
//                View viewHeader = inflater.inflate(R.layout.home_header, parent, false);
//                viewHolder = new LoadingVH(viewHeader);
//                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final StHomeFeed result = mValues.get(position);
        switch (getItemViewType(position)) {

            case ASSIGNMENT:
                final AssignmentView assignmentHolder = (AssignmentView) viewHolder;
//                assignmentHolder.title.setText(result.getContent());
//                assignmentHolder.name.setText(result.getSenderName());


                break;
            case ASSIGNMENT_MARK:
                final AssignmentMarkView assignmentMarkView = (AssignmentMarkView) viewHolder;
//                assignmentHolder.title.setText(result.getContent());
//                assignmentHolder.name.setText(result.getSenderName());



                break;
            case NOTICE:
                final NoticeView noticeViewHolder = (NoticeView) viewHolder;
                //  heroHolder.title.setText(result.getContent());

                break;
//            case HEADER:
//                final Header headerHolder = (Header) viewHolder;
//                //  heroHolder.title.setText(result.getContent());
//
//                break;

            case EXAM_SCHEDULE:
                final ExamView examViewHolder = (ExamView) viewHolder;
                //  heroHolder.title.setText(result.getContent());

                break;
            case EXAM_REPORT:
                final ExamResultView examResultViewHolder = (ExamResultView) viewHolder;
                //  heroHolder.title.setText(result.getContent());

                break;
            case RESOURCE:
                final ResourceView resourceView = (ResourceView) viewHolder;
                //  heroHolder.title.setText(result.getContent());

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

//    @Override
//    public void onBindViewHolder(ViewHolder viewHolder, int position) {
//        viewHolder.setData(mValues.get(position));
//    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (position == mValues.size() - 1 && isLoadingAdded)
            return LOADING;
//        else {
//            if (position == 0)
//                return HEADER;
            else {
                if (mValues.get(position).getContentType() == 1)
                    return NOTICE;
                else if (mValues.get(position).getContentType() == 2)
                    return EXAM_SCHEDULE;
                else if (mValues.get(position).getContentType() == 3)
                    return EXAM_REPORT;
                else if (mValues.get(position).getContentType() == 5)
                    return ASSIGNMENT;
                else if (mValues.get(position).getContentType() == 7)
                    return ASSIGNMENT_MARK;
                else
                    //if (mValues.get(position).getContentType() == 8)
                    return RESOURCE;
     //       }
        }
    }

    public interface ItemListener {
        void onItemClick(AssignmentModel item, int pos);
    }


    protected class AssignmentView extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView name;
        private TextView dueDate; // displays "year | language"
        private ImageView img;
        private ProgressBar mProgress;
        private TextView subject, section, present, total;
        private FrameLayout itemLayout;
        CardView cardView;
        CircleImageView pic;
        LinearLayout numLl, imgLl;

        public AssignmentView(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.text);
            pic = itemView.findViewById(R.id.pic);
            name = itemView.findViewById(R.id.name);
//            assignedDate = itemView.findViewById(R.id.assignedDate);
//            dueDate = itemView.findViewById(R.id.dueDate);
//            subject = itemView.findViewById(R.id.subject);
//            section = itemView.findViewById(R.id.section);
//            present = itemView.findViewById(R.id.present);
//            total = itemView.findViewById(R.id.total);
//            img = itemView.findViewById(R.id.img);
////            assignedDate = itemView.findViewById(R.id.assignedDate);
//            cardView = itemView.findViewById(R.id.cardView);
//            imgLl = itemView.findViewById(R.id.imgLl);
//            numLl = itemView.findViewById(R.id.numLl);

        }
    }

    protected class AssignmentMarkView extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView name;
        private TextView dueDate; // displays "year | language"
        private ImageView img;
        private ProgressBar mProgress;
        private TextView subject, section, present, total;
        private FrameLayout itemLayout;
        CardView cardView;
        CircleImageView pic;
        LinearLayout numLl, imgLl;

        public AssignmentMarkView(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.text);
            pic = itemView.findViewById(R.id.pic);
            name = itemView.findViewById(R.id.name);
//            assignedDate = itemView.findViewById(R.id.assignedDate);
//            dueDate = itemView.findViewById(R.id.dueDate);
//            subject = itemView.findViewById(R.id.subject);
//            section = itemView.findViewById(R.id.section);
//            present = itemView.findViewById(R.id.present);
//            total = itemView.findViewById(R.id.total);
//            img = itemView.findViewById(R.id.img);
////            assignedDate = itemView.findViewById(R.id.assignedDate);
//            cardView = itemView.findViewById(R.id.cardView);
//            imgLl = itemView.findViewById(R.id.imgLl);
//            numLl = itemView.findViewById(R.id.numLl);

        }
    }

    protected class NoticeView extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView mMovieDesc;
        private TextView mYear; // displays "year | language"
        private ImageView mPosterImg;
        private CardView cardView;
        CircleImageView pic;

        public NoticeView(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text);
            pic = itemView.findViewById(R.id.pic);
        }
    }
    protected class Header extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView mMovieDesc;
        private TextView mYear; // displays "year | language"
        private ImageView mPosterImg;
        private CardView cardView;
        CircleImageView pic;

        public Header(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text);
            pic = itemView.findViewById(R.id.pic);
        }
    }

    protected class ExamView extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView mMovieDesc;
        private TextView mYear; // displays "year | language"
        private ImageView mPosterImg;
        private CardView cardView;
        CircleImageView pic;

        public ExamView(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text);
            pic = itemView.findViewById(R.id.pic);
        }
    }

    protected class ExamResultView extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView mMovieDesc;
        private TextView mYear; // displays "year | language"
        private ImageView mPosterImg;
        private CardView cardView;
        CircleImageView pic;

        public ExamResultView(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text);
            pic = itemView.findViewById(R.id.pic);
        }
    }

    protected class ResourceView extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView mMovieDesc;
        private TextView mYear; // displays "year | language"
        private ImageView mPosterImg;
        private CardView cardView;
        CircleImageView pic;

        public ResourceView(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text);
            pic = itemView.findViewById(R.id.pic);
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

    public void add(StHomeFeed r) {
        mValues.add(r);
        notifyItemInserted(mValues.size() - 1);
    }

    public void addAllData(List<StHomeFeed> moveResults) {
        for (StHomeFeed result : moveResults) {
            add(result);
        }
    }

    public void remove(StHomeFeed r) {
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
        add(new StHomeFeed());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mValues.size() - 1;
        StHomeFeed result = getItem(position);

        if (result != null) {
            mValues.remove(position);
            notifyItemRemoved(position);
        }
    }

    public StHomeFeed getItem(int position) {
        return mValues.get(position);
    }

    private void gotoFragment(Fragment fragment, String tag, String assignmentId) {
        // load fragment
        Bundle bundle = new Bundle();
        bundle.putString("assignmentId", assignmentId);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        //transaction.addToBackStack(null);
        transaction.commit();
    }
}