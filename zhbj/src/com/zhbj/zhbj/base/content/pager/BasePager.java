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
 * 2016-8-24 下午3:31:27 创建 BasePager.java
 * 
 * 主页下5个子页面的基类：
 * 
 * 因为5个页面都是大同小异，所以抽取基类把所有的view的初始化交给base
 * 
 **/
public abstract class BasePager {
	public MainActivity mActivity;
	public View mRootView;// 布局对象
	public FrameLayout flContent;// 内容

	public TextView mTitle;// 标题
	public ImageButton btnMenu;// 菜单按钮
	private FrameLayout btn_lvTogv;

	public BasePager(Activity mActivity) {
		this.mActivity = (MainActivity) mActivity;
		initViews();
	}

	/**
	 * 初始化view对象
	 * 
	 * mRootView:是作为ViewPager显示的布局对象
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
	 * 切换ListView和GridView
	 */
	public void listToGrid(View v) {
	}

	// 设置侧边栏的可用状态
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
	 * 获取leftMenufragment
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
	 * 让子类实现，自己初始化数据
	 */
	public void initData() {
	}

}
