package com.einsteinford.kknews.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.einsteinford.kknews.R;
import com.einsteinford.kknews.beans.Article;

import java.util.List;

/**
 * Created by KK on 2016-10-27.
 */

public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_FOOTER = 1;

    private List<Article> mArticles;
    private OnItemViewClickListener mOnItemViewClickListener;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            return new ItemViewHolder(LayoutInflater
                    .from(parent.getContext()).inflate(R.layout.news_list_item, parent, false));
        } else if (viewType == VIEW_TYPE_FOOTER) {
            return new FooterViewHolder(LayoutInflater
                    .from(parent.getContext()).inflate(R.layout.news_list_footer, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder && position < mArticles.size()) {
            final ItemViewHolder viewHolder = (ItemViewHolder) holder;
            Article article = mArticles.get(position);
            viewHolder.titleTv.setText(article.getTitle());
            viewHolder.descTv.setText(article.getDesc());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemViewClickListener != null) {
                        mOnItemViewClickListener.OnItemViewClick(position, viewHolder.itemView);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mArticles != null) {
            return mArticles.size() + 1;
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mArticles.size()) {
            return VIEW_TYPE_FOOTER;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    public void setArticles(List<Article> articles) {
        this.mArticles = articles;
    }

    public List<Article> getArticles() {
        return mArticles;
    }

    public void addArticles(List<Article> articles){
        this.mArticles.addAll(articles);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private TextView titleTv;
        private TextView descTv;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            titleTv = (TextView) itemView.findViewById(R.id.titleTv);
            descTv = (TextView) itemView.findViewById(R.id.descTv);
            titleTv.setText("标题");
            descTv.setText("新闻详情");
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnItemViewClickListener(OnItemViewClickListener onItemViewClickListener) {
        this.mOnItemViewClickListener = onItemViewClickListener;
    }

    public interface OnItemViewClickListener {
        void OnItemViewClick(int position, View itemView);
    }
}
