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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.message.StCourseDiscussion;
import com.classtune.classtuneuni.model.AssignmentModel;
import com.classtune.classtuneuni.utils.AppSharedPreference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class StMsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<StCourseDiscussion> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int HERO = 2;
    private static final int ITEM = 0;

    private static String PREVIOUS_ID = "";
    private static  String CURRENT_ID = "";

    public StMsgAdapter(Context context) {
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
                View viewItem = inflater.inflate(R.layout.layout_item_others, parent, false);
                viewHolder = new MovieVH(viewItem);
                break;

            case HERO:
                View viewHero = inflater.inflate(R.layout.layout_item_own, parent, false);
                viewHolder = new HeroVH(viewHero);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final StCourseDiscussion result = mValues.get(position);
        switch (getItemViewType(position)) {

            case ITEM:
                final MovieVH itemHolder = (MovieVH) viewHolder;
                if(position<=0)
                    PREVIOUS_ID = result.getSenderId();
                else
                    PREVIOUS_ID = mValues.get(position-1).getSenderId();
                CURRENT_ID = result.getSenderId();
                itemHolder.title.setText(result.getContent());
                itemHolder.name.setText(result.getSenderName());

                if(position>0 && PREVIOUS_ID.equals(CURRENT_ID)) {
                    itemHolder.pic.setVisibility(View.INVISIBLE);
                    itemHolder.name.setVisibility(View.GONE);
                }


//                String str = "";
//                if (AppSharedPreference.getUserType().equals("3")) {
//                    str = result.getAssignment().getCreatedAt();
//
//                    String parts[] = str.split(" ");
//
//                    itemHolder.assignedDate.setText(AppUtility.getDateString(parts[0], AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));
//                    if (result.getAssignment().getDueDate() != null)
//                        itemHolder.dueDate.setText(AppUtility.getDateString(result.getAssignment().getDueDate(), AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));
//                    if (result.getAssignment().getCourseCode() != null)
//                        itemHolder.subject.setText(result.getAssignment().getCourseCode());
//                    if (result.getAssignment().getSectionName() != null)
//                        itemHolder.section.setText(result.getAssignment().getSectionName());
//                    if (result.getSubmission() != null && result.getSubmission()== 0) {
//                        itemHolder.imgLl.setVisibility(View.VISIBLE);
//                        itemHolder.numLl.setVisibility(View.GONE);
//                        if (result.getMark() != null)
//                            itemHolder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.not_examined));
//                        else
//                            itemHolder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.not_submited));
//                    } else {
//                        itemHolder.present.setText("" + result.getSubmission());
//                        if (result.getMark() != null)
//                            itemHolder.total.setText("" + result.getMark());
//                    }
//
//                    itemHolder.cardView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Fragment fragment = new AssignmentDetailsFragment();
//                            gotoFragment(fragment, "assignmentDetailsFragment", result.getAssignment().getId());
//
////                        if (mListener != null) {
////                            mListener.onItemClick(result, position);
////                        }
//                        }
//                    });
//                } else {
//                    str = result.getAssignment().getAssignDate();
//
//                    String parts[] = str.split(" ");
//
//                    itemHolder.assignedDate.setText(AppUtility.getDateString(parts[0], AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));
//                    if (result.getAssignment().getDueDate() != null)
//                        itemHolder.dueDate.setText(AppUtility.getDateString(result.getAssignment().getDueDate(), AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));
//                    if (result.getAssignment().getCourseCode() != null)
//                        itemHolder.subject.setText(result.getAssignment().getCourseCode());
//                    if (result.getAssignment().getSectionName() != null)
//                        itemHolder.section.setText(result.getAssignment().getSectionName());
//                    if (result.getSubmissions() != null)
//                        itemHolder.present.setText("" + result.getSubmissions());
//                    if (result.getStudents() != null)
//                        itemHolder.total.setText("" + result.getStudents());
//                    itemHolder.cardView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Fragment fragment = new TeacherStudentListFragment();
//                            gotoFragment(fragment, "teacherStudentListFragment", result.getAssignment().getId());
//
////                        if (mListener != null) {
////                            mListener.onItemClick(result, position);
////                        }
//                        }
//                    });
//                }


                break;
            case HERO:
                final HeroVH heroHolder = (HeroVH) viewHolder;
                if(position<=0)
                    PREVIOUS_ID = result.getSenderId();
                else
                    PREVIOUS_ID = mValues.get(position-1).getSenderId();
                CURRENT_ID = result.getSenderId();
                heroHolder.title.setText(result.getContent());
                if(position>0 && PREVIOUS_ID.equals(CURRENT_ID))
                    heroHolder.pic.setVisibility(View.INVISIBLE);
                break;
        }
    }

//    @Override
//    public void onBindViewHolder(ViewHolder viewHolder, int position) {
//        viewHolder.setData(mValues.get(position));
//    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public int getItemViewType(int position) {
//        if (position == 0) {
//            return HERO;
//        } else
        if (mValues.get(position).getSenderId().equals(AppSharedPreference.getUserId()))
            return HERO;
        else
            return ITEM;
    }

    public interface ItemListener {
        void onItemClick(AssignmentModel item, int pos);
    }


    protected class MovieVH extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView name;
        private TextView dueDate; // displays "year | language"
        private ImageView img;
        private ProgressBar mProgress;
        private TextView subject, section, present, total;
        private FrameLayout itemLayout;
        CardView cardView;
        CircleImageView pic;
        LinearLayout numLl, imgLl;

        public MovieVH(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.text);
            pic = itemView.findViewById(R.id.pic);
            name = itemView.findViewById(R.id.name);
//            assignedDate = itemView.findViewById(R.id.assignedDate);
//            dueDate = itemView.findViewById(R.id.dueDate);
//            subject = itemView.findViewById(R.id.subject);
//            section = itemView.findViewById(R.id.section);
//            present = itemView.findViewById(R.id.present);
//            total = itemView.findViewById(R.id.total);
//            img = itemView.findViewById(R.id.img);
////            assignedDate = itemView.findViewById(R.id.assignedDate);
//            cardView = itemView.findViewById(R.id.cardView);
//            imgLl = itemView.findViewById(R.id.imgLl);
//            numLl = itemView.findViewById(R.id.numLl);

        }
    }

    protected class HeroVH extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView mMovieDesc;
        private TextView mYear; // displays "year | language"
        private ImageView mPosterImg;
        private CardView cardView;
        CircleImageView pic;

        public HeroVH(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text);
            pic = itemView.findViewById(R.id.pic);
        }
    }

    public void add(StCourseDiscussion r) {
        mValues.add(r);
        notifyItemInserted(mValues.size() - 1);
    }

    public void addAllData(List<StCourseDiscussion> moveResults) {
        for (StCourseDiscussion result : moveResults) {
            add(result);
        }
    }

    private void gotoFragment(Fragment fragment, String tag, String assignmentId) {
        // load fragment
        Bundle bundle = new Bundle();
        bundle.putString("assignmentId", assignmentId);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        //transaction.addToBackStack(null);
        transaction.commit();
    }
}