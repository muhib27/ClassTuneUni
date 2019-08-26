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
import com.classtune.classtuneuni.model.ExamInfoModel;
import com.classtune.classtuneuni.utils.AppUtility;

import java.util.ArrayList;
import java.util.List;


public class ExamListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ExamInfoModel> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int HERO = 2;
    private static final int ITEM = 0;

    public ExamListAdapter(Context context) {
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
                View viewItem = inflater.inflate(R.layout.exam_item_row, parent, false);
                viewHolder = new MovieVH(viewItem);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final ExamInfoModel examInfoModel = mValues.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH itemHolder = (MovieVH) viewHolder;
                itemHolder.examName.setText(examInfoModel.getExamName());
                itemHolder.subject.setText(examInfoModel.getCourseCode());
                itemHolder.name.setText(examInfoModel.getInstructor());
                if(examInfoModel.getObtainedMark()!=null)
                itemHolder.marks.setText(examInfoModel.getObtainedMark());
                if(examInfoModel.getExamMark()!=null)
                itemHolder.total.setText(examInfoModel.getExamMark());
                if(examInfoModel.getExamDate()!=null)
                    itemHolder.date.setText(AppUtility.getDateString(examInfoModel.getExamDate(), AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));
                itemHolder.examCell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        if (mListener != null) {
//                            mListener.onItemClick(examInfoModel, position);
//                        }
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
            void onItemClick(ExamInfoModel item, int pos);
        }


        protected class MovieVH extends RecyclerView.ViewHolder {
            private TextView subject;
            private TextView name;
            private TextView marks, total, date; // displays "year | language"
            private ImageView mPosterImg;
            private ProgressBar mProgress;
            private TextView examName;
            private RelativeLayout examCell;
            CardView cardView;

            public MovieVH(View itemView) {
                super(itemView);
                examName = itemView.findViewById(R.id.examName);
                subject = itemView.findViewById(R.id.subject);
                name = itemView.findViewById(R.id.name);
                total = itemView.findViewById(R.id.total);
                marks = itemView.findViewById(R.id.marks);
                examCell = itemView.findViewById(R.id.examCell);
                date = itemView.findViewById(R.id.examDate);

            }
        }

    public void add(ExamInfoModel r) {
        mValues.add(r);
        notifyItemInserted(mValues.size() - 1);
    }

    public void addAllData(List<ExamInfoModel> moveResults) {
        for (ExamInfoModel result : moveResults) {
            add(result);
        }
    }
    }