package com.mrc.gaurav.asdf.data;

public class FeedItem {

    private String notification_text, notification_date, username;

    public FeedItem() {
    }

    public FeedItem(String username) {
        super();
        this.username = username;
    }

    public String getNotificationDate() {
        return notification_date;
    }

    public void setNotificationDate(String date) {
        this.notification_date = date;
    }

    public String getNotificationText() {
        return notification_text;
    }

    public void setNotificationText(String notific) {
        this.notification_text = notific;
    }

}
