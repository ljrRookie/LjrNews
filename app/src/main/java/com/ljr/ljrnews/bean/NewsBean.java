package com.ljr.ljrnews.bean;

import java.util.List;

/**
 * Created by LinJiaRong on 2017/5/16.
 * TODO：
 */

public class NewsBean {

    public int error_code;
    public String reason;
    public ResultBean result;

    public static class ResultBean {
        public List<DataBean> getData() {
            return data;
        }

        public String stat;
        public List<DataBean> data;

        public static class DataBean {
            /**
             * author_name : 最后一投
             * category : 体育
             * date : 2017-05-16 13:05
             * thumbnail_pic_s : http://09.imgmini.eastday.com/mobile/20170516/20170516130525_8e6746a33bf2341e10d2397471c2c87c_2_mwpm_03200403.jpeg
             * thumbnail_pic_s02 : http://09.imgmini.eastday.com/mobile/20170516/20170516130525_8e6746a33bf2341e10d2397471c2c87c_1_mwpm_03200403.jpeg
             * thumbnail_pic_s03 : http://09.imgmini.eastday.com/mobile/20170516/20170516130525_8e6746a33bf2341e10d2397471c2c87c_3_mwpm_03200403.jpeg
             * title : 他比朱荣振更强，李楠却把山东队抬进国家队，夹带私货害人害己！
             * uniquekey : f5cb41e0dd28e202843c5e6cc64e22b8
             * url : http://mini.eastday.com/mobile/170516130525033.html
             */

            public String author_name;
            public String category;
            public String date;
            public String thumbnail_pic_s;
            public String thumbnail_pic_s02;
            public String thumbnail_pic_s03;
            public String title;
            public String uniquekey;
            public String url;
        }
    }
}
