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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.model.OptionModel;
import com.classtune.classtuneuni.model.STAttendanceModel;
import com.classtune.classtuneuni.model.Student;

import java.util.ArrayList;
import java.util.List;


public class AnsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<OptionModel> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int HERO = 2;
    private static final int ITEM = 0;

    private boolean isLoadingAdded = false;

    public AnsAdapter(Context context) {
        mValues = new ArrayList<>();
        mContext = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.ans_item_row, parent, false);
                viewHolder = new Answer(viewItem);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final OptionModel optionModel = mValues.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final Answer itemHolder = (Answer) viewHolder;
                itemHolder.si.setText(optionModel.getSi());
                itemHolder.option.setText(optionModel.getOpt());
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    itemHolder.statusLl.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.square_border_ash) );
                    itemHolder.option.setTextColor(mContext.getResources().getColor(R.color.black));
                    itemHolder.si.setTextColor(mContext.getResources().getColor(R.color.ans_select));
                } else {
                    itemHolder.statusLl.setBackground(ContextCompat.getDrawable(mContext, R.drawable.square_border_ash));
                    itemHolder.option.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    itemHolder.si.setTextColor(ContextCompat.getColor(mContext, R.color.ans_select));
                }
                itemHolder.statusLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        if (mListener != null) {
//                            mListener.onItemClick(result, position);
//                        }
                        if(optionModel.getStatus().equals("0"))
                        {
                            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                itemHolder.statusLl.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.answer_selected) );
                                itemHolder.option.setTextColor(mContext.getResources().getColor(R.color.white));
                                itemHolder.si.setTextColor(mContext.getResources().getColor(R.color.white));
                            } else {
                                itemHolder.statusLl.setBackground(ContextCompat.getDrawable(mContext, R.drawable.answer_selected));
                                itemHolder.option.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                                itemHolder.si.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                            }
                            optionModel.setStatus("1");



                        }
                        else {
                            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                itemHolder.statusLl.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.square_border_ash) );
                                itemHolder.option.setTextColor(mContext.getResources().getColor(R.color.black));
                                itemHolder.si.setTextColor(mContext.getResources().getColor(R.color.ans_select));
                            } else {
                                itemHolder.statusLl.setBackground(ContextCompat.getDrawable(mContext, R.drawable.square_border_ash));
                                itemHolder.option.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                                itemHolder.si.setTextColor(ContextCompat.getColor(mContext, R.color.ans_select));
                            }

                            optionModel.setStatus("0");
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


    protected class Answer extends RecyclerView.ViewHolder {
        private TextView si;
        private TextView option;
        private TextView status; // displays "year | language"
        private ImageView rightImg;
        private ProgressBar mProgress;
        private TextView menuOption;
        private FrameLayout itemLayout;
        private RelativeLayout statusLl;
        CardView cardView;

        public Answer(View itemView) {
            super(itemView);

            si = itemView.findViewById(R.id.si);
            option = itemView.findViewById(R.id.option);
            statusLl = itemView.findViewById(R.id.rl);

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

    public void add(OptionModel r) {
        mValues.add(r);
        notifyItemInserted(mValues.size() - 1);
    }

    public void addAllData(List<OptionModel> moveResults) {
        for (OptionModel result : moveResults) {
            add(result);
        }
    }
    public void remove(OptionModel r) {
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
    public OptionModel getItem(int position) {
        return mValues.get(position);
    }
}