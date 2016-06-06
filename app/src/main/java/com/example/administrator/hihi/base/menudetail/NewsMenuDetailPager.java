package com.example.administrator.hihi.base.menudetail;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.administrator.hihi.MainActivity;
import com.example.administrator.hihi.R;
import com.example.administrator.hihi.base.BaseMenuDetailPager;
import com.example.administrator.hihi.base.TabDetailPager;
import com.example.administrator.hihi.bean.NewsData.NewsTabData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

/**
 * 菜单详情页-新闻
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager implements ViewPager.OnPageChangeListener{

	private ViewPager mViewPager;

	private ImageButton mButton;

	private ArrayList<TabDetailPager> mPagerList;

	private ArrayList<NewsTabData> mNewsTabData;// 页签网络数据

	private TabPageIndicator mIndicator;

	public NewsMenuDetailPager(Activity activity, ArrayList<NewsTabData> children) {
		super(activity);
		mNewsTabData=children;
	}

	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.news_menu_detail, null);
		mViewPager = (ViewPager) view.findViewById(R.id.vp_menu_detail);
		mButton= (ImageButton) view.findViewById(R.id.btn_next);
		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				nextPage();
			}
		});
		mIndicator = (TabPageIndicator) view.findViewById(R.id.indicator);

		//mViewPager.setOnPageChangeListener(this);
		mIndicator.setOnPageChangeListener(this);
		return view;
	}

	@Override
	public void initData() {
		mPagerList = new ArrayList<TabDetailPager>();
		// 初始化页签数据
		for (int i = 0; i < mNewsTabData.size(); i++) {
			TabDetailPager pager = new TabDetailPager(mActivity,mNewsTabData.get(i));
			mPagerList.add(pager);
		}

		mViewPager.setAdapter(new MenuDetailAdapter());
		mIndicator.setViewPager(mViewPager);//必须在viewpager设置完adapter后才能调用
	}

	// 跳转下一个页面
	public void nextPage() {
		int currentItem = mViewPager.getCurrentItem();
		mViewPager.setCurrentItem(++currentItem);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		MainActivity mainUi= (MainActivity) mActivity;
		SlidingMenu slidingMenu=mainUi.menu;
		if (position==0){
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}else{
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	class MenuDetailAdapter extends PagerAdapter {

		/**
		 * 重写此方法,返回页面标题,用于viewpagerIndicator的页签显示
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			return mNewsTabData.get(position).title;
		}
		@Override
		public int getCount() {
			return mPagerList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			TabDetailPager pager = mPagerList.get(position);
			container.addView(pager.mRootView);
			pager.initData();
			return pager.mRootView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

}
