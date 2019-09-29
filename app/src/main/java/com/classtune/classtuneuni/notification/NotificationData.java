package com.classtune.classtuneuni.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationData {
    @SerializedName("notifications")
    @Expose
    private List<Notification> notifications = null;

    @SerializedName("count")
    @Expose
    private String count;

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
