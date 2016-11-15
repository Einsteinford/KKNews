package com.einsteinford.kknews.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.einsteinford.kknews.R;
import com.einsteinford.kknews.beans.Article;
import com.einsteinford.kknews.beans.NewsRequestParams;
import com.einsteinford.kknews.ui.adapter.NewsListAdapter;
import com.einsteinford.kknews.utils.NewsLoader;

import java.util.List;

public class NewsListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, NewsListAdapter.OnItemViewClickListener {
    //实现刷新接口

    public View mView;
    private int mPage;
    private String mChannelID;
    private String mTitle;
    private NewsLoader mNewsLoader;
    public SwipeRefreshLayout mRefreshLayout;
    public LinearLayoutManager mLayoutManager;
    public NewsListAdapter mNewsListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_news_list, container, false);
        //整个viewpager的页面是RecyclerView，里面填充的是各个CardView
        init();
        return mView;
    }

    private void init() {
        mPage = 1;
        mChannelID = getArguments().getString("channelId", "");
        mTitle = getArguments().getString("title", "");
        mNewsLoader = new NewsLoader(getContext());


        mRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.refresh_layout);
        //实例化下拉刷新部件
        mRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mRefreshLayout.setOnRefreshListener(this);   //设置刷新监听器

        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mLayoutManager.getItemCount() == mLayoutManager.findLastVisibleItemPosition() + 1) {
                        loadNextPage();
                    }
                }
            }
        });
        mNewsListAdapter = new NewsListAdapter();
        mNewsListAdapter.setOnItemViewClickListener(this);
        recyclerView.setAdapter(mNewsListAdapter);

        refreshData();
    }

    @Override
    public void onRefresh() {

    }

    private void refreshData() {
        if (!mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(true);
        }
        mNewsLoader.setOnLoadNewsDataListener(new NewsLoader.OnLoadNewsDataListener() {
            @Override
            public void OnLoadNewsDataSuccess(String channelId, List<Article> articles) {
                mNewsListAdapter.setArticles(articles);
                mNewsListAdapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);
                mPage = 1;
            }

            @Override
            public void OnLoadNewsDataError(String error) {

            }
        });
        mNewsLoader.loadNewsData(new NewsRequestParams()
                .setChannelId(mChannelID)
                .setTitle(mTitle)
                .setPage(1));
    }

    private void loadNextPage() {
        mNewsLoader.setOnLoadNewsDataListener(new NewsLoader.OnLoadNewsDataListener() {
            @Override
            public void OnLoadNewsDataSuccess(String channelId, List<Article> articles) {
                mNewsListAdapter.addArticles(articles);
                mNewsListAdapter.notifyDataSetChanged();
                mPage++;
            }

            @Override
            public void OnLoadNewsDataError(String error) {

            }
        });

        mNewsLoader.loadNewsData(new NewsRequestParams()
                .setChannelId(mChannelID)
                .setTitle(mTitle)
                .setPage(mPage + 1));
    }

    @Override
    public void OnItemViewClick(int position, View itemView) {
        Intent intent = new Intent(getContext(),NewsActivity.class);
        intent.putExtra("title",mNewsListAdapter.getArticles().get(position).getTitle());
        intent.putExtra("html",mNewsListAdapter.getArticles().get(position).getHtml());
        startActivity(intent);
    }
}
