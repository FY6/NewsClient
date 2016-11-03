package com.zhbj.zhbj.base.content.pager.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.zhbj.zhbj.base.content.pager.BasePager;

/**
 * 2016-8-24 ����3:36:49 ���� HomePager.java
 * 
 * �ǻ۷���ҳ��
 * 
 **/
public class SmartServicePager extends BasePager {

	public SmartServicePager(Activity mActivity) {
		super(mActivity);
		// initData();
	}

	@Override
	public void initData() {

		mTitle.setText("����");
		setSlidingMenuEnable(true);

		SlidingMenu slidingMenu = mActivity.getSlidingMenu();
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		TextView textView = new TextView(mActivity);
		textView.setText("�ǻ۷���");
		textView.setTextSize(25);
		textView.setTextColor(Color.RED);
		textView.setGravity(Gravity.CENTER);

		flContent.addView(textView);
	}
}
