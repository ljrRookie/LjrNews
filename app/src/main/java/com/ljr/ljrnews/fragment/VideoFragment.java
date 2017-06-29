package com.ljr.ljrnews.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ljr.ljrnews.R;
import com.ljr.ljrnews.adapter.MovieAdapter;
import com.ljr.ljrnews.base.BaseFragment;
import com.ljr.ljrnews.bean.MovieBean;
import com.ljr.ljrnews.utils.CacheUtils;
import com.ljr.ljrnews.utils.Util;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;


/**
 * Created by LinJiaRong on 2017/5/15.
 * TODO：
 */

public class VideoFragment extends BaseFragment {
    @ViewInject(R.id.movie_recyclerview)
    RecyclerView mMovieRecyclerview;
    @ViewInject(R.id.movie_loading)
    ProgressBar mProgressBar;
    @ViewInject(R.id.loading_text)
    TextView mTextView;
    @ViewInject(R.id.movie_refresh)
    SwipeRefreshLayout mMovieRefresh;
    private boolean refresh = false;
    private MovieAdapter mMovieAdapter;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_video, null);
        x.view().inject(this,view);
        mMovieRefresh.setColorSchemeResources(R.color.colorAccent);
        mMovieRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        String saveResult = CacheUtils.getString(mContext, "http://api.m.mtime.cn/PageSubArea/TrailerList.api");
        if(!TextUtils.isEmpty(saveResult)){
            List<MovieBean.TrailersBean> trailersBeen = parseData(saveResult);
            showData(trailersBeen);
            LogUtil.e("从缓存中获取数据"+saveResult);
        }else{
            getDataFromNet(refresh);
        }

    mMovieAdapter.setOnClickMovieItem(new MovieAdapter.OnClickMovieItem() {
        @Override
        public void onClick(int position) {

        }
    });
    }

    private void getDataFromNet(boolean refresh) {
        RequestParams params = new RequestParams("http://api.m.mtime.cn/PageSubArea/TrailerList.api");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("onSuccess: "+result);
                if(result != null){
                    //缓存数据
                    CacheUtils.putString(mContext,"http://api.m.mtime.cn/PageSubArea/TrailerList.api",result);
                    LogUtil.e("缓存数据");
                    //解析数据
                    List<MovieBean.TrailersBean> trailersBeen = parseData(result);
                    showData(trailersBeen);

                }else{
                    Toast.makeText(mContext, "无数据！！！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("onError:"+ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("onCancelled:"+cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e("onFinished");
            }
        });
    }

    private void showData(List<MovieBean.TrailersBean> trailersBeen) {
        mMovieAdapter = new MovieAdapter(mContext, trailersBeen);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mMovieRecyclerview.setLayoutManager(layoutManager);
        mMovieRecyclerview.setAdapter(mMovieAdapter);
        mMovieRecyclerview.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mTextView.setVisibility(View.GONE);


    }

    private List<MovieBean.TrailersBean> parseData(String result) {
        MovieBean movieBean = new Gson().fromJson(result, MovieBean.class);
        List<MovieBean.TrailersBean> trailers = movieBean.trailers;
        return trailers;
    }

    private void refreshData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getDataFromNet(refresh);

            }
        }).start();
        mMovieRefresh.setRefreshing(false);
        Util.toast("刷新成功！", false);
    }
}
