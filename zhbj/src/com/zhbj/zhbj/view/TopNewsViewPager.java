package com.zhbj.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 2016-8-28 上午4:02:28 创建 HorizontalViewPager.java
 * 
 * 头条新闻图片
 **/
public class TopNewsViewPager extends ViewPager {

	private int startX;
	private int startY;

	public TopNewsViewPager(Context context, AttributeSet attrs) {

		super(context, attrs);
	}

	public TopNewsViewPager(Context context) {

		super(context);
	}

	/**
	 * 事件分发, 请求父控件及祖宗控件是否拦截事件
	 * 
	 * 1. 右划, 而且是第一个页面, 需要父控件拦截
	 * 
	 * 2. 左划, 而且是最后一个页面, 需要父控件拦截
	 * 
	 * 3. 上下滑动, 需要父控件拦截
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		getParent().requestDisallowInterceptTouchEvent(true);// 不要拦截,
		// 这样是为了保证ACTION_MOVE调用

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:

			// 写在这里可能会出现问题
			// getParent().requestDisallowInterceptTouchEvent(true);// 不要拦截,
			// 这样是为了保证ACTION_MOVE调用

			startX = (int) ev.getRawX();
			startY = (int) ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			int endY = (int) ev.getRawY();
			int endX = (int) ev.getRawX();

			if (Math.abs(endX - startX) > Math.abs(endY - startY)) {// 左右滑动

				if (endX > startX) {// 右滑
					// 右划, 而且是第一个页面, 需要父控件拦截
					if (getCurrentItem() == 0) {
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				} else {// 左划, 而且是最后一个页面, 需要父控件拦截
					if (getCurrentItem() == getAdapter().getCount() - 1) {
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				}

			} else {// 上下滑动
				getParent().requestDisallowInterceptTouchEvent(false);
			}

			break;
		default:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}
}
