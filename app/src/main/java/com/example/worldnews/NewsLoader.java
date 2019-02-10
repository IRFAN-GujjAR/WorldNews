package com.example.worldnews;

import android.content.Context;
import android.content.AsyncTaskLoader;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<NewsDetails>> {

    private String mUrl;

    public NewsLoader(Context context,String url){
        super(context);
        mUrl=url;

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsDetails> loadInBackground() {
       if(mUrl==null){
           return null;
       }
        List<NewsDetails> news=QueryUtils.fetchNewsData(mUrl);
        return news;
    }
}
