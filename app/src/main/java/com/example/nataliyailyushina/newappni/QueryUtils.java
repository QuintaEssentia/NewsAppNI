package com.example.nataliyailyushina.newappni;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static List<News> fetchNewsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<News> news = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link News}
        return news;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String formatDate(String dateData) {
        String guardianJsonDateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat jsonDateFormatter = new SimpleDateFormat(guardianJsonDateFormat);
        try {
            Date jsonDateToParse = jsonDateFormatter.parse(dateData);
            String resultDate = "MMM d, yyy";
            SimpleDateFormat resultDateFormatter = new SimpleDateFormat(resultDate);
            return resultDateFormatter.format(jsonDateToParse);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "Error Formatting the Json Date", e);
            return "";
        }
    }

    private static String formatTime(String dateData) {
        String guardianJsonDateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat jsonDateFormatter = new SimpleDateFormat(guardianJsonDateFormat);
        try {
            Date jsonDateToParse = jsonDateFormatter.parse(dateData);
            String resultTime = "h:mm a";
            SimpleDateFormat resultDateFormatter = new SimpleDateFormat(resultTime);
            return resultDateFormatter.format(jsonDateToParse);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "Error Formatting the Json Date", e);
            return "";
        }
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<News> extractFeatureFromJson(String earthquakeJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<News> news = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);

            JSONObject newsResponse = baseJsonResponse.getJSONObject("response");

            JSONArray newsResults = newsResponse.getJSONArray("results");


            // root-response array - results array - 1st element, section string etc
            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
           // JSONArray newsArray = baseJsonResponse.getJSONArray("response");

           //JSONObject newsResults1 = newsArray.getJSONObject(0);

            // For each earthquake in the earthquakeArray, create an {@link News} object
            for (int i = 0; i < newsResults.length(); i++) {

                // Get a single earthquake at position i within the list of earthquakes
                JSONObject currentNews = newsResults.getJSONObject(i);

                // For a given earthquake, extract the JSONObject associated with the
                // key called "properties", which represents a list of all properties
                // for that earthquake.
                //JSONObject properties = currentNews.getJSONObject("results");

                // Extract the value for the key called "mag"
                String title = currentNews.getString("webTitle");

                // Extract the value for the key called "place"
                String section = currentNews.getString("sectionName");

                // Extract the value for the key called "time"
                String date = currentNews.getString("webPublicationDate");

                date = formatDate(date);
                String time = formatTime(date);

                // Extract the value for the key called "url"
                String url = currentNews.getString("webUrl");

                JSONArray newsTags = currentNews.getJSONArray("tags");


                    //JSONObject currentTags = newsTags.getJSONObject(0);
                   // String author = currentTags.getString("webTitle");


                String author = "author name not found";

                if (newsTags != null && newsTags.length() > 0) //if the author found ,parse it
                    author = newsTags.getJSONObject(0).getString("firstName") + " " + newsTags.getJSONObject(0).getString("lastName");

                // Create a new {@link News} object with the magnitude, location, time,
                // and url from the JSON response.
                News newnews = new News(title, section, date, time, url, author);

                // Add the new {@link News} to the list of earthquakes.
                news.add(newnews);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return news;
    }

}
