package com.classtune.classtuneuni.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.model.STAttendanceModel;
import com.classtune.classtuneuni.model.SubjectResultModel;

import java.util.ArrayList;


public class TakeAttendanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<STAttendanceModel> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int HERO = 2;
    private static final int ITEM = 0;

    public TakeAttendanceAdapter(Context context, ArrayList<STAttendanceModel> values, ItemListener itemListener) {
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
        final STAttendanceModel stAttendanceModel = mValues.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH itemHolder = (MovieVH) viewHolder;
                itemHolder.rollNumberText.setText(stAttendanceModel.getRoll());
                itemHolder.nameText.setText(stAttendanceModel.getName());
//                itemHolder.rightImg.setText(stAttendanceModel.getObtained());
//                itemHolder.cardView.setOnClickListener(new View.OnClickListener() {
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
            void onItemClick(STAttendanceModel item, int pos);
        }


        protected class MovieVH extends RecyclerView.ViewHolder {
            private TextView rollNumberText;
            private TextView nameText;
            private TextView grade; // displays "year | language"
            private ImageView rightImg;
            private ProgressBar mProgress;
            private TextView menuOption;
            private FrameLayout itemLayout;
            CardView cardView;

            public MovieVH(View itemView) {
                super(itemView);

                rollNumberText = itemView.findViewById(R.id.roll_number_text);
                nameText = itemView.findViewById(R.id.name_text);
                rightImg = itemView.findViewById(R.id.right_img);

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
    }