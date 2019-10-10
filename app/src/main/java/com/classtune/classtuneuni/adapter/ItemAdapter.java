package com.classtune.classtuneuni.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.model.Item;
import com.classtune.classtuneuni.utils.AppUtility;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<Item> itemList;
    Context context;

    public ItemAdapter(Context context) {
//        this.itemList = itemList;
        itemList = new ArrayList<>();
        this.context = context;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        Item item = itemList.get(i);
        itemViewHolder.tvItemTitle.setText(AppUtility.getDateString(item.getItemTitle(), AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));

        // Create layout manager with initial prefetch item count
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                itemViewHolder.rvSubItem.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(item.getSubItemList().size());

        // Create sub item view adapter
        SubItemAdapter subItemAdapter = new SubItemAdapter(item.getSubItemList(), this.context);

        itemViewHolder.rvSubItem.setLayoutManager(layoutManager);
        itemViewHolder.rvSubItem.setAdapter(subItemAdapter);
        itemViewHolder.rvSubItem.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemTitle;
        private RecyclerView rvSubItem;

        ItemViewHolder(View itemView) {
            super(itemView);
            tvItemTitle = itemView.findViewById(R.id.tv_item_title);
            rvSubItem = itemView.findViewById(R.id.rv_sub_item);
        }
    }




    public void add(Item r) {
        itemList.add(r);
        notifyItemInserted(itemList.size() - 1);
    }

    public void addAllData(List<Item> moveResults) {
        for (Item result : moveResults) {
            add(result);
        }
    }
}
