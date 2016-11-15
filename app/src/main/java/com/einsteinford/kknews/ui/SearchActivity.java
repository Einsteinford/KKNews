package com.einsteinford.kknews.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.einsteinford.kknews.R;

public class    SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        }

        Bundle args = new Bundle();
        args.putString("title", getIntent().getStringExtra("title"));
        NewsListFragment fragment = new NewsListFragment();
        fragment.setArguments(args);        //为Fragment添加附加对象
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
        //替换为Fragment
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
