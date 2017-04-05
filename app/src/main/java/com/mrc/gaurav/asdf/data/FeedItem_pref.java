package com.mrc.gaurav.asdf.data;

public class FeedItem_pref {

    private String pref_id, pref_date,pref_slot1_leave_pref,pref_slot1_yn,
            pref_slot2_leave_pref,pref_slot2_yn, count;

    public FeedItem_pref() {
    }

    public FeedItem_pref(String pref_id, String pref_date, String pref_slot2_leave_pref,
                         String pref_slot1_yn, String pref_slot2_yn, String pref_slot1_leave_pref, String count) {
        super();
        this.pref_id = pref_id;
        this.pref_date = pref_date;
        this.pref_slot1_leave_pref = pref_slot1_leave_pref;
        this.pref_slot2_leave_pref = pref_slot2_leave_pref;
        this.pref_slot1_yn = pref_slot1_yn;
        this.pref_slot2_yn = pref_slot2_yn;
        this.count = count;
    }

    public String getPref_id() {
        return pref_id;
    }

    public void setPref_id(String id) {
        this.pref_id = id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setPref_date(String date) {
        this.pref_date = date;
    }

    public String getPref_date() {
        return pref_date;
    }

    public void setPref_slot1_leave_pref(String pref_leave_pref) {
        this.pref_slot1_leave_pref = pref_leave_pref;
    }

    public String getPref_slot1_leave_pref() {
        return pref_slot1_leave_pref;
    }

    public void setPref_slot2_leave_pref(String pref_leave_pref) {
        this.pref_slot2_leave_pref = pref_leave_pref;
    }

    public String getPref_slot2_leave_pref() {
        return pref_slot2_leave_pref;
    }

}
