package com.zhbj.zhbj.base.content.pager.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.zhbj.zhbj.base.content.pager.BasePager;

/**
 * 2016-8-24 下午3:36:49 创建 HomePager.java
 * 
 * 
 * 设置页面
 **/
public class SettingPager extends BasePager {

	public SettingPager(Activity mActivity) {
		super(mActivity);
		// initData();
	}

	@Override
	public void initData() {
		mTitle.setText("设置");
		btnMenu.setVisibility(View.GONE);
		setSlidingMenuEnable(false);

		TextView textView = new TextView(mActivity);
		textView.setText("设置");
		textView.setTextSize(25);
		textView.setTextColor(Color.RED);
		textView.setGravity(Gravity.CENTER);

		flContent.addView(textView);
	}
}
