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

import java.util.List;

public class FeedListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<FeedItem> feedItems;

    public FeedListAdapter(Activity activity, List<FeedItem> feedItems) {
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
            convertView = inflater.inflate(R.layout.feed_item, null);

        TextView notification_date = (TextView) convertView.findViewById(R.id.ndate);
        TextView notification_text = (TextView) convertView.findViewById(R.id.ntext);

        final FeedItem item = feedItems.get(position);

        notification_text.setText(item.getNotificationText());
        notification_date.setText(item.getNotificationDate());

        return convertView;
    }

}
