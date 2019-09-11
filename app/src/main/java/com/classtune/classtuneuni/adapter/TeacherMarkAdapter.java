package com.classtune.classtuneuni.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.model.ExamInfoModel;
import com.classtune.classtuneuni.model.Student;

import java.util.ArrayList;
import java.util.List;


public class TeacherMarkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Student> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int HERO = 2;
    private static final int ITEM = 0;

    public TeacherMarkAdapter(Context context) {
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
                View viewItem = inflater.inflate(R.layout.course_student_section_item_row, parent, false);
                viewHolder = new MovieVH(viewItem);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final Student student = mValues.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH itemHolder = (MovieVH) viewHolder;
                itemHolder.name.setText(student.getStudentName());
                itemHolder.studentId.setText(student.getStudentId());
                itemHolder.cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
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
        private TextView view;
        private ImageView delete;
        private LinearLayout cell, viewLl, deleteLl;


        public MovieVH(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            studentId = itemView.findViewById(R.id.studentId);
            view = itemView.findViewById(R.id.view);
            cell = itemView.findViewById(R.id.cell);
            viewLl = itemView.findViewById(R.id.viewLl);
            deleteLl = itemView.findViewById(R.id.deleteLl);


        }
    }

    public void add(Student r) {
        mValues.add(r);
        notifyItemInserted(mValues.size() - 1);
    }

    public void addAllData(List<Student> moveResults) {
        for (Student result : moveResults) {
            add(result);
        }
    }

}