package com.classtune.classtuneuni.adapter;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.activity.QuizActivity;
import com.classtune.classtuneuni.fragment.ExamDetailsFragment;
import com.classtune.classtuneuni.fragment.QuizDetailsFragment;
import com.classtune.classtuneuni.fragment.TeacherResultEntryFragment;
import com.classtune.classtuneuni.model.ExamInfoModel;
import com.classtune.classtuneuni.model.Quiz;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.AppUtility;
import com.classtune.classtuneuni.utils.PaginationAdapterCallback;

import java.util.ArrayList;
import java.util.List;


public class QuizListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Quiz> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int HERO = 2;
    private static final int ITEM = 0;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private PaginationAdapterCallback mCallback;
    private String errorMsg;

    public QuizListAdapter(Context context) {
        mValues = new ArrayList<>();
        mContext = context;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item, parent, false);
//        return new ViewHolder(view);
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.quiz_item_row, parent, false);
                viewHolder = new MovieVH(viewItem);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final Quiz quiz = mValues.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH itemHolder = (MovieVH) viewHolder;
                itemHolder.quizName.setText(quiz.getQuizTitle());
                itemHolder.quizSubject.setText(quiz.getCourseName());
                itemHolder.startDate.setText(AppUtility.getDateString(quiz.getStartDate(), AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));
                itemHolder.endDate.setText(AppUtility.getDateString(quiz.getEndDate(), AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));
                itemHolder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fragment fragment = new QuizDetailsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("id", quiz.getId());
                        gotoFragment(fragment, "quizDetailsFragment", bundle);

                    }
                });
                break;
        }
    }



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
        void onItemClick(ExamInfoModel item, int pos);
    }


    protected class MovieVH extends RecyclerView.ViewHolder {
        private TextView quizSubject;
        private TextView quizName;
        private TextView marks, total, startDate, endDate; // displays "year | language"
        private ImageView mPosterImg;
        private ProgressBar mProgress;
        private TextView examName, praticipantText;
        private RelativeLayout examCell;
        CardView cardView;

        public MovieVH(View itemView) {
            super(itemView);
            quizName = itemView.findViewById(R.id.quizName);
            quizSubject = itemView.findViewById(R.id.quizSubject);
            startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.endDate);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }


    public void add(Quiz r) {
        mValues.add(r);
        notifyItemInserted(mValues.size() - 1);
    }

    public void addAllData(List<Quiz> moveResults) {
        for (Quiz result : moveResults) {
            add(result);
        }
    }

    public void remove(Quiz r) {
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
        add(new Quiz());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mValues.size() - 1;
        Quiz result = getItem(position);

        if (result != null) {
            mValues.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Quiz getItem(int position) {
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
}