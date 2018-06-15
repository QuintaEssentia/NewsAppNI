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

    private String DATE_SEPARATOR = "T";
    private String TIME_SEPARATOR = "Z";
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

        // Find the earthquake at the given position in the list of earthquakes
        News currentNews = getItem(position);

        // Find the TextView with view ID magnitude
        TextView titleView = (TextView) listItemView.findViewById(R.id.newstitle);
        // Display the magnitude of the current earthquake in that TextView
        titleView.setText(currentNews.getmTitle());

        TextView sectionView = listItemView.findViewById(R.id.sectionName);
        sectionView.setText(currentNews.getmSection());



        // Get the original location string from the Earthquake object,
        // which can be in the format of "5km N of Cairo, Egypt" or "Pacific-Antarctic Ridge".
        String originalDate = currentNews.getmDate();
        String originalTime = currentNews.getmTime();

        // If the original location string (i.e. "5km N of Cairo, Egypt") contains
        // a primary location (Cairo, Egypt) and a location offset (5km N of that city)
        // then store the primary location separately from the location offset in 2 Strings,
        // so they can be displayed in 2 TextViews.

        //String newsDate;
        //String newsTimeRaw;
        //String newsTimeFin;

        // Check whether the originalLocation string contains the " of " text

            // Split the string into different parts (as an array of Strings)
            // based on the " of " text. We expect an array of 2 Strings, where
            // the first String will be "5km N" and the second String will be "Cairo, Egypt".
          //  String[] parts = originalDate.split(DATE_SEPARATOR);
                // Locaton offset should be "5km N " + " of " --> "5km N of"
            //    newsDate = parts[0];
                // Primary location should be "Cairo, Egypt"
             //  newsTimeRaw = parts[1];
              // String[] timeparts = newsTimeRaw.split(TIME_SEPARATOR);
              // newsTimeFin = timeparts[0];

        // Find the TextView with view ID date
           TextView dateText = listItemView.findViewById(R.id.pubDate);
          dateText.setText(originalDate);
        // Find the TextView with view ID location

        //DateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");


        //TextView timeText = listItemView.findViewById(R.id.pubTIme);
        //timeText.setText(newsTimeFin);



               // Create a new Date object from the date and time
      //  DateFormat df = new SimpleDateFormat("YYYY-MM-DD");

        //DateFormat dft = new SimpleDateFormat("hh:mm:ss");

        // Find the TextView with view ID date

        // Format the date string (i.e. "Mar 3, 1984")

        //Date resultDate = null;
        //try {
         //   resultDate = df.parse(newsDate);
        //} catch (ParseException e) {
        //    e.printStackTrace();
        //}
        //Date myDate = null;
        //try {
          //  myDate = myFormat.parse(originalDate);
        //} catch (ParseException e) {
          //  e.printStackTrace();
       // }
        //String formattedDate = formatDate(myDate);
        // Display the date of the news in that TextView
        //dateView.setText(formattedDate);

        // Find the TextView with view ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.pubTIme);
        timeView.setText(originalTime);
        // Format the time string (i.e. "4:30PM")
        //Date resultTime = null;
        //try {
        //    resultTime = dft.parse(newsTimeFin);
        //} catch (ParseException e) {
        //    e.printStackTrace();
        //}
        //String formattedTime = formatTime(myDate);
        // Display the time of the news in that TextView
        //timeView.setText(formattedTime);

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
