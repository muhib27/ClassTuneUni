package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.adapter.ChatItemAdapter;
import com.classtune.classtuneuni.adapter.ItemAdapter;
import com.classtune.classtuneuni.model.Item;
import com.classtune.classtuneuni.model.SubItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatDetailsFragment extends Fragment {


    public ChatDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_details, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvItem = view.findViewById(R.id.rv_item);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        ChatItemAdapter chatItemAdapter = new ChatItemAdapter(getActivity(),buildItemList());
        rvItem.setAdapter(chatItemAdapter);
        rvItem.setLayoutManager(layoutManager);
    }

    private List<Item> buildItemList() {
        List<Item> itemList = new ArrayList<>();
        for (int i=0; i<10; i++) {
            Item item = new Item("Item "+i, buildSubItemList());
            itemList.add(item);
        }
        return itemList;
    }

    private List<SubItem> buildSubItemList() {
        List<SubItem> subItemList = new ArrayList<>();
        for (int i=0; i<3; i++) {
            SubItem subItem = new SubItem("Sub Item "+i, "Description "+i);
            subItemList.add(subItem);
        }
        return subItemList;
    }

}
