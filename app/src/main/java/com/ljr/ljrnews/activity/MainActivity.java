package com.ljr.ljrnews.activity;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.ljr.ljrnews.utils.Util;

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
    private Fragment mCurrentFragment;
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
            public void onCheckedChanged(RadioGroup group, int checkedId) {

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
                switchContent(mCurrentFragment, toFragment);
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
     * @param fromFragment 当前显示的Fragment，准备被隐藏
     * @param toFragment   马上要切换的Fragment，准备显示
     */
    public void switchContent(Fragment fromFragment, Fragment toFragment) {
        if (fromFragment != toFragment) {
            mCurrentFragment = toFragment;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (!toFragment.isAdded()) {
                //toFragment没有被添加
                //1.先隐藏fromFragment
                if (fromFragment != null) {
                    ft.hide(fromFragment);
                }
                //2.再添加toFragment
                if (toFragment != null) {
                    ft.add(R.id.fragment, toFragment).commit();
                }
            } else {
                //toFragment已被添加
                //1.先隐藏fromFragment
                if (fromFragment != null) {
                    ft.hide(fromFragment);
                }
                //2.再显示toFragment
                if (toFragment != null) {
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
        Log.d(TAG, "initFragment: " + mBaseFragment.toString());
    }


    private void initNav() {
        //点击菜单内容进行逻辑业务
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.me:

                        break;
                    case R.id.mycollect:
                        Util.showToast(MainActivity.this, "我的收藏");
                        break;
                    case R.id.more:
                        Util.showToast(MainActivity.this, "更多功能");
                        break;
                    case R.id.live:
                        Util.showToast(MainActivity.this, "直播间");
                        break;
                    case R.id.setting:
                        Util.showToast(MainActivity.this, "设置");
                        break;
                    case R.id.call:
                        callContact();
                        Util.showToast(MainActivity.this, "联系我们");
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

    private void callContact() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("联系客服")
                .setIcon(R.drawable.ic_action_name)
                .setMessage("是否现在联系客服？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String phone = "15622732935";
                        //使用隐式意图，系统系统拨号应用界面
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + phone));
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", null).show();
    }

    @OnClick(R.id.title_left)
    public void onClick() {
        mMainDrawerlayout.openDrawer(GravityCompat.START);
        isLogin();
    }

    public void isLogin() {
        SharedPreferences sp = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String name = sp.getString("name", "");
        if (TextUtils.isEmpty(name)) {
            //本地无登录信息，弹出登录框
            Login();
        } else {
            //本地有登录信息，直接加载用户信息
            loadingUserInfo();
        }
    }

    private void loadingUserInfo() {

    }

    private void Login() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setIcon(R.drawable.ic_action_name)
                .setMessage("请进入登录页面")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }
                })
                .setCancelable(false)
                .show();
    }
}

