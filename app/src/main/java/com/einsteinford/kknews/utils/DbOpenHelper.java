package com.einsteinford.kknews.utils;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.einsteinford.kknews.beans.Channel;
import com.einsteinford.kknews.beans.ChannelListData;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by KK on 2016-10-31.
 */

public class DbOpenHelper extends SQLiteOpenHelper {
    public static final String TAG = DbOpenHelper.class.getSimpleName();

    public static final String DATABASE_NAME = "news.db";
    public static final int DATABASE_VERSION = 1;

    private Context mContext;

    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE channel(id TEXT,name TEXT,subscribed INT)");
        initTable(db);
    }

    private void initTable(SQLiteDatabase db) {
        NewsApi newsApi = new NewsApi(mContext);
        Gson gson = new Gson();

        ChannelListData data = gson.fromJson(newsApi.getChannelListSync(), ChannelListData.class);
        if (data != null) {
            if (data.getCode() == 0) {
                List<Channel> channels = data.getBody().getChannelList();
                String insertChannel = "INSERT INTO channel(id,name,subscribed) VALUES('%s','%s',%s)";
                int i = 0;
                for (Channel channel : channels) {
                    //默认订阅前5个频道
                    if (i < 5) {
                        db.execSQL(String.format(insertChannel, channel.getId(), channel.getName(), 1));
                    } else {
                        db.execSQL(String.format(insertChannel, channel.getId(), channel.getName(), 0));
                    }
                    i++;
                }
            } else {
                Log.e(TAG, data.getError());
                Looper.prepare();
                Toast.makeText(mContext, data.getError(), Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
