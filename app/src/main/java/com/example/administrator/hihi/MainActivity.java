package com.example.administrator.hihi;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.administrator.hihi.fragment.ContentFragment;
import com.example.administrator.hihi.fragment.LeftMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends Activity {

    private final String LEFTMENU="leftmenu";
    private final String CONTENT="content";
    public SlidingMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        initMenu();
        initFragment();
    }

    private void initMenu() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        // 设置滑动菜单视图的宽度
        menu.setBehindOffset(300);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        /**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         */
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        menu.setMenu(R.layout.left_menu);
    }

    /**
     * 初始化fragment, 将fragment数据填充给布局文件
     */
    private void initFragment(){
        FragmentManager fm=getFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        transaction.replace(R.id.fl_left_menu, new LeftMenuFragment(),
                LEFTMENU);// 用fragment替换framelayout
        transaction.replace(R.id.fl_content, new ContentFragment(),
                CONTENT);

        transaction.commit();// 提交事务
        // Fragment leftMenuFragment = fm.findFragmentByTag(FRAGMENT_LEFT_MENU);
    }
    //获取侧边栏对象
    public LeftMenuFragment getLeftMenuFragment(){
        FragmentManager fm=getFragmentManager();
        LeftMenuFragment fragment= (LeftMenuFragment) fm.findFragmentByTag(LEFTMENU);
        return fragment;
    }
    //获取主页面fragment
    public ContentFragment getContentFragment(){
        FragmentManager fm=getFragmentManager();
        ContentFragment fragment= (ContentFragment) fm.findFragmentByTag(LEFTMENU);
        return fragment;
    }

}
