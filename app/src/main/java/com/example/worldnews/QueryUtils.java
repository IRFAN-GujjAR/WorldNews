package com.example.worldnews;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    private QueryUtils() {
    }

    private static URL createUrl(String url) {

        URL urlObject = null;
        try {
            urlObject = new URL(url);
        } catch (MalformedURLException e) {
            //
        }

        return urlObject;
    }

    private static String makeHttpRequest(URL urlObject) throws IOException {

        String jsonResponse = "";
        if (urlObject == null) {
            return jsonResponse;
        }

        HttpURLConnection httpURLConnection=null;
        InputStream inputStream=null;

        try {
            httpURLConnection=(HttpURLConnection) urlObject.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if(httpURLConnection.getResponseCode()==200){
                inputStream=(InputStream) httpURLConnection.getInputStream();
                jsonResponse=readFromStream(inputStream);
            }
            else {

                //
            }
        } catch (IOException e) {
            //
        }finally {
            if(httpURLConnection!=null){
                httpURLConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }

        return jsonResponse;

    }

    private static List<NewsDetails> extractDataFromJsonResponse(String jsonResponse){
        List<NewsDetails> news=new ArrayList<>();
        if(TextUtils.isEmpty(jsonResponse)){
            return null;
        }

        try {
            JSONObject baseJSONObject=new JSONObject(jsonResponse);
            JSONArray articlesArray=baseJSONObject.getJSONArray("articles");
            for(int i=1;i<articlesArray.length();i++){
                JSONObject currentArticle=articlesArray.getJSONObject(i);
                JSONObject sourceObject=currentArticle.getJSONObject("source");
                String sourceName=sourceObject.getString("name");
                String title=currentArticle.getString("title");
                String description=currentArticle.getString("description");
                String urlOfImage=currentArticle.getString("urlToImage");
                String url=currentArticle.getString("url");
                String publishedDate=currentArticle.getString("publishedAt");

                NewsDetails currentNewsDetails=new NewsDetails(title,description,sourceName,publishedDate,urlOfImage,url);
                news.add(currentNewsDetails);
            }
        } catch (JSONException e) {
            //
        }

        return news;
    }


    public static List<NewsDetails> fetchNewsData(String requestUrl){
        URL url=createUrl(requestUrl);
        String jsonResponse=null;
        try {
            jsonResponse=makeHttpRequest(url);
        } catch (IOException e) {
            //
        }
        List<NewsDetails> news=extractDataFromJsonResponse(jsonResponse);

        return news;
    }




    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output=new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String line=bufferedReader.readLine();
            while (line!=null){
                output.append(line);
                line=bufferedReader.readLine();
            }
        }

        return output.toString();

    }
}
