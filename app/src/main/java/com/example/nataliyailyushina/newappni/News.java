package com.example.nataliyailyushina.newappni;

public class News {
    private String mTitle;
    private String mDate;
    private String mSection;
    private String mUrl;

public News(String Title, String Section, String Date, String Url){
    mTitle = Title;
    mSection = Section;
    mDate = Date;
    mUrl = Url;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmSection() {
        return mSection;
    }

    public String getmUrl() {
        return mUrl;
    }

    public String getmTitle() {
        return mTitle;
    }
}
