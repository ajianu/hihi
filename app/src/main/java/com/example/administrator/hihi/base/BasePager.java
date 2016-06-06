package com.example.administrator.hihi.base;


import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.hihi.MainActivity;
import com.example.administrator.hihi.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * 主页下5个子页面的基类
 */
public class BasePager {

    public Activity mActivity;
    public View mRootView;// 布局对象

    public TextView tvTitle;// 标题对象

    public FrameLayout flContent;// 内容

    public ImageButton btnMenu;// 菜单按钮

    public ViewPager mViewPager;

    public BasePager(Activity activity) {
        mActivity = activity;
        initViews();
    }

    /**
     * 初始化布局
     */
    public void initViews() {
        mRootView = View.inflate(mActivity, R.layout.base_pager, null);
        tvTitle = (TextView) mRootView.findViewById(R.id.tv_title);
        flContent = (FrameLayout) mRootView.findViewById(R.id.fl_content);
        btnMenu = (ImageButton) mRootView.findViewById(R.id.btn_menu);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSlidingMenu();
            }
        });
       // View view = View.inflate(mActivity, R.layout.news_menu_detail,null);
       // mViewPager= (ViewPager) view.findViewById(R.id.vp_menu_detail);
    }

    /**
     * 切换SlidingMenu的状态
     */
    protected void toggleSlidingMenu() {
        MainActivity mainUi = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUi.menu;
        slidingMenu.toggle();// 切换状态, 显示时隐藏, 隐藏时显示
    }



    /**
     * 初始化数据
     */
    public void initData() {

    }

    /**
     * 设置侧边栏开启或关闭
     */
    public void setSlidingMenuEnable(boolean enable) {
        MainActivity mainUi = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUi.menu;
        if (enable) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

}
