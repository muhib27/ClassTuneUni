package com.classtune.classtuneuni.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.activity.QuizActivity;
import com.classtune.classtuneuni.fragment.QuizExplainationFragment;
import com.classtune.classtuneuni.fragment.ResourceViewFragment;
import com.classtune.classtuneuni.model.ComResult;
import com.classtune.classtuneuni.model.QuizGridModel;
import com.classtune.classtuneuni.quiz.Question;
import com.classtune.classtuneuni.resource.Resource;
import com.classtune.classtuneuni.utils.AppUtility;
import com.classtune.classtuneuni.utils.PaginationAdapterCallback;

import java.util.ArrayList;
import java.util.List;


public class QuizGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Question> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int LOADING = 2;
    private static final int ITEM = 0;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private PaginationAdapterCallback mCallback;

    private String errorMsg;

    public QuizGridAdapter(Context context) {
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
                View viewItem = inflater.inflate(R.layout.quiz_grid_item, parent, false);
                viewHolder = new MovieVH(viewItem);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final Question result = mValues.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH itemHolder = (MovieVH) viewHolder;
                itemHolder.question.setText(String.valueOf(position + 1));

                if (result.getCorrect() != null && result.getCorrect() == 1) {

                        itemHolder.cell.setBackground(ContextCompat.getDrawable(mContext, R.drawable.correct_answer_bg));
//                        itemHolder.option.setTextColor(ContextCompat.getColor(mContext, R.color.white));
//                        itemHolder.si.setTextColor(ContextCompat.getColor(mContext, R.color.white));

                } else {

                        itemHolder.cell.setBackground(ContextCompat.getDrawable(mContext, R.drawable.wrong_answer_bg));
//                        itemHolder.option.setTextColor(ContextCompat.getColor(mContext, R.color.black));
//                        itemHolder.si.setTextColor(ContextCompat.getColor(mContext, R.color.ans_select));

                }
                itemHolder.cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fragment fragment = new QuizExplainationFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("pos", position);
                        gotoFragment(fragment, "quizExplainationFragment", bundle);
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
        if (position == mValues.size() - 1 && isLoadingAdded)
            return LOADING;
        else
            return ITEM;
    }

    public interface ItemListener {
        void onItemClick(ComResult item, int pos);
    }


    protected class MovieVH extends RecyclerView.ViewHolder {
        private TextView question;
        private TextView subject;
        private TextView date; // displays "year | language"
        private ImageView imageView;
        private ProgressBar mProgress;
        private TextView menuOption;
        private LinearLayout cell;
        CardView cardView;

        public MovieVH(View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.questionNo);
            cell = itemView.findViewById(R.id.cell);


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


    public void add(Question r) {
        mValues.add(r);
        notifyItemInserted(mValues.size() - 1);
    }


    public void addAllData(List<Question> moveResults) {
        for (Question result : moveResults) {
            add(result);
        }
    }

    public void remove(Question r) {
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
        add(new Question());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mValues.size() - 1;
        Question result = getItem(position);

        if (result != null) {
            mValues.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Question getItem(int position) {
        return mValues.get(position);
    }

//    private DrawableRequestBuilder<String> loadThumbImage(@NonNull String posterPath) {
//        return Glide
//                .with(context)
//                .load(posterPath)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
//                .centerCrop()
//                .crossFade();
//    }

    private void gotoFragment(Fragment fragment, String tag, Bundle bundle) {
        // load fragment
        fragment.setArguments(bundle);
        FragmentTransaction transaction = ((QuizActivity)mContext).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.quizContainer, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}