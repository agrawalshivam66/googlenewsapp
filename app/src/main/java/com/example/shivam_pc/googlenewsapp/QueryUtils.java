package com.example.shivam_pc.googlenewsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Shivam-PC on 21-01-2018.
 */


public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static List<news> fetchnewsData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<news> newslist1 = extractFeatureFromJson(jsonResponse);

        return newslist1;
    }


    private static URL createUrl(String stringUrl) {
        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static Bitmap image(String src) {
        if (src == null) {
            src = "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fi5.walmartimages.com%2Fasr%2Ff752abb3-1b49-4f99-b68a-7c4d77b45b40_1.39d6c524f6033c7c58bd073db1b99786.jpeg%3FodnHeight%3D450%26odnWidth%3D450%26odnBg%3DFFFFFF&imgrefurl=https%3A%2F%2Fwww.walmart.com%2Fip%2FScotch-Multipurpose-Scissors-8in-Pointed-Gray-black%2F19675467&docid=_jQrOId75pO15M&tbnid=-pCCN7e53SUTOM%3A&vet=10ahUKEwiJ95jRpO7YAhXImJQKHVQGDS4QMwgxKAAwAA..i&w=275&h=275&bih=662&biw=1366&q=no%20image%20found&ved=0ahUKEwiJ95jRpO7YAhXImJQKHVQGDS4QMwgxKAAwAA&iact=mrc&uact=8";
        }
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<news> extractFeatureFromJson(String newsjson) {
        if (TextUtils.isEmpty(newsjson)) {
            return null;
        }

        List<news> news_list = new ArrayList<>();

        int i = 0;
        try {
            JSONObject basejsonResponse = new JSONObject(newsjson);

            JSONArray newsarray = basejsonResponse.getJSONArray("articles");

            for (i = 0; i < newsarray.length(); i++) {

                String title = "Not Available";
                String describe = "Not Available";
                String url = "Not Available";
                String source = "Not Available";
                String time = "Not Available";

                JSONObject currentnews = newsarray.getJSONObject(i);


                title = currentnews.optString("title");

                describe = currentnews.optString("description");

                time = currentnews.optString("publishedAt");

                url = currentnews.optString("url");

                JSONObject sources = currentnews.getJSONObject("source");

                source = sources.optString("name");

                String img = currentnews.optString("urlToImage");

                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+ss:ss");
                String ago=gettimeago(sdf1,time);
                if(ago.equals("1 Jan 1970"))

                {
                    ago=gettimeago(sdf2,time);
                }

                news newss = new news(title, source, ago, url, describe, img);

                news_list.add(newss);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
            Log.e("value", Integer.toString(i));
        }

        return news_list;
    }


    static String gettimeago(SimpleDateFormat sdf, String time) {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        long time2 = 0;
        try {
            time2 = sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long now = System.currentTimeMillis();

        String ago =
                (String) DateUtils.getRelativeTimeSpanString(time2, now, DateUtils.MINUTE_IN_MILLIS);
        return ago;
    }
}