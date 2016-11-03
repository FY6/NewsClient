package com.zhbj.zhbj.base.newsdetail.pager.impl;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhbj.zhbj.R;
import com.zhbj.zhbj.base.newsdetail.pager.BaseMenuDetailPager;

/**
 * 2016-8-26 ����1:27:13 ���� TopicMenuDetailPager.java
 * 
 * ר��
 * 
 **/
public class TopicMenuDetailPager extends BaseMenuDetailPager {

	public TopicMenuDetailPager(Activity mActivity) {
		super(mActivity);
		initData();
	}

	@Override
	public View initViews() {
		TextView textView = new TextView(mActivity);
		textView.setText("ר��");
		textView.setGravity(Gravity.CENTER);
		textView.setTextSize(25);
		textView.setTextColor(Color.RED);
		mRootView = textView;
		return mRootView;
	}

	@Override
	public void initData() {
	}

}
