package com.example.nataliyailyushina.newappni;

public class News {
    private String mTitle;
    private String mDate;
    private String mSection;
    private String mTime;
    private String mUrl;
    private String mAuthor;

public News(String Title, String Section, String Date, String Time, String url, String author){
    mTitle = Title;
    mSection = Section;
    mDate = Date;
    mTime = Time;
    mUrl = url;
    mAuthor = author;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmTime() {return mTime; }

    public String getmSection() {
        return mSection;
    }

    public String getmUrl() {
        return mUrl;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }
}
