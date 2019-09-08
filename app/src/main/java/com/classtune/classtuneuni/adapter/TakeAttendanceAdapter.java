package com.classtune.classtuneuni.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
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
import com.classtune.classtuneuni.fragment.TakeAttendanceFragment;
import com.classtune.classtuneuni.model.STAttendanceModel;
import com.classtune.classtuneuni.model.Student;

import java.util.ArrayList;
import java.util.List;


public class TakeAttendanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<Student> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int HERO = 2;
    private static final int ITEM = 0;

    private boolean isLoadingAdded = false;

    public TakeAttendanceAdapter(Context context) {
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
                View viewItem = inflater.inflate(R.layout.take_attendance_item_row, parent, false);
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
        final Student student = mValues.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH itemHolder = (MovieVH) viewHolder;
                itemHolder.rollNumberText.setText(student.getStudentId());
                itemHolder.nameText.setText(student.getStudentName());
                final int sdk = android.os.Build.VERSION.SDK_INT;

//                int i = student.getAbsent();
                if(student.getAbsent() != null && student.getAbsent().equals("1"))
                {
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        itemHolder.statusLl.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.absent_bg) );
                    } else {
                        itemHolder.statusLl.setBackground(ContextCompat.getDrawable(mContext, R.drawable.absent_bg));
                    }
                    itemHolder.status.setText("A");
                    student.setStatus("0");

                }
                else
                {
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        itemHolder.statusLl.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.present_bg) );
                    } else {
                        itemHolder.statusLl.setBackground(ContextCompat.getDrawable(mContext, R.drawable.present_bg));
                    }
                    itemHolder.status.setText("P");
                    student.setStatus("1");
                }
//                itemHolder.rightImg.setText(stAttendanceModel.getObtained());
                itemHolder.statusLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        if (mListener != null) {
//                            mListener.onItemClick(result, position);
//                        }
                        if(itemHolder.status.getText().toString().equals("P"))
                        {
                            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                itemHolder.statusLl.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.absent_bg) );
                            } else {
                                itemHolder.statusLl.setBackground(ContextCompat.getDrawable(mContext, R.drawable.absent_bg));
                            }
                            itemHolder.status.setText("A");
                            student.setStatus("0");


                        }
                        else {
                            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                itemHolder.statusLl.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.present_bg) );
                            } else {
                                itemHolder.statusLl.setBackground(ContextCompat.getDrawable(mContext, R.drawable.present_bg));
                            }
                            itemHolder.status.setText("P");
                            student.setStatus("1");
                        }
                        }
                    });


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
        void onItemClick(STAttendanceModel item, int pos);
    }


    protected class MovieVH extends RecyclerView.ViewHolder {
        private TextView rollNumberText;
        private TextView nameText;
        private TextView status; // displays "year | language"
        private ImageView rightImg;
        private ProgressBar mProgress;
        private TextView menuOption;
        private FrameLayout itemLayout;
        private LinearLayout statusLl;
        CardView cardView;

        public MovieVH(View itemView) {
            super(itemView);

            rollNumberText = itemView.findViewById(R.id.studentId);
            nameText = itemView.findViewById(R.id.name_text);
            status = itemView.findViewById(R.id.status);
            statusLl = itemView.findViewById(R.id.statusLl);
           // rightImg = itemView.findViewById(R.id.right_img);

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

    public void add(Student r) {
        mValues.add(r);
        notifyItemInserted(mValues.size() - 1);
    }

    public void addAllData(List<Student> moveResults) {
        for (Student result : moveResults) {
            add(result);
        }
    }
    public void remove(Student r) {
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
    public Student getItem(int position) {
        return mValues.get(position);
    }
}