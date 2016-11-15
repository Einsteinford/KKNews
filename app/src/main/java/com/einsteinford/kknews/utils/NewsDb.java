package com.einsteinford.kknews.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.einsteinford.kknews.beans.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KK on 2016-11-02.
 */

public class NewsDb {

    private SQLiteDatabase db;

    public NewsDb(Context context) {
        this.db = new DbOpenHelper(context).getWritableDatabase();
    }

    public List<Channel> getSubscribedChannelList(){
        return getChannelList("subscribed=1");
    }

    public List<Channel> getAllChannelList(){
        return getChannelList(null);
    }

    private List<Channel> getChannelList(String selection){
        List<Channel> channels = new ArrayList<>();
        Cursor cursor = db.query("channel",new String[]{"id","name","subscribed"},
                selection,null,null,null,null);
        while (cursor.moveToNext()){
            Channel channel = new Channel();
            channel.setId(cursor.getString(cursor.getColumnIndex("id")));
            channel.setName(cursor.getString(cursor.getColumnIndex("name")));
            channel.setSubscribed(cursor.getInt(cursor.getColumnIndex("subscribed")));
            channels.add(channel);
        }
        cursor.close();
        return channels;
    }

    public void setChannelSubscribed(String channelId,boolean subscribed){
        //订阅频道
        ContentValues cv = new ContentValues();
        if (subscribed){
            cv.put("subscribed",1);
        }else {
            cv.put("subscribed",0);
        }
        db.update("channel",cv,"id='"+channelId+"'",null);  //更新数据库
    }
}
