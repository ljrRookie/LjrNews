package com.ljr.ljrnews.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_video, null);
        x.view().inject(this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet(refresh);
    }

    private void getDataFromNet(boolean refresh) {
        RequestParams params = new RequestParams("http://api.m.mtime.cn/PageSubArea/TrailerList.api");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("onSuccess: "+result);
                if(result != null){
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
        MovieAdapter movieAdapter = new MovieAdapter(mContext, trailersBeen);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mMovieRecyclerview.setLayoutManager(layoutManager);
        mMovieRecyclerview.setAdapter(movieAdapter);
        mMovieRecyclerview.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mTextView.setVisibility(View.GONE);


    }

    private List<MovieBean.TrailersBean> parseData(String result) {
        MovieBean movieBean = new Gson().fromJson(result, MovieBean.class);
        List<MovieBean.TrailersBean> trailers = movieBean.trailers;
        return trailers;
    }


}
