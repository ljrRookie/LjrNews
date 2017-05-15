package com.ljr.ljrnews.activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ljr.ljrnews.R;
import com.ljr.ljrnews.base.BaseFragment;
import com.ljr.ljrnews.fragment.NewsFragment;
import com.ljr.ljrnews.fragment.ReadFragment;
import com.ljr.ljrnews.fragment.VideoFragment;
import com.ljr.ljrnews.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.nav_view)
    NavigationView mNavView;
    @Bind(R.id.main_drawerlayout)
    DrawerLayout mMainDrawerlayout;
    @Bind(R.id.title_left)
    ImageView mTitleLeft;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.title_setting)
    ImageView mTitleSetting;
    @Bind(R.id.fragment)
    FrameLayout mFragment;
    @Bind(R.id.rb_news)
    RadioButton mRbNews;
    @Bind(R.id.rb_live)
    RadioButton mRbLive;
    @Bind(R.id.rb_read)
    RadioButton mRbRead;
    @Bind(R.id.rg_main)
    RadioGroup mRgMain;
    private ArrayList<BaseFragment> mBaseFragment;
    //Fragment的对应位置数字
    private int mPosition;
    //当前选中的Fragment
    private Fragment mContent;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTitleSetting.setVisibility(View.INVISIBLE);
        initNav();
        initFragment();
        initListener();

    }

    private void initListener() {
        mRgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group,  int checkedId) {

                switch (checkedId) {
                    case R.id.rb_news:
                        mPosition = 0;
                        break;
                    case R.id.rb_live:
                        mPosition = 1;
                        break;
                    case R.id.rb_read:
                        mPosition = 2;
                        break;
                    default:
                        mPosition = 0;
                        break;
                }
                //根据位置得到对应的fragment
                BaseFragment toFragment = getFragment();
                //切换fragment
                //  switchFragment(toFragment); 会导致每次切换fragment切换都重新初始化
                switchContent(mContent,toFragment);
            }
        });
        //设置默认选中常用框架
        mRgMain.check(R.id.rb_news);
    }
    //根据位置得到对应的fragment
    private BaseFragment getFragment() {
        BaseFragment fragment = mBaseFragment.get(mPosition);
        return fragment;
    }
    /**
     *
     * @param fromFragment 当前显示的Fragment，准备被隐藏
     * @param toFragment 马上要切换的Fragment，准备显示
     */
    public void switchContent(Fragment fromFragment, Fragment toFragment) {
        if(fromFragment != toFragment){
            mContent = toFragment;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if(!toFragment.isAdded()){
                //toFragment没有被添加
                //1.先隐藏fromFragment
                if(fromFragment != null){
                    ft.hide(fromFragment);
                }
                //2.再添加toFragment
                if(toFragment != null){
                    ft.add(R.id.fragment,toFragment).commit();
                }
            }else{
                //toFragment已被添加
                //1.先隐藏fromFragment
                if(fromFragment != null){
                    ft.hide(fromFragment);
                }
                //2.再显示toFragment
                if(toFragment != null){
                    ft.show(toFragment).commit();
                }
            }
        }
    }
    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new NewsFragment());
        mBaseFragment.add(new VideoFragment());
        mBaseFragment.add(new ReadFragment());
        Log.d(TAG, "initFragment: "+mBaseFragment.toString());
    }


    private void initNav() {
        //点击菜单内容进行逻辑业务
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.me:
                        ToastUtil.showToast(MainActivity.this, "个人中心");
                        break;
                    case R.id.mycollect:
                        ToastUtil.showToast(MainActivity.this, "我的收藏");
                        break;
                    case R.id.more:
                        ToastUtil.showToast(MainActivity.this, "更多功能");
                        break;
                    case R.id.live:
                        ToastUtil.showToast(MainActivity.this, "直播间");
                        break;
                    case R.id.setting:
                        ToastUtil.showToast(MainActivity.this, "设置");
                        break;
                    case R.id.call:
                        ToastUtil.showToast(MainActivity.this, "联系我们");
                        break;
                    default:
                        break;
                }
                //关闭滑动菜单
                mMainDrawerlayout.closeDrawers();
                return true;
            }
        });
    }

    @OnClick(R.id.title_left)
    public void onClick() {
        mMainDrawerlayout.openDrawer(GravityCompat.START);
    }

}

