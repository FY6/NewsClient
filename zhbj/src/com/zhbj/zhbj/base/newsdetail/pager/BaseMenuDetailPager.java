package com.zhbj.zhbj.base.newsdetail.pager;

import android.app.Activity;
import android.view.View;

/**
 * 2016-8-26 ����12:48:40 ���� BaseMenuDetalPager.java
 * 
 * �˵�����ҳ����:�������ҳ��Ĳ���ֻ��һ��ViewPager
 **/
public abstract class BaseMenuDetailPager {
	public Activity mActivity;
	public View mRootView;

	public BaseMenuDetailPager(Activity mActivity) {
		super();
		this.mActivity = mActivity;
		mRootView = initViews();
	}

	/**
	 * �������ʵ��
	 * 
	 * @return
	 */
	public abstract View initViews();

	/**
	 * ��ʼ������
	 * 
	 */
	public void initData() {
	}

}
