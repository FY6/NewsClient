package com.zhbj.zhbj.base.content.pager.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.zhbj.zhbj.base.content.pager.BasePager;

/**
 * 2016-8-24 下午3:36:49 创建 HomePager.java
 * 
 * 主页面
 **/
public class HomePager extends BasePager {

	public HomePager(Activity mActivity) {
		super(mActivity);
		// initData();
	}

	@Override
	public void initData() {
		mTitle.setText("智慧成都");
		btnMenu.setVisibility(View.GONE);
		setSlidingMenuEnable(false);

		btnMenu.setVisibility(View.GONE);
		TextView textView = new TextView(mActivity);
		textView.setText("主页");
		textView.setTextSize(25);
		textView.setTextColor(Color.RED);
		textView.setGravity(Gravity.CENTER);

		flContent.addView(textView);
	}
}
