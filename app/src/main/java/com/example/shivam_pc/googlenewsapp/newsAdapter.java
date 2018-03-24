package com.example.shivam_pc.googlenewsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Shivam-PC on 21-01-2018.
 */

public class newsAdapter extends ArrayAdapter<news> {

    Context context;

    public newsAdapter(news_activity context, List<news> news_list){

        super(context,0,news_list);
        this.context=context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate
                    (R.layout.list_item, parent, false);

        }

        news currentnews = getItem(position);

        TextView name = (TextView) listItemView.findViewById(R.id.title);
        String names1 = currentnews.gettitle();
        name.setText(names1);

        TextView sourcetext = (TextView) listItemView.findViewById(R.id.source);
        String source = currentnews.getsource();
        sourcetext.setText(source);


        TextView time = (TextView) listItemView.findViewById(R.id.time);
        String timestr = currentnews.gettime();

        time.setText(timestr);

        TextView describ = (TextView) listItemView.findViewById(R.id.describe);
        String describestr = currentnews.getdescribe();
        describ.setText(describestr);

        ImageView img =(ImageView)listItemView.findViewById(R.id.image);

        Glide.with(getContext())
                .load(currentnews.getimage())
                .into(img);


        return listItemView;
    }

    }





