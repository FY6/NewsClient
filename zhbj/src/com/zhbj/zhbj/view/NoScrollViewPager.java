package com.zhbj.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 2016-8-24 下午4:24:34 创建 NoScrollViewPager.java
 * 
 * 去掉ViewPager的滑动事件
 * 
 **/
public class NoScrollViewPager extends ViewPager {

	public NoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollViewPager(Context context) {

		super(context);
	}

	/**
	 * 
	 * 因为父类拦截touchEvent ，子类就不能处理touchevent事件
	 * 
	 * 所以我们要重写父类onInterceptTouchEvent方法，不让父类拦截
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;
	}

	/**
	 * 重新onTouchEvent事件，什么都不用做
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return false;
	}
}
