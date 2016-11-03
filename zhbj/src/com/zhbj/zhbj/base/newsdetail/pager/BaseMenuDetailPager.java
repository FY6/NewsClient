package com.zhbj.zhbj.base.newsdetail.pager;

import android.app.Activity;
import android.view.View;

/**
 * 2016-8-26 上午12:48:40 创建 BaseMenuDetalPager.java
 * 
 * 菜单详情页基类:其中这个页面的布局只有一个ViewPager
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
	 * 子类必须实现
	 * 
	 * @return
	 */
	public abstract View initViews();

	/**
	 * 初始化数据
	 * 
	 */
	public void initData() {
	}

}
