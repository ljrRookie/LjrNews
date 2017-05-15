package com.ljr.ljrnews;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.ljr.ljrnews.activity.MainActivity;
import com.ljr.ljrnews.utils.CacheUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends Activity {

    public static final String START_MAIN = "start_main";
    @Bind(R.id.splash)
    RelativeLayout mSplash;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        //启动页---动画处理
        start();
    }

    private void start() {

        /**
         * target:代表动画执行在谁身上（找对象）
         * propertyName：动画类型（alpha：透明变化；tranlationX，translationY：平移动画；scale：缩放动画）
         * values：轨迹（选择起点和终点的变化）
         */
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mSplash, "alpha", 0f, 1f);
        objectAnimator.setDuration(1500);
        objectAnimator.start();
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onAnimationEnd(Animator animation) {
                boolean isStartMain = CacheUtils.getBoolean(SplashActivity.this, START_MAIN);
                if(isStartMain){
                    mIntent = new Intent(SplashActivity.this, MainActivity.class);
                }else{
                    mIntent =new Intent(SplashActivity.this, GuideActivity.class);
                }
                startActivity(mIntent);
                overridePendingTransition(R.anim.my_scale_action,
                        R.anim.my_alpha_action);
               finish();
            }
        });
    }
}

