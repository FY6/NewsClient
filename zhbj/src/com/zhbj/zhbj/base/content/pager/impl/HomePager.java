package com.zhbj.zhbj.base.content.pager.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.zhbj.zhbj.base.content.pager.BasePager;

/**
 * 2016-8-24 ����3:36:49 ���� HomePager.java
 * 
 * ��ҳ��
 **/
public class HomePager extends BasePager {

	public HomePager(Activity mActivity) {
		super(mActivity);
		// initData();
	}

	@Override
	public void initData() {
		mTitle.setText("�ǻ۳ɶ�");
		btnMenu.setVisibility(View.GONE);
		setSlidingMenuEnable(false);

		btnMenu.setVisibility(View.GONE);
		TextView textView = new TextView(mActivity);
		textView.setText("��ҳ");
		textView.setTextSize(25);
		textView.setTextColor(Color.RED);
		textView.setGravity(Gravity.CENTER);

		flContent.addView(textView);
	}
}
