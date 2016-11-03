package com.zhbj.zhbj.base.content.pager.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.zhbj.zhbj.base.content.pager.BasePager;

/**
 * 2016-8-24 ����3:36:49 ���� HomePager.java
 * 
 * ����ҳ��
 **/
public class GovAffairsPager extends BasePager {

	public GovAffairsPager(Activity mActivity) {
		super(mActivity);
		// initData();
	}

	@Override
	public void initData() {

		mTitle.setText("�˿ڹ���");
		setSlidingMenuEnable(true);

		TextView textView = new TextView(mActivity);
		textView.setText("����");
		textView.setTextSize(25);
		textView.setTextColor(Color.RED);
		textView.setGravity(Gravity.CENTER);

		flContent.addView(textView);
	}

}
