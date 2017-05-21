package com.ljr.ljrnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ljr.ljrnews.R;
import com.ljr.ljrnews.bean.NewsBean;
import com.ljr.ljrnews.utils.Util;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by LinJiaRong on 2017/5/18.
 * TODO：
 */

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "RecyclerAdapter";

    private final List<NewsBean.ResultBean.DataBean> mdata;
    private Context mContext;
    private TextView mNewsTitle;
    private ImageView mImg1;
    private ImageView mImg2;
    private ImageView mImg3;
    private ImageView mCollect;
    private TextView mNewsTime;
    private TextView mNewsAuther;
    private LinearLayout mLinearLayout;

    public RecyclerAdapter(Context context, List<NewsBean.ResultBean.DataBean> data) {
        this.mContext = context;
        this.mdata = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.news_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.setData(position);
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }


     class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View view) {
            super(view);
            mNewsTitle = (TextView) view.findViewById(R.id.news_title);
            mImg1 = (ImageView) view.findViewById(R.id.img1);
            mImg2 = (ImageView) view.findViewById(R.id.img2);
            mImg3 = (ImageView) view.findViewById(R.id.img3);
            mCollect = (ImageView)view.findViewById(R.id.collect);
            mNewsTime = (TextView) view.findViewById(R.id.news_time);
            mNewsAuther = (TextView) view.findViewById(R.id.news_auther);
            mLinearLayout= (LinearLayout) view.findViewById(R.id.news_list_main);
        }

         public void setData(final int position) {
             NewsBean.ResultBean.DataBean newsBean = mdata.get(position);
             mNewsTitle.setText(newsBean.title);
             Glide.with(mContext).load(newsBean.thumbnail_pic_s).into(mImg1);
             if(newsBean.thumbnail_pic_s02 != null){
                 Glide.with(mContext).load(newsBean.thumbnail_pic_s02).into(mImg2);
             }else{
                 mImg2.setVisibility(View.GONE);
             }
             if(newsBean.thumbnail_pic_s03 != null){
                 Glide.with(mContext).load(newsBean.thumbnail_pic_s03).into(mImg3);
             }else{
                 mImg3.setVisibility(View.GONE);
             }

             mNewsAuther.setText(newsBean.author_name);
             mNewsTime.setText(newsBean.date);
             mCollect.setImageResource(R.drawable.favourite_press);
             mCollect.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     mCollect.setImageResource(R.drawable.favourite_normal);

                     Util.toast("已收藏！！",false);
                 }
             });
             mLinearLayout.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                  //   Util.toast("已收藏！！",false);
                     if(onClickRecyclerView!=null){
                         onClickRecyclerView.onClick(position);
                     }
                 }
             });

         }
     }
     public interface OnClickRecyclerView{
         void onClick(int positon);
     }
    public void setOnClickRecyclerView(OnClickRecyclerView onClickRecyclerView) {
        this.onClickRecyclerView = onClickRecyclerView;
    }

    private OnClickRecyclerView onClickRecyclerView;
}
