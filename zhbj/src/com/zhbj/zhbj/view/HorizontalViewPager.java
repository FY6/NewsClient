package com.zhbj.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 2016-8-28 ����4:02:28 ���� HorizontalViewPager.java
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
	 * �¼��ַ������󸸿ؼ������ڿؼ��Ƿ������¼�
	 * 
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (getCurrentItem() != 0) {
			// ���󸸿ؼ������ڿؼ���Ҫ�����¼�
			getParent().requestDisallowInterceptTouchEvent(true);
		} else {
			// ����ǵ�һҳ��Ҫ��ʾ����������󸸿ؼ������ڿؼ������¼�
			getParent().requestDisallowInterceptTouchEvent(false);
		}

		return super.dispatchTouchEvent(ev);
	}
}
