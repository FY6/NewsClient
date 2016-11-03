package com.zhbj.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 2016-8-24 ����4:24:34 ���� NoScrollViewPager.java
 * 
 * ȥ��ViewPager�Ļ����¼�
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
	 * ��Ϊ��������touchEvent ������Ͳ��ܴ���touchevent�¼�
	 * 
	 * ��������Ҫ��д����onInterceptTouchEvent���������ø�������
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;
	}

	/**
	 * ����onTouchEvent�¼���ʲô��������
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return false;
	}
}
