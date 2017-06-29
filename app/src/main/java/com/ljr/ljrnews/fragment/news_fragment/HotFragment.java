package com.ljr.ljrnews.fragment.news_fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.ljr.ljrnews.R;
import com.ljr.ljrnews.adapter.NewsAdapter;
import com.ljr.ljrnews.base.BaseFragment;
import com.ljr.ljrnews.bean.NewsBean;
import com.ljr.ljrnews.utils.AppNetConfig;
import com.ljr.ljrnews.utils.CacheUtils;
import com.ljr.ljrnews.utils.Util;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by LinJiaRong on 2017/5/16.
 * TODO：
 */

public class HotFragment extends BaseFragment {

    private static final String TAG = "HotFragment";
    private static final String TYPE = "hot";
    @ViewInject(R.id.news_recyclerview)
    private RecyclerView mNewsRecyclerview;
    @ViewInject(R.id.refresh)
    private SwipeRefreshLayout mRefreshLayout;
    private NewsAdapter mAdapter;
    private NewsBean mNewsBean;
    String url = AppNetConfig.getURL(TYPE);

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.tab_news, null);
        x.view().inject(this, view);
        mNewsRecyclerview.setLayoutManager(new GridLayoutManager(mContext, 1));
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        String saveJson = CacheUtils.getString(mContext, url);
        if(!TextUtils.isEmpty(saveJson)){
            LogUtil.e("缓存的数据");
            parseData(saveJson);
        }
        LogUtil.e("第一次获取数据");
        getBeanFromNet();
        initRefresh();
    }


    public void getBeanFromNet() {
        /**
         * 使用xUtils3联网获取数据
         */


        Log.d(TAG, "initData: " + url);
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "onSuccess: " + result);
                //解析数据
                parseData(result);
                //缓存数据
                CacheUtils.putString(mContext,url,result);

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

    private void parseData(String result) {
        mNewsBean = new Gson().fromJson(result, NewsBean.class);
        NewsBean.ResultBean bean = mNewsBean.result;
        mAdapter = new NewsAdapter(mContext, bean);
        mNewsRecyclerview.setAdapter(mAdapter);
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

                getBeanFromNet();
                Log.e(TAG, "run: 刷新完成!");
            }
        }).start();
        mRefreshLayout.setRefreshing(false);
        Util.toast("刷新成功！", false);
    }
}
