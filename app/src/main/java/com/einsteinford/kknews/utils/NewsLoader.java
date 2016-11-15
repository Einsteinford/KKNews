package com.einsteinford.kknews.utils;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.einsteinford.kknews.beans.Article;
import com.einsteinford.kknews.beans.NewsListData;
import com.einsteinford.kknews.beans.NewsRequestParams;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by KK on 2016-11-02.
 */

public class NewsLoader {
    private Context mContext;
    private NewsApi mNewsApi;
    private NewsDb mNewsDb;

    private OnLoadNewsDataListener mOnLoadNewsDataListener;
    //实现内部的接口,观察者模式

    public NewsLoader(Context context) {
        this.mContext = context;
        this.mNewsApi = new NewsApi(context);
        this.mNewsDb = new NewsDb(context);
    }

    public void loadNewsData(NewsRequestParams params) {
        loadNewsDataOnline(params);
        //可能还存在offline的处理
    }

    public void loadNewsDataOnline(final NewsRequestParams params) {
        mNewsApi.getNewsList(params, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (mOnLoadNewsDataListener != null) {
                    NewsListData newsListData = new Gson().fromJson(response,NewsListData.class);
                    //通过Gson将json文件转换为对象
                    if (newsListData.getCode()==0){     //0表示返回成功
                        //此处调用内部接口，但没有实现，实现由使用此接口的对象进行处理
                        mOnLoadNewsDataListener.OnLoadNewsDataSuccess(params.getChannelId(),newsListData.getBody().getPage().getArticleList());
                    }else {
                        mOnLoadNewsDataListener.OnLoadNewsDataError(newsListData.getError());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mOnLoadNewsDataListener != null) {
                    mOnLoadNewsDataListener.OnLoadNewsDataError(error.getMessage());
                }
            }
        });
    }

    public void setOnLoadNewsDataListener(OnLoadNewsDataListener onLoadNewsDataListener) {
        //通过set实现接口
        this.mOnLoadNewsDataListener = onLoadNewsDataListener;
    }

    public interface OnLoadNewsDataListener {
        void OnLoadNewsDataSuccess(String channelId, List<Article> articles);

        void OnLoadNewsDataError(String error);

    }
}
