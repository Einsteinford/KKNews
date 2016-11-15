package com.einsteinford.kknews.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.einsteinford.kknews.beans.NewsRequestParams;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by KK on 2016-10-31.
 */

public class NewsApi {
    private static final String TAG = NewsApi.class.getSimpleName();

    public static final String URL_CHANNEL = "http://ali-news.showapi.com/channelList";
    public static final String URL_NEWS = "http://ali-news.showapi.com/newsList";

    private RequestQueue mRequestQueue;
//    private Context mContext;


    public NewsApi(Context context) {
        this.mRequestQueue = Volley.newRequestQueue(context);
//        this.mContext = context;
    }

    public void getNewsList(NewsRequestParams params, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        String urlStringFormat = URL_NEWS + "?channelId=%s&channelName=%s&needContent=%s&needHtml=%s&page=%s&title=%s";
        String url = String.format(urlStringFormat, params.getChannelId(), params.getChannelName(),
                params.getNeedContent(), params.getNeedHtml(), params.getPage(), params.getTitle());

        NewsRequest newsRequest = new NewsRequest(url, responseListener, errorListener);
        mRequestQueue.add(newsRequest);
    }

    public void getChannelList(Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        NewsRequest newsRequest = new NewsRequest(URL_CHANNEL, responseListener, errorListener);
        mRequestQueue.add(newsRequest);
    }

    public String getChannelListSync() {
        //创建一个标准格式化字符串，用于String.format()
        RequestFuture<String> requestFuture = RequestFuture.newFuture();

        NewsRequest newsRequest = new NewsRequest(URL_CHANNEL, requestFuture, requestFuture);
        mRequestQueue.add(newsRequest);

        try {
            return requestFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
