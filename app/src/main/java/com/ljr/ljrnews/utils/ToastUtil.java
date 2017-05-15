package com.ljr.ljrnews.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by LinJiaRong on 2017/5/15.
 * TODO：
 */

public class ToastUtil {
    public static void showToast(Context context, CharSequence text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}
