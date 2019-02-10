package com.example.worldnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<NewsDetails> {
    public NewsAdapter(Context context, List<NewsDetails> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView=convertView;
        if(listView==null){
            listView= LayoutInflater.from(getContext()).inflate(R.layout.list_item_design,parent,false);
        }

        NewsDetails currentNewsDetails=getItem(position);

        TextView titleTextView=(TextView) listView.findViewById(R.id.title_text_view);
        titleTextView.setText(currentNewsDetails.getmTitle());

        TextView categoryTextView=(TextView) listView.findViewById(R.id.category_text_view);
        categoryTextView.setText(CategoryVariableStorage.getCategory());

        TextView descriptionTextView=(TextView) listView.findViewById(R.id.content_or_description_text_view);
        descriptionTextView.setText(currentNewsDetails.getmDescriptionOrContent());

        TextView sourceTextView=(TextView) listView.findViewById(R.id.source_text_view);
        sourceTextView.setText(currentNewsDetails.getmSourceOfNews());

        TextView publishedDateTextView=(TextView) listView.findViewById(R.id.published_date_text_view);
        TextView publishedTimeTextView=(TextView) listView.findViewById(R.id.published_time_text_view);

        String originalDateTimeFormat=currentNewsDetails.getmDateTimeOfNews();
        String[] parts=originalDateTimeFormat.split("T");
        String date=parts[0];
        String unformattedTime=parts[1];
        String time=unformattedTime.substring(0,unformattedTime.length()-1);

        publishedDateTextView.setText(date);
        publishedTimeTextView.setText(time);

        ImageView imageView=(ImageView) listView.findViewById(R.id.image_view);
        if(!currentNewsDetails.getmUrlOfImage().isEmpty()){
            Picasso.get().load(currentNewsDetails.getmUrlOfImage()).placeholder(R.drawable.placeholder).resize(380,200).into(imageView);
        }

        return listView;
    }
}
