package com.ljr.ljrnews.utils;

/**
 * Created by LinJiaRong on 2017/5/16.
 * TODO：
 */

public class AppNetConfig {
    public static String getURL(String TYPE) {
        String URL = "http://v.juhe.cn/toutiao/index?type="+TYPE+"&key=bf37a556327520ca56aa1c4bc05b3f08";
        return URL;
    }


}
