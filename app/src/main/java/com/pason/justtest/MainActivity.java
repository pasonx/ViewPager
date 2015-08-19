package com.pason.justtest;


import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener,ViewPager.OnPageChangeListener{

    private ViewPager vp;

    private List<Fragment> mTabs = new ArrayList<Fragment>();

    private List<ChangeColorIconWithText> mBtnTabs = new ArrayList<ChangeColorIconWithText>();

    private String[] mTitles = new String[]{"First Fragment","Second Fragment","Third Fragment","Fourth Fragment"};

    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        vp.setAdapter(mAdapter);
        initEvent();
    }

    private void initEvent() {
        vp.setOnPageChangeListener(this);
    }


    private void initView(){
        vp = (ViewPager)findViewById(R.id.vpager);
        ChangeColorIconWithText one = (ChangeColorIconWithText) findViewById(R.id.ind_one);
        mBtnTabs.add(one);
        ChangeColorIconWithText two = (ChangeColorIconWithText) findViewById(R.id.ind_two);
        mBtnTabs.add(two);
        ChangeColorIconWithText three = (ChangeColorIconWithText) findViewById(R.id.ind_three);
        mBtnTabs.add(three);
        ChangeColorIconWithText four = (ChangeColorIconWithText) findViewById(R.id.ind_four);
        mBtnTabs.add(four);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);

        one.setIconAlpha(1.0f);
    }

    private void initData(){
        for(String title:mTitles)
        {
            TabFragment tabFragment = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putString(TabFragment.TITLE,title);
            tabFragment.setArguments(bundle);
            mTabs.add(tabFragment);

        }
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTabs.size();
            }

            @Override
            public Fragment getItem(int i) {
                return mTabs.get(i);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        reSetOtherBtnTabs();
        switch (v.getId())
        {
            case R.id.ind_one:
                mBtnTabs.get(0).setIconAlpha(1.0f);
                vp.setCurrentItem(0,false);
                break;
            case R.id.ind_two:
                mBtnTabs.get(1).setIconAlpha(1.0f);
                vp.setCurrentItem(1, false);
                break;
            case R.id.ind_three:
                mBtnTabs.get(2).setIconAlpha(1.0f);
                vp.setCurrentItem(2, false);
                break;
            case R.id.ind_four:
                mBtnTabs.get(3).setIconAlpha(1.0f);
                vp.setCurrentItem(3, false);
                break;

        }
    }

    private void reSetOtherBtnTabs() {
        for (int i = 0 ; i < mBtnTabs.size(); i ++)
        {
            mBtnTabs.get(i).setIconAlpha(0);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if (positionOffset > 0)
        {
            ChangeColorIconWithText left = mBtnTabs.get(position);
            ChangeColorIconWithText right = mBtnTabs.get(position + 1);
            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
