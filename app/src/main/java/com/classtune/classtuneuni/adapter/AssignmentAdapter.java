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
import com.classtune.classtuneuni.assignment.Assignment;
import com.classtune.classtuneuni.fragment.AssignmentDetailsFragment;
import com.classtune.classtuneuni.fragment.TeacherStudentListFragment;
import com.classtune.classtuneuni.model.AssignmentModel;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.AppUtility;
import com.classtune.classtuneuni.utils.PaginationAdapterCallback;

import java.util.ArrayList;
import java.util.List;


public class AssignmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Assignment> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int LOADING = 2;
    private static final int ITEM = 0;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private PaginationAdapterCallback mCallback;

    private String errorMsg;

    public AssignmentAdapter(Context context, PaginationAdapterCallback mCallback) {
        mValues = new ArrayList<>();
        mContext = context;
        this.mCallback = mCallback;
    }

//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        private TextView textView;
//        private ImageView imageView;
//        private RelativeLayout relativeLayout;
//        private Item item;
//
//        public ViewHolder(View v) {
//            super(v);
//            v.setOnClickListener(this);
//            textView = (TextView) v.findViewById(R.id.textView);
//            imageView = (ImageView) v.findViewById(R.id.imageView);
//            relativeLayout = (RelativeLayout) v.findViewById(R.id.relativeLayout);
//        }
//
//        public void setData(Item item) {
//            this.item = item;
//            textView.setText(item.text);
//            imageView.setImageResource(item.drawable);
//            relativeLayout.setBackgroundColor(Color.parseColor(item.color));
//        }
//
//        @Override
//        public void onClick(View view) {
//            if (mListener != null) {
//                mListener.onItemClick(item);
//            }
//        }
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item, parent, false);
//        return new ViewHolder(view);
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.assignment_item_row, parent, false);
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
        final Assignment result = mValues.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH itemHolder = (MovieVH) viewHolder;
                if(result.getAssignment().getTitle()!=null)
                itemHolder.title.setText(result.getAssignment().getTitle());

                String str = "";
                if (AppSharedPreference.getUserType().equals("3")) {
                    str = result.getAssignment().getCreatedAt();

                    String parts[] = str.split(" ");

                    itemHolder.assignedDate.setText(AppUtility.getDateString(parts[0], AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));
                    if (result.getAssignment().getDueDate() != null)
                        itemHolder.dueDate.setText(AppUtility.getDateString(result.getAssignment().getDueDate(), AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));
                    if (result.getAssignment().getCourseCode() != null)
                        itemHolder.subject.setText(result.getAssignment().getCourseCode());
                    if (result.getAssignment().getSectionName() != null)
                        itemHolder.section.setText(result.getAssignment().getSectionName());
                    if (result.getSubmission() != null && result.getSubmission()== 0) {
                        itemHolder.imgLl.setVisibility(View.VISIBLE);
                        itemHolder.numLl.setVisibility(View.GONE);
                        if (result.getMark() != null)
                            itemHolder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.not_examined));
                        else
                            itemHolder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.not_submited));
                    } else {
                        itemHolder.present.setText("" + result.getSubmission());
                        if (result.getMark() != null)
                            itemHolder.total.setText("" + result.getMark());
                    }

                    itemHolder.cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Fragment fragment = new AssignmentDetailsFragment();
                            gotoFragment(fragment, "assignmentDetailsFragment", result.getAssignment().getId());

//                        if (mListener != null) {
//                            mListener.onItemClick(result, position);
//                        }
                        }
                    });
                } else {
                    str = result.getAssignment().getAssignDate();

                    String parts[] = str.split(" ");

                    itemHolder.assignedDate.setText(AppUtility.getDateString(parts[0], AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));
                    if (result.getAssignment().getDueDate() != null)
                        itemHolder.dueDate.setText(AppUtility.getDateString(result.getAssignment().getDueDate(), AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));
                    if (result.getAssignment().getCourseCode() != null)
                        itemHolder.subject.setText(result.getAssignment().getCourseCode());
                    if (result.getAssignment().getSectionName() != null)
                        itemHolder.section.setText(result.getAssignment().getSectionName());
                    if (result.getSubmissions() != null)
                        itemHolder.present.setText("" + result.getSubmissions());
                    if (result.getStudents() != null)
                        itemHolder.total.setText("" + result.getStudents());
                    itemHolder.cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Fragment fragment = new TeacherStudentListFragment();
                            gotoFragment(fragment, "teacherStudentListFragment", result.getAssignment().getId());

//                        if (mListener != null) {
//                            mListener.onItemClick(result, position);
//                        }
                        }
                    });
                }



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
//        if (position == 0) {
//            return HERO;
//        } else
        return ITEM;
    }

    public interface ItemListener {
        void onItemClick(AssignmentModel item, int pos);
    }


    protected class MovieVH extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView assignedDate;
        private TextView dueDate; // displays "year | language"
        private ImageView img;
        private ProgressBar mProgress;
        private TextView subject, section, present, total;
        private FrameLayout itemLayout;
        CardView cardView;
        LinearLayout numLl, imgLl;

        public MovieVH(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            assignedDate = itemView.findViewById(R.id.assignedDate);
            dueDate = itemView.findViewById(R.id.dueDate);
            subject = itemView.findViewById(R.id.subject);
            section = itemView.findViewById(R.id.section);
            present = itemView.findViewById(R.id.present);
            total = itemView.findViewById(R.id.total);
            img = itemView.findViewById(R.id.img);
//            assignedDate = itemView.findViewById(R.id.assignedDate);
            cardView = itemView.findViewById(R.id.cardView);
            imgLl = itemView.findViewById(R.id.imgLl);
            numLl = itemView.findViewById(R.id.numLl);

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

    public void addAllData(List<Assignment> moveResults) {
        for (Assignment result : moveResults) {
            add(result);
        }
    }

    public void remove(Assignment r) {
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
        add(new Assignment());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mValues.size() - 1;
        Assignment result = getItem(position);

        if (result != null) {
            mValues.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Assignment getItem(int position) {
        return mValues.get(position);
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