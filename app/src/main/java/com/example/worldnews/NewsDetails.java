package com.example.worldnews;

public class NewsDetails {

    private String mTitle;
    private String mCategory;
    private String mDescriptionOrContent;
    private String mSourceOfNews;
    private String mDateTimeOfNews;
    private String mUrlOfImage;
    private String mUrl;


    public NewsDetails(String mTitle, String mCategory, String mDescriptionOrContent, String mSourceOfNews, String mDateTimeOfNews, String mUrlOfImage, String mUrl) {
        this.mTitle = mTitle;
        this.mCategory = mCategory;
        this.mDescriptionOrContent = mDescriptionOrContent;
        this.mSourceOfNews = mSourceOfNews;
        this.mDateTimeOfNews = mDateTimeOfNews;
        this.mUrlOfImage = mUrlOfImage;
        this.mUrl = mUrl;
    }

    public NewsDetails(String mTitle, String mDescriptionOrContent, String mSourceOfNews, String mDateTimeOfNews, String mUrlOfImage, String mUrl) {
        this.mTitle = mTitle;
        this.mCategory = mCategory;
        this.mDescriptionOrContent = mDescriptionOrContent;
        this.mSourceOfNews = mSourceOfNews;
        this.mDateTimeOfNews = mDateTimeOfNews;
        this.mUrlOfImage = mUrlOfImage;
        this.mUrl = mUrl;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmCategory() {
        return mCategory;
    }

    public String getmDescriptionOrContent() {
        return mDescriptionOrContent;
    }

    public String getmSourceOfNews() {
        return mSourceOfNews;
    }

    public String getmDateTimeOfNews() {
        return mDateTimeOfNews;
    }

    public String getmUrlOfImage() {
        return mUrlOfImage;
    }

    public String getmUrl() {
        return mUrl;

    }
}
