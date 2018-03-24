package com.example.shivam_pc.googlenewsapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shivam-PC on 22-03-2018.
 */

public class topstoriesfragments extends Fragment implements LoaderManager.LoaderCallbacks<List<news>> {


    ProgressBar p;
    private  String google_api_url = "https://newsapi.org/v2/top-headlines?sources=google-news-in&apiKey=6ca8f05aaed846a292b1d0b35e524bbf";
    private newsAdapter mAdapter;
    private TextView empty;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootvview=inflater.inflate(R.layout.news_activity,container,false);


        mAdapter = new newsAdapter((news_activity) getActivity(), new ArrayList<news>());
    ListView news_list_view = (ListView)rootvview.findViewById(R.id.list);


        p = (ProgressBar)rootvview.findViewById(R.id.loading_spinner);
        news_list_view.setAdapter(mAdapter);

        empty = (TextView)rootvview.findViewById(R.id.empty_view);
        news_list_view.setEmptyView(empty);


        news_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                news currentnews = mAdapter.getItem(position);
                String newsurl = currentnews.geturl().toString();
                Intent webint = new Intent(getActivity(),webpage_opener.class);
                webint.putExtra("url",newsurl);
                startActivity(webint);
            }

        });



        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);


        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected())

        {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(1, null, this);
        }

        else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = rootvview.findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            empty.setText("No Internet Connection");
        }
    return rootvview;
    }





    @Override
    public Loader<List<news>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new newsLoader(getContext(), google_api_url);
    }


    @Override
    public void onLoadFinished(Loader<List<news>> loader, List<news> newsList) {
        // Set empty state text to display "No news found."
        empty.setText("No news Found");
        // Clear the adapter of previous news data
        mAdapter.clear();


        // data set. This will trigger the ListView to update.
        if (newsList != null && !newsList.isEmpty()) {
            mAdapter.addAll(newsList);

            assert p != null;

            p.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<news>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}




