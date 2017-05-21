package com.ljr.ljrnews.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.ljr.ljrnews.base.LjrNewsApplication;

/**
 * Created by LinJiaRong on 2017/5/15.
 * TODO：
 */

public class Util {
    public static void showToast(Context context, CharSequence text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
        public static Context getContext() {
            return LjrNewsApplication.sContext;
        }
    public static void toast(String message,boolean isLengthLong){
        Toast.makeText(Util.getContext(), message,isLengthLong? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }
}
