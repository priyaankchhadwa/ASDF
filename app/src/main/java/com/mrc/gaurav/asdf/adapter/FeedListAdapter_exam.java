package com.mrc.gaurav.asdf.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mrc.gaurav.asdf.R;
import com.mrc.gaurav.asdf.data.FeedItem;
import com.mrc.gaurav.asdf.data.FeedItem_exam;

import java.util.List;

public class FeedListAdapter_exam extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<FeedItem_exam> feedItems;

    public FeedListAdapter_exam(Activity activity, List<FeedItem_exam> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.feed_item_exam, null);

        TextView mScode = (TextView) convertView.findViewById(R.id.exam_scode);
        TextView mEdate = (TextView) convertView.findViewById(R.id.exam_edate);
        TextView mEtime = (TextView) convertView.findViewById(R.id.exam_etime);
        TextView mRoom = (TextView) convertView.findViewById(R.id.exam_room);

        final FeedItem_exam item = feedItems.get(position);

        mScode.setText(item.getScode());
        mEdate.setText(item.getEdate());
        mEtime.setText(item.getEtime());
        mRoom.setText(item.getRoom());

        return convertView;
    }
}
