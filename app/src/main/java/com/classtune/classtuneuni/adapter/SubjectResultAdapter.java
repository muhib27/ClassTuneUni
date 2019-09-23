package com.classtune.classtuneuni.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.assignment.Assignment;
import com.classtune.classtuneuni.model.SubjectResultModel;

import java.util.ArrayList;
import java.util.List;


public class SubjectResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SubjectResultModel> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int HERO = 2;
    private static final int ITEM = 0;

    public SubjectResultAdapter(Context context) {
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
                View viewItem = inflater.inflate(R.layout.com_result_item_row, parent, false);
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
        final SubjectResultModel result = mValues.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH itemHolder = (MovieVH) viewHolder;
                if(result.getAssessment()!= null)
                itemHolder.subject.setText(result.getAssessment());
                if(result.getWeight()!= null)
                itemHolder.gradePoint.setText("" + result.getWeight() + "%");
                if(result.getObtained()!= null)
                itemHolder.grade.setText("" + result.getObtained());
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
        public int getItemCount () {
            return mValues.size();
        }

        @Override
        public int getItemViewType ( int position){
//        if (position == 0) {
//            return HERO;
//        } else
            return ITEM;
        }

        public interface ItemListener {
            void onItemClick(SubjectResultModel item, int pos);
        }


        protected class MovieVH extends RecyclerView.ViewHolder {
            private TextView subject;
            private TextView gradePoint;
            private TextView grade; // displays "year | language"
            private ImageView mPosterImg;
            private ProgressBar mProgress;
            private TextView menuOption;
            private FrameLayout itemLayout;
            CardView cardView;

            public MovieVH(View itemView) {
                super(itemView);

                subject = itemView.findViewById(R.id.subject);
                gradePoint = itemView.findViewById(R.id.gradePoint);
                grade = itemView.findViewById(R.id.grade);

            }
        }

    public void add(SubjectResultModel r) {
        mValues.add(r);
        notifyItemInserted(mValues.size() - 1);
    }

    public void addAllData(List<SubjectResultModel> moveResults) {
        for (SubjectResultModel result : moveResults) {
            add(result);
        }
    }

    public void remove(SubjectResultModel r) {
        int position = mValues.indexOf(r);
        if (position > -1) {
            mValues.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
       // isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }
    public SubjectResultModel getItem(int position) {
        return mValues.get(position);
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }
    }