package com.classtune.classtuneuni.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.assignment.Participant;
import com.classtune.classtuneuni.attendance.StAttendanceData;
import com.classtune.classtuneuni.model.ExamInfoModel;
import com.classtune.classtuneuni.model.Student;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class StudentReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<StAttendanceData> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int HERO = 2;
    private static final int ITEM = 0;


    public StudentReportAdapter(Context context) {
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
                View viewItem = inflater.inflate(R.layout.student_report_item_row, parent, false);
                viewHolder = new MovieVH(viewItem);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final StAttendanceData stAttendanceData = mValues.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH itemHolder = (MovieVH) viewHolder;
                if(stAttendanceData.getName()!=null)
                itemHolder.name.setText(stAttendanceData.getName());
                if(stAttendanceData.getStudentId()!=null)
                itemHolder.studentId.setText(stAttendanceData.getStudentId());
                if(stAttendanceData.getPresent()!=null)
                    itemHolder.parcent.setText(""+ stAttendanceData.getPresent());

//                itemHolder.cell.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                });
                break;
        }
    }


    @Override
    public int getItemCount() {
        return mValues == null ? 0 : mValues.size();
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
        private TextView name;
        private TextView studentId;
        private TextView dayCount;
        private TextView dayText;
        private TextView parcent;
        private LinearLayout cell, viewLl, deleteLl;
        CircleImageView imageView;



        public MovieVH(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            studentId = itemView.findViewById(R.id.studentId);
            dayText = itemView.findViewById(R.id.dayText);
            dayCount = itemView.findViewById(R.id.dayCount);
            parcent = itemView.findViewById(R.id.parcent);
            imageView = itemView.findViewById(R.id.image);
//            cell = itemView.findViewById(R.id.cell);
//            viewLl = itemView.findViewById(R.id.viewLl);


        }
    }

    public void add(StAttendanceData r) {
        mValues.add(r);
        notifyItemInserted(mValues.size() - 1);
    }

    public void addAllData(List<StAttendanceData> moveResults) {
        for (StAttendanceData result : moveResults) {
            add(result);
        }
    }

    public void remove(StAttendanceData r) {
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
    public StAttendanceData getItem(int position) {
        return mValues.get(position);
    }

}