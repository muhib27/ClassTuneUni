package com.classtune.classtuneuni.adapter;

import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.course_resonse.Course;
import com.classtune.classtuneuni.fragment.CourseOfferFragment;
import com.classtune.classtuneuni.fragment.OfferedCourseDetailsFragment;
import com.classtune.classtuneuni.model.CourseModel;

import java.util.ArrayList;
import java.util.List;


public class CourseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Course> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int HERO = 2;
    private static final int ITEM = 0;

    public CourseListAdapter(Context context, ItemListener itemListener) {
//        mValues = values;
        mContext = context;
        mListener = itemListener;
        mValues = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item, parent, false);
//        return new ViewHolder(view);
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.course_list_item_row, parent, false);
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
        final Course result = mValues.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH itemHolder = (MovieVH) viewHolder;
                itemHolder.subject.setText(result.getName());
                itemHolder.gradePoint.setText(result.getCourseCode());
                itemHolder.grade.setText(result.getCreditPoint());
                if (!result.getCourseOffered())
                    itemHolder.cardView.setVisibility(View.VISIBLE);
                else
                    itemHolder.cardView.setVisibility(View.INVISIBLE);
                itemHolder.cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        if (result.getCourseOffered()) {
//                            Fragment fragment = new OfferedCourseDetailsFragment();
//                            gotoFragment(fragment, "offeredCourseDetailsFragment", result.getId());
//                        }
//                        else
                            {
                            Fragment fragment = new CourseOfferFragment();
                            gotoFragment(fragment, "courseOfferFragment", result.getId());
                        }
                    }

                });


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
            void onItemClick(CourseModel item, int pos);
        }


        protected class MovieVH extends RecyclerView.ViewHolder {
            private TextView subject;
            private TextView gradePoint;
            private TextView grade; // displays "year | language"
            private ImageView mPosterImg;
            private ProgressBar mProgress;
            private TextView menuOption;
            private LinearLayout cell;
            CardView cardView;

            public MovieVH(View itemView) {
                super(itemView);

                subject = itemView.findViewById(R.id.courseName);
                gradePoint = itemView.findViewById(R.id.courseCode);
                grade = itemView.findViewById(R.id.credit);
                cell = itemView.findViewById(R.id.cell);
                cardView = itemView.findViewById(R.id.cardView);

            }
        }

        protected class HeroVH extends RecyclerView.ViewHolder {
            private TextView title;
            private TextView mMovieDesc;
            private TextView mYear; // displays "year | language"
            private ImageView mPosterImg;
            private CardView cardView;

            public HeroVH(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.textView);
            }
        }

    public void add(Course r) {
        mValues.add(r);
        notifyItemInserted(mValues.size() - 1);
    }

    public void addAllData(List<Course> moveResults) {
        for (Course result : moveResults) {
            add(result);
        }
    }

    private void gotoFragment(Fragment fragment, String tag, String courseId) {
        // load fragment
        Bundle bundle = new Bundle();
        bundle.putString("courseId", courseId);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        //transaction.addToBackStack(null);
        transaction.commit();
    }
}