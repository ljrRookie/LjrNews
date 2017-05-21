package com.ljr.ljrnews.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ljr.ljrnews.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class NewsWebActivity extends Activity {

    @Bind(R.id.title_left)
    ImageView mTitleLeft;
    @Bind(R.id.size)
    ImageView mSize;
    @Bind(R.id.title_setting)
    ImageView mShare;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.webview)
    WebView mWebview;
    @Bind(R.id.pb_loading)
    ProgressBar mPbLoading;
    private WebSettings mWebSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web);
        ButterKnife.bind(this);
        initView();
        getDataForNet();
    }

    private void getDataForNet() {
        String url = getIntent().getStringExtra("url");
        mWebSettings = mWebview.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setTextZoom(100);
        mWebview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mPbLoading.setVisibility(View.GONE);
            }
        });
        mWebview.loadUrl(url);

    }

    private void initView() {
        mTitleLeft.setImageResource(R.drawable.ic_back);
        mSize.setVisibility(View.VISIBLE);
        mPbLoading.setVisibility(View.VISIBLE);
        mShare.setImageResource(R.drawable.ic_share);
    }

    @OnClick({R.id.title_left, R.id.size, R.id.title_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.size:
                showChangeTextSizeDialog();
                break;
            case R.id.title_setting:
                showShare();
                break;
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("LjrNews");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("https://github.com/ljrRookie");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是款轻量级的新闻app(LjrNews)");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("https://github.com/ljrRookie");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是款轻量级的新闻app(LjrNews)");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("LjrNews");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("https://github.com/ljrRookie");

// 启动分享GUI
        oks.show(this);
    }
    private int tempSize = 2;
    private int realSize = tempSize;
    private void showChangeTextSizeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] item={"超大字体","大字体","正常字体","小字体","超小字体"};
        builder.setIcon(R.drawable.ic_action_name)
                .setTitle("设置文字大小")
                .setSingleChoiceItems(item, realSize, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tempSize = which;
                    }
                }).setNegativeButton("取消",null)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        realSize = tempSize;
                        changeTextSize(realSize);
                    }
                }).show();
    }

    private void changeTextSize(int realSize) {
        switch(realSize){
            case 0 ://超大字体
                mWebSettings.setTextZoom(200);
                break;
            case 1 :
                mWebSettings.setTextZoom(150);
                break;
            case 2 :
                mWebSettings.setTextZoom(100);
                break;
            case 3 :
                mWebSettings.setTextZoom(75);
                break;
            case 4 :
                mWebSettings.setTextZoom(50);
                break;

        }
    }
}
