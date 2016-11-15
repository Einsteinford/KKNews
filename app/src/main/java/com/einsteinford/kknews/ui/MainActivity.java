package com.einsteinford.kknews.ui;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.einsteinford.kknews.R;
import com.einsteinford.kknews.beans.Channel;
import com.einsteinford.kknews.ui.adapter.NewsPagerAdapter;
import com.einsteinford.kknews.utils.NewsDb;
import com.einsteinford.kknews.utils.SaveDataUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int CODE_DATA_CHANGED = 1000;
    public static final String TAG = MainActivity.class.getSimpleName();
    public ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView(); //调用初始化方法
        initData(); //调用数据初始化方法
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);   //将toolbar设定成页面的ActionBar

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);    //实例化TabLayout
        mViewPager = (ViewPager) findViewById(R.id.view_pager);     //实例化ViewPager并设为全局变量
        tabLayout.setupWithViewPager(mViewPager);   //绑定tab和viewpager，让两边同步变化
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //添加的TabLayout的页面改变监听器，意思ViewPager是变化时，另Tab等同变化

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //设定tab选中监听器
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(), true);
                //选中哪个就把viewpager切换到对应页面
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initData() {
        final List<NewsListFragment> fragments = new ArrayList<>();
        final List<String> titles = new ArrayList<>();

        new Thread() {
            @Override
            public void run() {
                super.run();
                //耗时操作放在工作线程中
                NewsDb newsDb = new NewsDb(MainActivity.this);
                List<Channel> channels = newsDb.getSubscribedChannelList();         //读取订阅的频道
                for (Channel channel : channels) {
                    Bundle args = new Bundle();
                    args.putString("channelId", channel.getId());
                    NewsListFragment fragment = new NewsListFragment();
                    fragment.setArguments(args);
                    fragments.add(fragment);
                    titles.add(channel.getName());
                }
                runOnUiThread(new Runnable() {      //在主线程中运行
                    @Override
                    public void run() {
                        /**
                         * viewpager装配数据的流程：
                         * 1. 数据源（一个或多个数据的list，一般是 titles、Fragments，或只有Fragments)
                         * 2. 将数据源加载到 PagerAdapter
                         * 3. 给viewpager绑定pagerAdapter，完成数据与viewpager控件的数据绑定
                         */
                        NewsPagerAdapter pagerAdapter = new NewsPagerAdapter(getSupportFragmentManager(), fragments, titles);
                        mViewPager.setAdapter(pagerAdapter);
                    }
                });
            }
        }.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //在菜单第一次加载是调用
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_activity, menu);
        //只有继承自AppCompatActivity的活动，才能通过getMenuInflater().inflate()获得menu的实例。
        //接下来进行搜索栏的设置
        MenuItem menuItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        //
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("title", query);
                startActivity(intent);
                searchView.onActionViewCollapsed();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;    //若为false则不显示
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                break;
            case R.id.download:
                break;
            case R.id.manage:
                startActivityForResult(new Intent(MainActivity.this, ChannelActivity.class), CODE_DATA_CHANGED);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == CODE_DATA_CHANGED) {
            initData();
        }
    }
}
