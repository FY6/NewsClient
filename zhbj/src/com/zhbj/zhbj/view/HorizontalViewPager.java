package com.zhbj.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 2016-8-28 上午4:02:28 创建 HorizontalViewPager.java
 * 
 **/
public class HorizontalViewPager extends ViewPager {

	public HorizontalViewPager(Context context, AttributeSet attrs) {

		super(context, attrs);
	}

	public HorizontalViewPager(Context context) {

		super(context);
	}

	/**
	 * 事件分发，请求父控件和祖宗控件是否拦截事件
	 * 
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (getCurrentItem() != 0) {
			// 请求父控件和祖宗控件不要拦截事件
			getParent().requestDisallowInterceptTouchEvent(true);
		} else {
			// 如果是第一页需要显示侧边栏，请求父控件和祖宗控件拦截事件
			getParent().requestDisallowInterceptTouchEvent(false);
		}

		return super.dispatchTouchEvent(ev);
	}
}
