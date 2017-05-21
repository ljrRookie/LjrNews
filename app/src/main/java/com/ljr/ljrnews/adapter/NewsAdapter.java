package com.ljr.ljrnews.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ljr.ljrnews.R;
import com.ljr.ljrnews.activity.NewsWebActivity;
import com.ljr.ljrnews.bean.NewsBean;
import com.ljr.ljrnews.utils.Util;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnLoadImageListener;


import java.util.ArrayList;


import java.util.List;

/**
 * Created by LinJiaRong on 2017/5/17.
 * TODO：
 */

public class NewsAdapter extends RecyclerView.Adapter{
    private static final String TAG = "NewsAdapter";
    private static final int LIST = 1;
    private Context mContext;
    private NewsBean.ResultBean mNewsBean;
    private LayoutInflater mInflater;

    /**
     * 广告条幅类型
     */
    public static final int BANNER = 0;

    /**
     * 当前类型
     */
    private int currentType = BANNER;
    public NewsAdapter(Context context, NewsBean.ResultBean newsBean) {
        this.mContext = context;
        this.mNewsBean = newsBean;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(mContext, mInflater.inflate(R.layout.news_banner, null));
        } else if(viewType == LIST){
            return new ListViewHolder(mContext, mInflater.inflate(R.layout.news_list, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(mNewsBean.data);
        } else if (getItemViewType(position) == LIST) {
            ListViewHolder listViewHolder = (ListViewHolder) holder;
            listViewHolder.setData(mNewsBean.data);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        switch(position){
            case BANNER:
                currentType = BANNER;
                break;
            case LIST:
                currentType = LIST;
                break;
            default:
                   break;
        }
        return currentType;
    }

    class BannerViewHolder extends RecyclerView.ViewHolder{
        private Context mContext;
        private Banner banner;

        public BannerViewHolder(Context context ,View view) {
            super(view);
            this.mContext =context;
            this.banner = (Banner) view.findViewById(R.id.news_banner);
        }

        public void setData(List<NewsBean.ResultBean.DataBean> data) {
            //设置banner属性
            //得到图片集合地址
            ArrayList<String> imgsURL = new ArrayList<>();
            for(int i=0;i<4;i++){
                String imgURL = data.get(i).thumbnail_pic_s;
                imgsURL.add(imgURL);
            }
            banner.setImages(imgsURL, new OnLoadImageListener() {
                @Override
                public void OnLoadImage(ImageView view, Object url) {
                    Glide.with(mContext).load(url).into(view);
                }
            });
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            banner.setBannerAnimation(Transformer.Accordion);
            banner.setImages(imgsURL);

            //设置item的点击事件
            banner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
                    Util.toast("position==" + position,false);
                }
            });

        }

    }

    private class ListViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView mRecyclerView;
        private Context mContext;
        private RecyclerAdapter mRecyclerAdapter;

        public ListViewHolder(Context context, View view) {
            super(view);
            this.mContext = context;
            this.mRecyclerView = (RecyclerView) view.findViewById(R.id.news_list);
        }

        public void setData(final List<NewsBean.ResultBean.DataBean> data) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
            mRecyclerAdapter = new RecyclerAdapter(mContext, data);
            mRecyclerView.setAdapter(mRecyclerAdapter);
            mRecyclerAdapter.setOnClickRecyclerView(new RecyclerAdapter.OnClickRecyclerView() {
                @Override
                public void onClick(int positon) {
                    Util.toast("已收藏！",false);
                }
            });
            mRecyclerAdapter.setOnClickRecyclerView(new RecyclerAdapter.OnClickRecyclerView() {
                @Override
                public void onClick(int positon) {
                    Util.toast("点击了第"+positon+"个",false);
                    Intent intent = new Intent(mContext, NewsWebActivity.class);
                    NewsBean.ResultBean.DataBean dataBean = data.get(positon);
                    String url = dataBean.url;
                    intent.putExtra("url",url);
                    mContext.startActivity(intent);

                }
            });
        }
    }
}
