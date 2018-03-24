package com.example.shivam_pc.googlenewsapp;

/**
 * Created by Shivam-PC on 21-01-2018.
 */

public class news {
    private String msource;

    private String mdescribe;

    private String mtime;

    private String murl;

    private String mtitle;

    private String mbit;

    public news(String title, String source, String time, String url, String describe,String bit){
        mtime=time;
        mdescribe=describe;
        msource=source;
        mtitle=title;
        murl=url;
       mbit=bit;

    }

    public String gettitle(){
        return mtitle;
    }
    public String gettime(){
        return mtime;
    }
    public String getdescribe(){
        return mdescribe;
    }
    public String getsource(){
        return msource;
    }
    public String geturl(){
        return murl;
    }
    public String getimage()
    {
        return mbit;
    }


}
