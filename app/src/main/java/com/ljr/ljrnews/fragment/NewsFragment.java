package com.ljr.ljrnews.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljr.ljrnews.R;
import com.ljr.ljrnews.adapter.FragmentAdapter;
import com.ljr.ljrnews.base.BaseFragment;
import com.ljr.ljrnews.fragment.news_fragment.AmusementFragment;
import com.ljr.ljrnews.fragment.news_fragment.ChinaFragment;
import com.ljr.ljrnews.fragment.news_fragment.FashionFragment;
import com.ljr.ljrnews.fragment.news_fragment.HotFragment;
import com.ljr.ljrnews.fragment.news_fragment.InternationFragment;
import com.ljr.ljrnews.fragment.news_fragment.InvestFragment;
import com.ljr.ljrnews.fragment.news_fragment.KeJiFragment;
import com.ljr.ljrnews.fragment.news_fragment.PhysicalFragment;
import com.ljr.ljrnews.fragment.news_fragment.SocietyFragment;
import com.ljr.ljrnews.fragment.news_fragment.WarFragment;
import com.ljr.ljrnews.utils.Util;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by LinJiaRong on 2017/5/15.
 * TODO：
 */

public class NewsFragment extends BaseFragment {
    private static final String TAG = "NewsFragment";

    private ArrayList<BaseFragment> mFragments;
    private String[] mTitles;
    private View mView;
    @ViewInject(R.id.tablayout)
    private TabLayout mTablayout;
    @ViewInject(R.id.news_viewpager)
    private ViewPager mNewsViewpager;
    // private ArrayList<String> mTitles;

    @Override
    public View initView() {
        mView = View.inflate(mContext, R.layout.fragment_news, null);
      //  mTablayout = (TabLayout) mView.findViewById(R.id.tablayout);
      //  mNewsViewpager = (ViewPager) mView.findViewById(R.id.news_viewpager);
        x.view().inject(this,mView);
        return mView;
    }

    @Override
    public void initData() {
        super.initData();
        mTitles = new String[]{"头条","社会","娱乐", "体育","国内","国际","科技","时尚","财经","军事"};
        mFragments = new ArrayList<>();
        mFragments.add(new HotFragment());
        mFragments.add(new SocietyFragment());
        mFragments.add(new AmusementFragment());
        mFragments.add(new PhysicalFragment());
        mFragments.add(new ChinaFragment());
        mFragments.add(new InternationFragment());
        mFragments.add(new KeJiFragment());
        mFragments.add(new FashionFragment());
        mFragments.add(new InvestFragment());
        mFragments.add(new WarFragment());

        FragmentManager fragmentManager = getFragmentManager();
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentAdapter fragmentAdapter = new FragmentAdapter(fragmentManager, mFragments, mTitles);
            mNewsViewpager.setAdapter(fragmentAdapter);
            mTablayout.setupWithViewPager(mNewsViewpager);
            mTablayout.setTabsFromPagerAdapter(fragmentAdapter);


    }


}
