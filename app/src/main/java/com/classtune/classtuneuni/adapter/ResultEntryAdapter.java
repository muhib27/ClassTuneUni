package com.classtune.classtuneuni.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.model.ResultEntryModel;

import java.util.ArrayList;


public class ResultEntryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ResultEntryModel> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int HERO = 2;
    private static final int ITEM = 0;

    public ResultEntryAdapter(Context context) {
        mContext = context;
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
                View viewItem = inflater.inflate(R.layout.result_entry_item_row, parent, false);
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
        final ResultEntryModel result = mValues.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH itemHolder = (MovieVH) viewHolder;
                itemHolder.roll.setText(result.getRoll());
                itemHolder.name.setText(result.getName());
                itemHolder.marks.setText(result.getMarks());
                itemHolder.cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mListener != null) {
                            mListener.onItemClick(result, position);
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
        void onItemClick(ResultEntryModel item, int pos);
    }


    protected class MovieVH extends RecyclerView.ViewHolder {
        private TextView roll;
        private TextView name;
        private TextView grade; // displays "year | language"
        private ImageView mPosterImg;
        private ProgressBar mProgress;
        private TextView menuOption;
        private RelativeLayout cell;
        CardView cardView;
        private EditText marks;

        public MovieVH(View itemView) {
            super(itemView);

            roll = itemView.findViewById(R.id.roll);
            name = itemView.findViewById(R.id.name);
            marks = itemView.findViewById(R.id.marks);
            cell = itemView.findViewById(R.id.cell);

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

//    public void add(ExamInfoModel r) {
//        mValues.add(r);
//        notifyItemInserted(mValues.size() - 1);
//    }
//
//    public void addAllData(List<ExamInfoModel> moveResults) {
//        for (ExamInfoModel result : moveResults) {
//            add(result);
//        }
//    }
}