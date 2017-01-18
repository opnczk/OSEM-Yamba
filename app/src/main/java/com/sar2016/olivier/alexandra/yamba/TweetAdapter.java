package com.sar2016.olivier.alexandra.yamba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import winterwell.jtwitter.Twitter;

/**
 * Created by olivier on 16/01/17.
 */

public class TweetAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Tweet> mDataSource;

    public TweetAdapter(Context context, List<Tweet> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.tweet, parent, false);

        TextView tweetTextView =
                (TextView) rowView.findViewById(R.id.tweet_text);

        TextView userTextView =
                (TextView) rowView.findViewById(R.id.tweet_user);

        Tweet tweet = (Tweet) getItem(position);

        tweetTextView.setText(tweet.getTxt());
        userTextView.setText(tweet.getUser());

        return rowView;
    }
}
