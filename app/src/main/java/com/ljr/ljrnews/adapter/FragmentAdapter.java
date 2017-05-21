package com.ljr.ljrnews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ljr.ljrnews.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LinJiaRong on 2017/5/16.
 * TODO：
 */

public class FragmentAdapter extends FragmentStatePagerAdapter{
    private ArrayList<BaseFragment> mFragments;
    private String[] mTitles;
    public FragmentAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments, String[] titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
