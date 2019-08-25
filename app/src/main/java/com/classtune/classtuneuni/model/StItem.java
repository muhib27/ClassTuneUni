package com.classtune.classtuneuni.model;

import com.classtune.classtuneuni.response.Notice;
import com.classtune.classtuneuni.response.NoticeInfo;

import java.util.List;

public class StItem {
    private String itemTitle;
    private List<NoticeInfo> subItemList;

    public StItem(String itemTitle, List<NoticeInfo> subItemList) {
        this.itemTitle = itemTitle;
        this.subItemList = subItemList;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public List<NoticeInfo> getSubItemList() {
        return subItemList;
    }

    public void setSubItemList(List<NoticeInfo> subItemList) {
        this.subItemList = subItemList;
    }
}
