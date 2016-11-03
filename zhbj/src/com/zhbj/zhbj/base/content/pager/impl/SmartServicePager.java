package com.zhbj.zhbj.base.content.pager.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.zhbj.zhbj.base.content.pager.BasePager;

/**
 * 2016-8-24 下午3:36:49 创建 HomePager.java
 * 
 * 智慧服务页面
 * 
 **/
public class SmartServicePager extends BasePager {

	public SmartServicePager(Activity mActivity) {
		super(mActivity);
		// initData();
	}

	@Override
	public void initData() {

		mTitle.setText("生活");
		setSlidingMenuEnable(true);

		SlidingMenu slidingMenu = mActivity.getSlidingMenu();
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		TextView textView = new TextView(mActivity);
		textView.setText("智慧服务");
		textView.setTextSize(25);
		textView.setTextColor(Color.RED);
		textView.setGravity(Gravity.CENTER);

		flContent.addView(textView);
	}
}
