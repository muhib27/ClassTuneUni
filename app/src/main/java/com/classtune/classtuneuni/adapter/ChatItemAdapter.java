package com.classtune.classtuneuni.adapter;

//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.classtune.classtuneuni.R;
//import com.classtune.classtuneuni.model.Item;
//
//import java.util.List;
//
//public class ChatItemAdapter extends RecyclerView.Adapter<ChatItemAdapter.ItemViewHolder> {
//
//    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
//    private List<Item> itemList;
//    Context context;
//
//    public ChatItemAdapter(Context context, List<Item> itemList) {
//        this.itemList = itemList;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item, viewGroup, false);
//        return new ItemViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
//        Item item = itemList.get(i);
//        itemViewHolder.tvItemTitle.setText(item.getItemTitle());
//
//        // Create layout manager with initial prefetch item count
//        LinearLayoutManager layoutManager = new LinearLayoutManager(
//                itemViewHolder.rvSubItem.getContext(),
//                LinearLayoutManager.VERTICAL,
//                false
//        );
//        layoutManager.setInitialPrefetchItemCount(item.getSubItemList().size());
//
//        // Create sub item view adapter
//        SubItemAdapter subItemAdapter = new SubItemAdapter(item.getSubItemList());
//
//        itemViewHolder.rvSubItem.setLayoutManager(layoutManager);
//        itemViewHolder.rvSubItem.setAdapter(subItemAdapter);
//        itemViewHolder.rvSubItem.setRecycledViewPool(viewPool);
//    }
//
//    @Override
//    public int getItemCount() {
//        return itemList.size();
//    }
//
//    class ItemViewHolder extends RecyclerView.ViewHolder {
//        private TextView tvItemTitle;
//        private RecyclerView rvSubItem;
//
//        ItemViewHolder(View itemView) {
//            super(itemView);
//            tvItemTitle = itemView.findViewById(R.id.tv_item_title);
//            rvSubItem = itemView.findViewById(R.id.rv_sub_item);
//        }
//    }
//}

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.model.ComResult;
import com.classtune.classtuneuni.model.CourseModel;
import com.classtune.classtuneuni.model.Item;

import java.util.ArrayList;
import java.util.List;


public class ChatItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<Item> itemList;
    Context context;

    private ArrayList<CourseModel> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int HERO = 2;
    private static final int ITEM = 0;

    //    public ChatItemAdapter(Context context, ArrayList<CourseModel> values, ItemListener itemListener) {
//        mValues = values;
//        mContext = context;
//        mListener = itemListener;
//    }
    public ChatItemAdapter(Context context, List<Item> itemList) {
        this.itemList = itemList;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item, parent, false);
//        return new ViewHolder(view);
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.layout_item_own, parent, false);
                viewHolder = new ItemViewHolder(viewItem);
                break;

            case HERO:
                View viewHero = inflater.inflate(R.layout.layout_item_others, parent, false);
                viewHolder = new ItemViewHolderHero(viewHero);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        //final CourseModel result = mValues.get(position);
        Item item = itemList.get(position);
        switch (getItemViewType(position)) {
            case ITEM:

                final ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
                //itemViewHolder.tvItemTitle.setText(item.getItemTitle());

                // Create layout manager with initial prefetch item count
                LinearLayoutManager layoutManager = new LinearLayoutManager(
                        itemViewHolder.rvSubItem.getContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                );
                layoutManager.setInitialPrefetchItemCount(item.getSubItemList().size());

                // Create sub item view adapter
                ChatSubItemAdapter chatSubItemAdapter = new ChatSubItemAdapter(item.getSubItemList());

                itemViewHolder.rvSubItem.setLayoutManager(layoutManager);
                itemViewHolder.rvSubItem.setAdapter(chatSubItemAdapter);
                itemViewHolder.rvSubItem.setRecycledViewPool(viewPool);


                break;

            case HERO:
                final ItemViewHolderHero itemViewHolderHero = (ItemViewHolderHero) viewHolder;
                //itemViewHolderHero.tvItemTitle.setText(item.getItemTitle());

                // Create layout manager with initial prefetch item count
                LinearLayoutManager layoutManager1 = new LinearLayoutManager(
                        itemViewHolderHero.rvSubItem.getContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                );
                layoutManager1.setInitialPrefetchItemCount(item.getSubItemList().size());

                // Create sub item view adapter
                ChatSubItemAdapterOthers chatSubItemAdapterOthers = new ChatSubItemAdapterOthers(item.getSubItemList());

                itemViewHolderHero.rvSubItem.setLayoutManager(layoutManager1);
                itemViewHolderHero.rvSubItem.setAdapter(chatSubItemAdapterOthers);
                itemViewHolderHero.rvSubItem.setRecycledViewPool(viewPool);


                break;

        }
    }


    @Override
    public int getItemCount() {
        // return mValues.size();
        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
//        if (position == 0) {
//            return HERO;
//        } else
        if (position % 2 == 0)
            return HERO;
        else
            return ITEM;
    }

    public interface ItemListener {
        void onItemClick(CourseModel item, int pos);
    }

//
//    protected class MovieVH extends RecyclerView.ViewHolder {
//        private TextView subject;
//        private TextView gradePoint;
//        private TextView grade; // displays "year | language"
//        private ImageView mPosterImg;
//        private ProgressBar mProgress;
//        private TextView menuOption;
//        private LinearLayout cell;
//        CardView cardView;
//
//        public MovieVH(View itemView) {
//            super(itemView);
//
//            subject = itemView.findViewById(R.id.subject);
//            gradePoint = itemView.findViewById(R.id.gradePoint);
//            grade = itemView.findViewById(R.id.grade);
//            cell = itemView.findViewById(R.id.cell);
//
//        }
//    }
//
//    protected class HeroVH extends RecyclerView.ViewHolder {
//        private TextView title;
//        private TextView mMovieDesc;
//        private TextView mYear; // displays "year | language"
//        private ImageView mPosterImg;
//        private CardView cardView;
//
//        public HeroVH(View itemView) {
//            super(itemView);
//            title = itemView.findViewById(R.id.textView);
//        }
//    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemTitle;
        private RecyclerView rvSubItem;

        ItemViewHolder(View itemView) {
            super(itemView);
            //tvItemTitle = itemView.findViewById(R.id.tv_item_title);
            rvSubItem = itemView.findViewById(R.id.rv_sub_item);
        }
    }

    class ItemViewHolderHero extends RecyclerView.ViewHolder {
        private TextView tvItemTitle;
        private RecyclerView rvSubItem;

        ItemViewHolderHero(View itemView) {
            super(itemView);
            //tvItemTitle = itemView.findViewById(R.id.tv_item_title);
            rvSubItem = itemView.findViewById(R.id.rv_sub_item);
        }
    }
}
