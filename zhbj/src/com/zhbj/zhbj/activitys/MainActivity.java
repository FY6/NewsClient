package com.zhbj.zhbj.activitys;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.zhbj.zhbj.R;
import com.zhbj.zhbj.fragments.ContentFragment;
import com.zhbj.zhbj.fragments.LeftMenuFragment;

/**
 * 2016-8-23 上午12:29:29 创建 MainActivity
 * 
 * 主界面
 **/
public class MainActivity extends SlidingFragmentActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		initViews();
		initFragments();
	}

	/**
	 * 初始化view和SlidingMenu的布局
	 */
	private void initViews() {
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.leftmenu);// 设置侧边栏布局
		SlidingMenu slidingMenu = getSlidingMenu();// 获取侧边栏对象
		slidingMenu.setMode(SlidingMenu.LEFT);// 设置只有左边菜单
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// 设置全屏显示
		// 屏幕适配
		int width = getWindowManager().getDefaultDisplay().getWidth();
		slidingMenu.setBehindOffset(200 * width / 320);// 设置预留屏幕宽度
	}

	/**
	 * 初始化Fragment
	 */
	private void initFragments() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		transaction
				.replace(R.id.fl_main, new ContentFragment(), "MainFragment");
		transaction.replace(R.id.fl_leftmenu, new LeftMenuFragment(),
				"LeftMenuFragment");
		transaction.commit();
	}

}
