package com.susu.hh.mygreendao.tablelyout;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.susu.hh.mygreendao.R;


public class TablyoutActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablyout);
        TabLayout tablayout = (TabLayout) findViewById(R.id.tablayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabfragmentAdapter tabfragmentAdapter = new TabfragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabfragmentAdapter);
        tablayout.setupWithViewPager(viewPager);
    }
}
