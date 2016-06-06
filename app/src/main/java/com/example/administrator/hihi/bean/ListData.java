package com.example.administrator.hihi.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/1.
 */
public class ListData {

    public ArrayList<ListNewsData> authors;
    public ListTopData category;

    /*
        新闻数据
         */
    public class ListNewsData{
        public String id;
        public String avatar;//图片
        public boolean followStatus;//状态
        public String intro;//描述
        public String nickname;//昵称
        public String subscriptionNum;//订阅号

    }
    /*
    顶部数据
     */
    public class ListTopData{
        public String id;
        public String categoryName;

        @Override
        public String toString() {
            return categoryName;
        }
    }
}
