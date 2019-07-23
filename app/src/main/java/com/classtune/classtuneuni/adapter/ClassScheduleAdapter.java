package com.classtune.classtuneuni.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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
import com.classtune.classtuneuni.model.ClassScheduleModel;
import com.classtune.classtuneuni.model.ExamInfoModel;

import java.util.ArrayList;


public class ClassScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ClassScheduleModel> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int HERO = 2;
    private static final int ITEM = 0;

    public ClassScheduleAdapter(Context context, ArrayList<ClassScheduleModel> values, ItemListener itemListener) {
        mValues = values;
        mContext = context;
        mListener = itemListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item, parent, false);
//        return new ViewHolder(view);
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.class_schedule_item_row, parent, false);
                viewHolder = new MovieVH(viewItem);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final ClassScheduleModel classScheduleModel = mValues.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH itemHolder = (MovieVH) viewHolder;
                itemHolder.day.setText(classScheduleModel.getDay());
                itemHolder.date.setText(classScheduleModel.getDate());
                itemHolder.monthYear.setText(classScheduleModel.getMonthYear());
                itemHolder.time.setText(classScheduleModel.getTime());
                itemHolder.scheduleCell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mListener != null) {
                            mListener.onItemClick(classScheduleModel, position);
                        }
                        }
                    });


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
            void onItemClick(ClassScheduleModel item, int pos);
        }


        protected class MovieVH extends RecyclerView.ViewHolder {
            private TextView day;
            private TextView date;
            private TextView monthYear; // displays "year | language"
            private ImageView mPosterImg;
            private ProgressBar mProgress;
            private TextView time;
            private LinearLayout scheduleCell;
            CardView cardView;

            public MovieVH(View itemView) {
                super(itemView);
                day = itemView.findViewById(R.id.day);
                date = itemView.findViewById(R.id.date);
                monthYear = itemView.findViewById(R.id.monthYear);
                time = itemView.findViewById(R.id.time);
                scheduleCell = itemView.findViewById(R.id.scheduleCell);

            }
        }

    }