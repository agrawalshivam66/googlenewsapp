package com.example.shivam_pc.googlenewsapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import java.util.List;



/**
 * Created by Shivam-PC on 21-01-2018.
 */


class newsLoader extends AsyncTaskLoader<List<news>> {

    /** Query URL */
    private String mUrl;

    public newsLoader(news_activity newss, String google_api_url) {
        super(newss);
        mUrl=google_api_url;
    }

    public newsLoader(Context context, String google_api_url) {
        super(context);
        mUrl=google_api_url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<news> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of news
        List<news> newlist = QueryUtils.fetchnewsData(mUrl);
        return newlist;
    }
}


