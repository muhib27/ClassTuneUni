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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.exam.Exam;
import com.classtune.classtuneuni.fragment.ExamDetailsFragment;
import com.classtune.classtuneuni.model.ExamInfoModel;
import com.classtune.classtuneuni.utils.AppUtility;
import com.classtune.classtuneuni.utils.PaginationAdapterCallback;

import java.util.ArrayList;
import java.util.List;


public class ExamPublishedListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    private List<Exam> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int HERO = 2;
    private static final int ITEM = 0;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private PaginationAdapterCallback mCallback;
    private String errorMsg;
    private String title = "";
    private int count = 0;

    public ExamPublishedListAdapter(Context context) {
        mValues = new ArrayList<>();
        mContext = context;

    }

    public ExamPublishedListAdapter(List<Exam> exams, Context mContext) {
        mValues = exams;
        this.mContext = mContext;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item, parent, false);
//        return new ViewHolder(view);
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.published_exam_item_row, parent, false);
                viewHolder = new MovieVH(viewItem);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final Exam exam = mValues.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH itemHolder = (MovieVH) viewHolder;

                if (exam != null) {
//                    itemHolder.examCell.setVisibility(View.VISIBLE);

//                    for (int i = 0; i < exam.getExams().size(); i++) {
//                        if(count == 0) {
//                            itemHolder.examType.setVisibility(View.VISIBLE);
//                            itemHolder.examType.setText(title);
//                        }
//                        else {
//                            itemHolder.examType.setVisibility(View.GONE);
//                        }

                    itemHolder.exam.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Fragment fragment = new ExamDetailsFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("id", exam.getId());
                            gotoFragment(fragment, "examDetailsFragment", bundle);
                        }
                    });


                        if (exam.getName() != null)
                            itemHolder.examName.setText(exam.getName());
                        if (exam.getMarks() != null)
                            itemHolder.marks.setText(exam.getMarks());
                        if (exam.getMaxMark() != null)
                            itemHolder.total.setText(exam.getMaxMark());
                    if (exam.getExamDate() != null && exam.getExamDate().contains("-")) {
                        String[] parts = exam.getExamDate().split("-");
                        if (parts.length >= 2)
                            itemHolder.examDate.setText(parts[2]);
                    }
                    if (exam.getExamDate() != null && exam.getExamDate().contains("-"))
                        itemHolder.examMY.setText(AppUtility.getDateString(exam.getExamDate(), AppUtility.DATE_FORMAT_APP_M_Y, AppUtility.DATE_FORMAT_SERVER));
                        count++;
                  //  }
                }
                else {
                    itemHolder.examCell.setVisibility(View.GONE);
                }
//                if(AppSharedPreference.getUserType().equals("3")) {
//                    itemHolder.praticipantText.setVisibility(View.INVISIBLE);
//                    if(examInfoModel.getObtainedMark()!=null)
//                        itemHolder.marks.setText(examInfoModel.getObtainedMark());
////                    if(examInfoModel.getExamMark()!=null)
////                        itemHolder.total.setText(examInfoModel.getExamMark());
//                    if(examInfoModel.getExamMark()!=null) {
//                        String mark = "";
//                        if(examInfoModel.getExamMark().contains("."))
//                        {
//                            String[] marks = examInfoModel.getExamMark().split("\\.");
//                            if(marks.length>0)
//                                mark = marks[0];
//                        }
//                        else
//                            mark = examInfoModel.getExamMark();
//                        itemHolder.total.setText("" + mark);
//                    }
//                    if(examInfoModel.getInstructor()!=null)
//                    itemHolder.name.setText(examInfoModel.getInstructor());
//                }
//                else {
//                    itemHolder.praticipantText.setVisibility(View.VISIBLE);
//                    if(examInfoModel.getParticipants()!=null)
//                        itemHolder.marks.setText(examInfoModel.getParticipants());
//                    if(examInfoModel.getTotalStudents()!=null)
//                        itemHolder.total.setText(examInfoModel.getTotalStudents());
//                    if(examInfoModel.getAssessmentName()!=null)
//                    itemHolder.name.setText(examInfoModel.getAssessmentName());
//                }
//
//                if(examInfoModel.getExamName()!=null)
//                    itemHolder.examName.setText(examInfoModel.getExamName());
//
//                itemHolder.subject.setText(examInfoModel.getCourseCode());
//                if(examInfoModel.getExamDate()!=null)
//                    itemHolder.date.setText(AppUtility.getDateString(examInfoModel.getExamDate(), AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));
//                itemHolder.examCell.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
////                        if (mListener != null) {
////                            mListener.onItemClick(examInfoModel, position);
//
//
////                        }
//                        if(AppSharedPreference.getUserType().equals("2"))
//                        {
//                            Fragment fragment =new TeacherResultEntryFragment();
//                            Bundle bundle = new Bundle();
//                            bundle.putString("examName", examInfoModel.getExamName());
//                            bundle.putString("courseCode", examInfoModel.getCourseCode());
//                            bundle.putString("assessmentName", examInfoModel.getAssessmentName());
//                            bundle.putString("examDate", examInfoModel.getExamDate());
//                            bundle.putString("participant", examInfoModel.getParticipants());
//                            bundle.putString("total", examInfoModel.getTotalStudents());
//                            gotoFragment(fragment, "reacherResultEntryFragment", bundle);
//                        }
//                        else {
//                            Fragment fragment = new ExamDetailsFragment();
//                            Bundle bundle = new Bundle();
//                            bundle.putString("id", examInfoModel.getExamId());
//                            gotoFragment(fragment, "examDetailsFragment", bundle);
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
        void onItemClick(ExamInfoModel item, int pos);
    }


    protected class MovieVH extends RecyclerView.ViewHolder {
        private TextView subject;
        private TextView name, examDate, examMY;
        private TextView marks, total, date; // displays "year | language"
        private ImageView mPosterImg;
        private ProgressBar mProgress;
        private TextView examName, praticipantText, examType;
        private RelativeLayout examCell;
        CardView exam;

        public MovieVH(View itemView) {
            super(itemView);
//            examType = itemView.findViewById(R.id.examType);
            examName = itemView.findViewById(R.id.examName);
//            subject = itemView.findViewById(R.id.subject);
            name = itemView.findViewById(R.id.name);
            total = itemView.findViewById(R.id.total);
            marks = itemView.findViewById(R.id.marks);
            examCell = itemView.findViewById(R.id.examCell);
            examDate = itemView.findViewById(R.id.examDate);
            examMY = itemView.findViewById(R.id.examMY);
            exam = itemView.findViewById(R.id.exam);
//            date = itemView.findViewById(R.id.examDate);
//            praticipantText = itemView.findViewById(R.id.participantText);

        }
    }


    public void add(Exam r) {
        mValues.add(r);
        notifyItemInserted(mValues.size() - 1);
    }

    public void addAllData(List<Exam> moveResults) {
       // count = 0;
       // this.title = title;
        for (Exam result : moveResults) {
            add(result);
        }
    }

    public void remove(Exam r) {
        int position = mValues.indexOf(r);
        if (position > -1) {
            mValues.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Exam());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mValues.size() - 1;
        Exam result = getItem(position);

        if (result != null) {
            mValues.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Exam getItem(int position) {
        return mValues.get(position);
    }

    private void gotoFragment(Fragment fragment, String tag, Bundle bundle) {
        // load fragment

        fragment.setArguments(bundle);
        FragmentTransaction transaction = ((MainActivity) mContext).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}