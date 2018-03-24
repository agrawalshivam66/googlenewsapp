package com.example.shivam_pc.googlenewsapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Shivam-PC on 21-01-2018.
 */

public class news_activity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hometab);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        fragmenttabs adapter = new fragmenttabs(this,getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);
        // Create an adapter that knows which fragment should be shown on each page

        // Set the content of the activity to use the activity_main.xml layout file

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);

        // Connect the tab layout with the view pager. This will
        //   1. Update the tab layout when the view pager is swiped
        //   2. Update the view pager when a tab is selected
        //   3. Set the tab layout's tab names with the view pager's adapter's titles
        //      by calling onPageTitle()
        tabLayout.setupWithViewPager(viewPager);

    }

}
