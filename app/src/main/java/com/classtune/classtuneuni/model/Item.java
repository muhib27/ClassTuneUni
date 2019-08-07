package com.classtune.classtuneuni.model;

import com.classtune.classtuneuni.response.Notice;
import com.classtune.classtuneuni.response.NoticeInfo;

import java.util.List;

public class Item {
    private String itemTitle;
    private List<Notice> subItemList;

    public Item(String itemTitle, List<Notice> subItemList) {
        this.itemTitle = itemTitle;
        this.subItemList = subItemList;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public List<Notice> getSubItemList() {
        return subItemList;
    }

    public void setSubItemList(List<Notice> subItemList) {
        this.subItemList = subItemList;
    }
}
