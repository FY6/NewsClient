package com.zhbj.zhbj.base.content.pager;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.zhbj.zhbj.R;
import com.zhbj.zhbj.activitys.MainActivity;
import com.zhbj.zhbj.fragments.LeftMenuFragment;

/**
 * 2016-8-24 ����3:31:27 ���� BasePager.java
 * 
 * ��ҳ��5����ҳ��Ļ��ࣺ
 * 
 * ��Ϊ5��ҳ�涼�Ǵ�ͬС�죬���Գ�ȡ��������е�view�ĳ�ʼ������base
 * 
 **/
public abstract class BasePager {
	public MainActivity mActivity;
	public View mRootView;// ���ֶ���
	public FrameLayout flContent;// ����

	public TextView mTitle;// ����
	public ImageButton btnMenu;// �˵���ť
	private FrameLayout btn_lvTogv;

	public BasePager(Activity mActivity) {
		this.mActivity = (MainActivity) mActivity;
		initViews();
	}

	/**
	 * ��ʼ��view����
	 * 
	 * mRootView:����ΪViewPager��ʾ�Ĳ��ֶ���
	 */
	public void initViews() {
		mRootView = View.inflate(mActivity, R.layout.basepager, null);
		flContent = (FrameLayout) mRootView.findViewById(R.id.fl_content);
		mTitle = (TextView) mRootView.findViewById(R.id.tv_title);
		btnMenu = (ImageButton) mRootView.findViewById(R.id.btn_nemu);
		btn_lvTogv = (FrameLayout) mRootView.findViewById(R.id.btn_lvTogv);

		listToGrid(btn_lvTogv);

		initListener();
	}

	private void initListener() {
		btnMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SlidingMenu slidingMenu = mActivity.getSlidingMenu();
				slidingMenu.showMenu();
			}
		});
	}

	/**
	 * �л�ListView��GridView
	 */
	public void listToGrid(View v) {
	}

	// ���ò�����Ŀ���״̬
	public void setSlidingMenuEnable(boolean enable) {
		if (enable) {
			SlidingMenu slidingMenu = mActivity.getSlidingMenu();
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		} else {
			SlidingMenu slidingMenu = mActivity.getSlidingMenu();
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}

	/**
	 * ��ȡleftMenufragment
	 * 
	 * @return
	 */
	public LeftMenuFragment getLeftMenuFragment() {
		FragmentManager fm = mActivity.getSupportFragmentManager();
		LeftMenuFragment leftMenufragment = (LeftMenuFragment) fm
				.findFragmentByTag("LeftMenuFragment");

		return leftMenufragment;
	}

	/**
	 * ������ʵ�֣��Լ���ʼ������
	 */
	public void initData() {
	}

}
