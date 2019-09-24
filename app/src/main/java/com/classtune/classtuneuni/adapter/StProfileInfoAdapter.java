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
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.assignment.Assignment;
import com.classtune.classtuneuni.model.ComResult;
import com.classtune.classtuneuni.model.ProfileCourseModel;
import com.classtune.classtuneuni.profile.StCourseAssessment;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class StProfileInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<StCourseAssessment> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int HERO = 2;
    private static final int ITEM = 0;


    public StProfileInfoAdapter(Context context) {
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
                View viewItem = inflater.inflate(R.layout.st_profile_item_row, parent, false);
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
        final StCourseAssessment result = mValues.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH itemHolder = (MovieVH) viewHolder;
                if(result.getCourseCode()!=null)
                itemHolder.subject.setText(result.getCourseCode());
                if(result.getAttendance()!=null)
                itemHolder.attendance.setText(""+result.getAttendance());
                if(result.getObtained()!=null && result.getObtained().getGrade()!=null)
                itemHolder.grade.setText(result.getObtained().getGrade());
//                itemHolder.cell.setOnClickListener(new View.OnClickListener() {
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
            void onItemClick(ProfileCourseModel item, int pos);
        }


        protected class MovieVH extends RecyclerView.ViewHolder {
            private TextView subject;
            private TextView attendance;
            private TextView grade; // displays "year | language"
            private ImageView mPosterImg;
            private ProgressBar mProgress;
            private TextView menuOption;
            private LinearLayout cell;
            CardView cardView;

            public MovieVH(View itemView) {
                super(itemView);

                subject = itemView.findViewById(R.id.subject);
                attendance = itemView.findViewById(R.id.attendance);
                grade = itemView.findViewById(R.id.grade);
                cell = itemView.findViewById(R.id.cell);

            }
        }

    public void add(StCourseAssessment r) {
        mValues.add(r);
        notifyItemInserted(mValues.size() - 1);
    }

    public void addAllData(List<StCourseAssessment> moveResults) {
        for (StCourseAssessment result : moveResults) {
            add(result);
        }
    }
    }