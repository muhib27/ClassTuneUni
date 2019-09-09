package com.classtune.classtuneuni.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.class_schedule.Routine;
import com.classtune.classtuneuni.exam.Policy;
import com.classtune.classtuneuni.model.SubjectResultModel;

import java.util.ArrayList;
import java.util.List;


public class ExamPolicyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<Policy> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int HERO = 2;
    private static final int ITEM = 0;
    private boolean isLoadingAdded = false;
    private boolean isEditable;



    public ExamPolicyAdapter(Context context) {
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
                View viewItem = inflater.inflate(R.layout.exam_policy_item_row, parent, false);
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
        final Policy result = mValues.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH itemHolder = (MovieVH) viewHolder;
                if (result.getName() != null)
                    itemHolder.assessment.setText(result.getName());
                if (result.getPercentage() != null)
                    itemHolder.weight.setText("" + result.getPercentage() + "%");
                if (result.getBestCount() != null)
                    itemHolder.bestCount.setText("" + result.getBestCount());
//                if(!isEditable){
//                    itemHolder.assessment.setEnabled(false);
//                    itemHolder.assessment.setFocusable(false);
//                    itemHolder.assessment.setClickable(false);
//
//                    itemHolder.weight.setEnabled(false);
//                    itemHolder.weight.setFocusable(false);
//                    itemHolder.weight.setClickable(false);
//                }
//                else {
//                    itemHolder.assessment.setEnabled(true);
//                    itemHolder.assessment.setFocusable(true);
//                    itemHolder.assessment.setClickable(true);
//
//                    itemHolder.weight.setEnabled(true);
//                    itemHolder.weight.setFocusable(true);
//                    itemHolder.weight.setClickable(true);
//                }

//                itemHolder.assessment.addTextChangedListener(new TextWatcher() {
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start,
//                                              int before, int count) {
//                        //setting data to array, when changed
//                        Policy policy = new Policy(s.toString(), mValues.get(position).getPercentage());
//                        mValues.remove(position);
//                        mValues.add(position, policy);
//
//                    }
//
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start,
//                                                  int count, int after) {
//                        //blank
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//                        //blank
//                    }
//                });

//                itemHolder.weight.addTextChangedListener(new TextWatcher() {
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start,
//                                              int before, int count) {
//                        //setting data to array, when changed
//                        Policy policy = new Policy(mValues.get(position).getName(), s.toString());
//                        mValues.remove(position);
//                        mValues.add(position, policy);
//
//                    }
//
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start,
//                                                  int count, int after) {
//                        //blank
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//                        //blank
//                    }
//                });

//                if (result.getObtained() != null)
//                    itemHolder.grade.setText("" + result.getObtained());
//                itemHolder.cardView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (mListener != null) {
//                            mListener.onItemClick(result, position);
//                        }
//                        }
//                    });


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
        void onItemClick(SubjectResultModel item, int pos);
    }


    protected class MovieVH extends RecyclerView.ViewHolder {
        public TextView assessment;
        public TextView weight;
        private TextView bestCount; // displays "year | language"
        private ImageView mPosterImg;
        private ProgressBar mProgress;
        private TextView menuOption;
        private FrameLayout itemLayout;
        CardView cardView;

        public MovieVH(View itemView) {
            super(itemView);

            assessment = itemView.findViewById(R.id.assessment);
            weight = itemView.findViewById(R.id.weight);
            bestCount = itemView.findViewById(R.id.bestCount);


        }
    }

    public void add(Policy r) {
        mValues.add(r);
        notifyItemInserted(mValues.size() - 1);
    }

    public void addAllData(List<Policy> moveResults, boolean b) {
        for (Policy result : moveResults) {
            add(result);
        }
        isEditable = b;
    }

    public void remove(Policy r) {
        int position = mValues.indexOf(r);
        if (position > -1) {
            mValues.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public Policy getItem(int position) {
        return mValues.get(position);
    }

//    public void enableEditing(){
//        assessment.setEnabled(true);
//        assessment.setFocusable(true);
//        assessment.setClickable(true);
//
//        weight.setEnabled(true);
//        weight.setFocusable(true);
//        weight.setClickable(false);
//    }
//    public void disableEditing(){
//        assessment.setEnabled(false);
//        assessment.setFocusable(false);
//        assessment.setClickable(false);
//
//        weight.setEnabled(false);
//        weight.setFocusable(false);
//        weight.setClickable(false);
//    }
}