package com.ljr.ljrnews.base;

import android.app.Application;
import android.content.Context;

import org.xutils.x;

import cn.sharesdk.framework.ShareSDK;


/**
 * Created by LinJiaRong on 2017/5/16.
 * TODO：
 */

public class LjrNewsApplication extends Application{
    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        ShareSDK.initSDK(this);
        sContext = this.getApplicationContext();
        x.Ext.setDebug(true);
        x.Ext.init(this);
    }
}
