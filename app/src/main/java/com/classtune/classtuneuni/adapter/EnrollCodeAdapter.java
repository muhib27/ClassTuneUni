package com.classtune.classtuneuni.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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
import com.classtune.classtuneuni.course_resonse.Course;
import com.classtune.classtuneuni.course_resonse.OfferedCourseSection;
import com.classtune.classtuneuni.model.ExamInfoModel;

import java.util.ArrayList;
import java.util.List;


public class EnrollCodeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<OfferedCourseSection> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int HERO = 2;
    private static final int ITEM = 0;

    public EnrollCodeAdapter(Context context) {
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
                View viewItem = inflater.inflate(R.layout.offered_course_item_row, parent, false);
                viewHolder = new MovieVH(viewItem);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final OfferedCourseSection offeredCourseSection = mValues.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH itemHolder = (MovieVH) viewHolder;
                itemHolder.code.setText(offeredCourseSection.getEnCode());
                itemHolder.section.setText(offeredCourseSection.getName());
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
            void onItemClick(ExamInfoModel item, int pos);
        }


        protected class MovieVH extends RecyclerView.ViewHolder {
            private TextView code;
            private TextView section;
            private TextView marks; // displays "year | language"
            private ImageView mPosterImg;
            private ProgressBar mProgress;
            private TextView examName;
            private RelativeLayout examCell;
            CardView cardView;

            public MovieVH(View itemView) {
                super(itemView);
                code = itemView.findViewById(R.id.code);
                section = itemView.findViewById(R.id.section);


            }
        }

    public void add(OfferedCourseSection r) {
        mValues.add(r);
        notifyItemInserted(mValues.size() - 1);
    }

    public void addAllData(List<OfferedCourseSection> moveResults) {
        for (OfferedCourseSection result : moveResults) {
            add(result);
        }
    }

    }