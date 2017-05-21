package com.ljr.ljrnews.fragment.news_fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.ljr.ljrnews.R;
import com.ljr.ljrnews.adapter.NewsAdapter;
import com.ljr.ljrnews.base.BaseFragment;
import com.ljr.ljrnews.bean.NewsBean;
import com.ljr.ljrnews.utils.AppNetConfig;
import com.ljr.ljrnews.utils.Util;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by LinJiaRong on 2017/5/16.
 * TODO：
 */

public class InvestFragment extends BaseFragment {
    private static final String TAG = "InvestFragment";
    @ViewInject(R.id.news_recyclerview)
    private RecyclerView mNewsRecyclerview;
    @ViewInject(R.id.refresh)
    private SwipeRefreshLayout mRefreshLayout;
    private NewsAdapter mAdapter;
    private NewsBean mNewsBean;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.tab_news, null);
        x.view().inject(this, view);
        mNewsRecyclerview.setLayoutManager(new GridLayoutManager(mContext,1));
        return view;
    }
    @Override
    public void initData() {
        super.initData();
        getBeanFromNet();
        initRefresh();
    }
    private void initViewData() {
        NewsBean.ResultBean result = mNewsBean.result;
        mAdapter = new NewsAdapter(mContext, result);
        mNewsRecyclerview.setAdapter(mAdapter);

    }
    public void getBeanFromNet() {
        /**
         * 使用xUtils3联网获取数据
         */
        String type = "caijing";
        String url = AppNetConfig.getURL(type);

        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                //解析数据
                mNewsBean = new Gson().fromJson(result, NewsBean.class);
                initViewData();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d(TAG, "onError: " + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d(TAG, "onCancelled: " + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.d(TAG, "onFinished: ");
            }
        });
    }
    private void initRefresh() {
        mRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }
    private void refreshData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getBeanFromNet();
                mAdapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);
            }
        }).start();
    }
}
