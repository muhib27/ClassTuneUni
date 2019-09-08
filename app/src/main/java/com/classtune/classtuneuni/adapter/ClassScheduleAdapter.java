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
import com.classtune.classtuneuni.assignment.Assignment;
import com.classtune.classtuneuni.class_schedule.Routine;
import com.classtune.classtuneuni.home.StHomeFeed;
import com.classtune.classtuneuni.model.ClassScheduleModel;
import com.classtune.classtuneuni.utils.AppUtility;

import java.util.ArrayList;
import java.util.List;


public class ClassScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Routine> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int HERO = 2;
    private static final int ITEM = 0;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    public ClassScheduleAdapter(Context context) {
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
                View viewItem = inflater.inflate(R.layout.schedule_item_row, parent, false);
                viewHolder = new MovieVH(viewItem);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final Routine routine = mValues.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH itemHolder = (MovieVH) viewHolder;
                if(routine.getDay()!=null)
                itemHolder.day.setText(routine.getDay());
                if(routine.getDate()!=null)
                itemHolder.date.setText(routine.getDate());
                if(routine.getRoom()!=null)
                itemHolder.room.setText(routine.getRoom());
                if(routine.getEndTime()!=null && routine.getStartTime() != null)
                itemHolder.time.setText(getDuration(routine.getEndTime().substring(0, 5), routine.getStartTime().substring(0, 5)));
                if(routine.getMonth() != null && routine.getYear() !=null)
                itemHolder.monthYear.setText(AppUtility.getMonth(routine.getMonth()) + ", " + routine.getYear() );

                if(position == 0)
                {
                    itemHolder.ll.setBackgroundColor(mContext.getResources().getColor(R.color.appColor));
                    itemHolder.day.setTextColor(mContext.getResources().getColor(R.color.white));
                    itemHolder.date.setTextColor(mContext.getResources().getColor(R.color.white));
                    itemHolder.monthYear.setTextColor(mContext.getResources().getColor(R.color.white));
                }
//                itemHolder.scheduleCell.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (mListener != null) {
//                            mListener.onItemClick(classScheduleModel, position);
//                        }
//                        }
//                    });


                break;
        }
    }

    private String getYearMonth(String month, String year) {

       return "";
    }

    private String getDuration(String endTime, String startTime) {
        String time = "";
        time = getTime(startTime) +  " - " +getTime(endTime);
        return time;
    }
    private String getTime(String st){
        String time = "";
        if(st.length()>2) {

            if (Integer.parseInt(st.substring(0, 2)) >= 12) {
                time = st + "pm";
            } else {
                time = st + "am";
            }

        }
        return time;
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
        void onItemClick(ClassScheduleModel item, int pos);
    }


    protected class MovieVH extends RecyclerView.ViewHolder {
        private TextView room;
        private TextView day, date, time;
        private TextView monthYear; // displays "year | language"
        private ImageView mPosterImg;
        private ProgressBar mProgress;
        LinearLayout ll;
        private RelativeLayout scheduleCell;
        CardView cardView;

        public MovieVH(View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day);
            date = itemView.findViewById(R.id.date);
            monthYear = itemView.findViewById(R.id.monthYear);
            time = itemView.findViewById(R.id.time);
            room = itemView.findViewById(R.id.room);
            scheduleCell = itemView.findViewById(R.id.scheduleCell);
            ll = itemView.findViewById(R.id.ll);

        }
    }
    public void add(Routine r) {
        mValues.add(r);
        notifyItemInserted(mValues.size() - 1);
    }

    public void addAllData(List<Routine> moveResults) {
        for (Routine result : moveResults) {
            add(result);
        }
    }

    public void remove(Routine r) {
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
    public Routine getItem(int position) {
        return mValues.get(position);
    }
}