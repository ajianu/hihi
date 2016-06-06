package com.example.administrator.hihi.base;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.hihi.R;
import com.example.administrator.hihi.bean.ListData;
import com.example.administrator.hihi.bean.NewsData.NewsTabData;
import com.example.administrator.hihi.bean.TabData.TabNewsData;
import com.example.administrator.hihi.bean.TabData.TopNewsData;
import com.example.administrator.hihi.global.GlobalContent;
import com.example.administrator.hihi.utils.BitmapUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/8.
 */
public class TabDetailPager extends BaseMenuDetailPager implements ViewPager.OnPageChangeListener{

    NewsTabData mTabData;
    private String mUrl;
    private ListData mTabDetailData;
    private ViewPager mViewPager;
    private TextView tvTitle;//头条新闻的标题
    private ArrayList<ListData.ListNewsData> mTopNewsList;//头条新闻数据集合
    private CirclePageIndicator mIndicator;//头条新闻位置指示器
    private ListView lvList;//新闻列表
    private ArrayList<TabNewsData> mNewsList;
    private NewsAdapter mNewsAdapter;
    private ArrayList<ListData> mTopdata;//顶部数据

    private int[] arr=new int[]{
            R.mipmap.a1, R.mipmap.a2,
            R.mipmap.a3, R.mipmap.a4,
            R.mipmap.a5,R.mipmap.a6,
            R.mipmap.a7,R.mipmap.a8,
            R.mipmap.a9,R.mipmap.a10};

    public TabDetailPager(Activity activity, NewsTabData newsTabData) {
        super(activity);
        mTabData = newsTabData;
        mUrl = GlobalContent.SERVER_URL2;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_news);
        tvTitle= (TextView) view.findViewById(R.id.tv_title);
        mIndicator= (CirclePageIndicator) view.findViewById(R.id.indicator);
        lvList= (ListView) view.findViewById(R.id.lv_list);
        return view;
    }

    @Override
    public void initData() {
        getDataFromServer();
    }

    private void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, mUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                parseData(result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }

    protected void parseData(String result) {
        Gson gson = new Gson();
        mTabDetailData = gson.fromJson(result, ListData.class);
        Log.i("页签详情解析", "" + mTabDetailData);
        mTopNewsList=mTabDetailData.authors;

        if (mTopNewsList!=null) {
            mViewPager.setAdapter(new TopNewsAdapter());
            tvTitle.setText(mTopNewsList.get().);
            mIndicator.setViewPager(mViewPager);
            mIndicator.setSnap(true);//支持快照显示
            mIndicator.setOnPageChangeListener(this);
            mIndicator.onPageSelected(0);//让指示器重新定位到第一个点
        }
        //填充新闻列表
        if (mNewsList!=null) {
            mNewsAdapter=new NewsAdapter();
            lvList.setAdapter(mNewsAdapter);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        TopNewsData topNewsData=mTopNewsList.get(position);
        tvTitle.setText(topNewsData.title);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 头条新闻适配器
     */
    class TopNewsAdapter extends PagerAdapter {
/*
        private BitmapUtils utils;
        public TopNewsAdapter(){
            utils=new BitmapUtils(mActivity);
        }*/
        @Override
        public int getCount() {
            return mTabDetailData.data.topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView image = new ImageView(mActivity);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            //image.setImageResource(R.drawable.topnews_item_default);
            //TopNewsData topNewsData=mTopNewsList.get(position);
            //utils.display(image,topNewsData.topimage);
            image.setImageBitmap(BitmapUtils.getBitmap(arr[position],mActivity));
            container.addView(image);
            return image;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    /**
    *新闻列表的适配器
    * */
    class NewsAdapter extends BaseAdapter{

        /*private BitmapUtils utils;
        public NewsAdapter(){
            utils=new BitmapUtils();
            utils.configDefaultLoadingImage(R.drawable.pic_item_list_default);
        }*/
        @Override
        public int getCount() {
            return mNewsList.size();
        }

        @Override
        public TabNewsData getItem(int position) {
            return mNewsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView==null){
                convertView=View.inflate(mActivity,R.layout.list_news_item,null);
                holder=new ViewHolder();
                holder.ivPic= (ImageView) convertView.findViewById(R.id.iv_pic);
                holder.tvTitle= (TextView) convertView.findViewById(R.id.tv_title);
                holder.tvDate= (TextView) convertView.findViewById(R.id.tv_date);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.ivPic.setScaleType(ImageView.ScaleType.FIT_XY);
            holder.ivPic.setImageBitmap(BitmapUtils.getBitmap(arr[position],mActivity));
            TabNewsData item=getItem(position);
            holder.tvTitle.setText(item.title);
            holder.tvDate.setText(item.pubdate);
            //utils.display(holder.ivPic,item.listimage);
            return convertView;
        }
    }

    static class ViewHolder{
        public TextView tvTitle;
        public TextView tvDate;
        public ImageView ivPic;

    }
}
