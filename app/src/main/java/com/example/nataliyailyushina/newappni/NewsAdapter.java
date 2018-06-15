package com.example.nataliyailyushina.newappni;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {


    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        // Find the news at the given position in the list of earthquakes
        News currentNews = getItem(position);

        // Find the TextView with view ID title
        TextView titleView = (TextView) listItemView.findViewById(R.id.newstitle);
        // Display the title of the current earthquake in that TextView
        titleView.setText(currentNews.getmTitle());

        TextView sectionView = listItemView.findViewById(R.id.sectionName);
        sectionView.setText(currentNews.getmSection());

        //get date and time from JSON
        String originalDate = currentNews.getmDate();
        String originalTime = currentNews.getmTime();

        // Find the TextView with view ID date
        TextView dateText = listItemView.findViewById(R.id.pubDate);
        dateText.setText(originalDate);

        // Find the TextView with view ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.pubTIme);
        timeView.setText(originalTime);

        TextView authorView = listItemView.findViewById(R.id.pubAuthor);
        authorView.setText(currentNews.getmAuthor());

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }
}