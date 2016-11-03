package com.zhbj.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 2016-8-28 ����4:02:28 ���� HorizontalViewPager.java
 * 
 * ͷ������ͼƬ
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
	 * �¼��ַ�, ���󸸿ؼ������ڿؼ��Ƿ������¼�
	 * 
	 * 1. �һ�, �����ǵ�һ��ҳ��, ��Ҫ���ؼ�����
	 * 
	 * 2. ��, ���������һ��ҳ��, ��Ҫ���ؼ�����
	 * 
	 * 3. ���»���, ��Ҫ���ؼ�����
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		getParent().requestDisallowInterceptTouchEvent(true);// ��Ҫ����,
		// ������Ϊ�˱�֤ACTION_MOVE����

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:

			// д��������ܻ��������
			// getParent().requestDisallowInterceptTouchEvent(true);// ��Ҫ����,
			// ������Ϊ�˱�֤ACTION_MOVE����

			startX = (int) ev.getRawX();
			startY = (int) ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			int endY = (int) ev.getRawY();
			int endX = (int) ev.getRawX();

			if (Math.abs(endX - startX) > Math.abs(endY - startY)) {// ���һ���

				if (endX > startX) {// �һ�
					// �һ�, �����ǵ�һ��ҳ��, ��Ҫ���ؼ�����
					if (getCurrentItem() == 0) {
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				} else {// ��, ���������һ��ҳ��, ��Ҫ���ؼ�����
					if (getCurrentItem() == getAdapter().getCount() - 1) {
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				}

			} else {// ���»���
				getParent().requestDisallowInterceptTouchEvent(false);
			}

			break;
		default:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}
}
