package com.einsteinford.kknews.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.einsteinford.kknews.R;
import com.einsteinford.kknews.beans.Channel;

import java.util.List;

/**
 * Created by KK on 2016-11-14.
 */

public class ChannelListAdapter extends RecyclerView.Adapter<ChannelListAdapter.ChannelViewHolder> {
    private List<Channel> mChannels;
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;

    @Override
    public ChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChannelViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ChannelViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ChannelViewHolder extends RecyclerView.ViewHolder {
        public ChannelViewHolder(View itemView) {
            super(itemView);
        }
    }
}
