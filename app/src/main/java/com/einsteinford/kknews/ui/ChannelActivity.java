package com.einsteinford.kknews.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.einsteinford.kknews.R;
import com.einsteinford.kknews.beans.ChannelListData;
import com.einsteinford.kknews.utils.NewsDb;

public class ChannelActivity extends AppCompatActivity {
    private NewsDb mNewsDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        init();
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //在左上角添加返回箭头
            getSupportActionBar().setTitle("订阅频道");
        }
        mNewsDb = new NewsDb(this);     //创建了包含数据库可读写对象的类


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:     //左上角返回键的id
                finish();
                break;
        }
        return false;
    }
}
