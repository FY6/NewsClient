package com.zhbj.zhbj.base.newsdetail.pager.impl;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;
import com.zhbj.zhbj.R;
import com.zhbj.zhbj.activitys.MainActivity;
import com.zhbj.zhbj.base.newsdetail.pager.BaseMenuDetailPager;
import com.zhbj.zhbj.domain.NewsData.NewsTabData;

/**
 * 2016-8-26 上午1:03:47 创建 NewsMenuDetailPager.java
 * 
 * 菜单详情页-新闻
 **/
public class NewsMenuDetailPager extends BaseMenuDetailPager implements
		OnPageChangeListener {
	private ViewPager mViewPager;

	private ArrayList<TabDetailPager> mPagerList;// 维护11个页签

	private ArrayList<NewsTabData> mNewsTabData;// 页签的网络数据

	private TabPageIndicator mIndicator;

	public NewsMenuDetailPager(Activity mActivity,
			ArrayList<NewsTabData> children) {
		super(mActivity);
		mNewsTabData = children;
		// initData();
	}

	/**
	 * 从重写基类的initViews方法，进行初始化view，加载news_menu_detail布局文件。
	 * 
	 * 从布局文件中，拿到ViewPager，并对ViewPager进行数据填充
	 * 
	 */
	@Override
	public View initViews() {
		mRootView = View.inflate(mActivity, R.layout.news_menu_detail, null);
		mViewPager = (ViewPager) mRootView.findViewById(R.id.vp_menu_detail);

		ViewUtils.inject(this, mRootView);// 注入view事件

		// 拿到ViewPaerIndicator对象
		mIndicator = (TabPageIndicator) mRootView.findViewById(R.id.indicator);

		initListener();
		return mRootView;
	}

	private void initListener() {
		// mViewPager.setOnPageChangeListener(this);//注意:当viewpager和Indicator绑定时,
		// 滑动监听需要设置给Indicator而不是viewpager
		mIndicator.setOnPageChangeListener(this);
	}

	/**
	 * 切换页签
	 * 
	 * @param v
	 */
	@OnClick(R.id.btn_next)
	public void nextPage(View v) {
		int currentItem = mViewPager.getCurrentItem();
		mViewPager.setCurrentItem(++currentItem);
	}

	/**
	 * 重写父类initData方法，进行初始化操作
	 * 
	 */
	@Override
	public void initData() {
		mPagerList = new ArrayList<TabDetailPager>();
		for (int i = 0; i < mNewsTabData.size(); i++) {
			mPagerList.add(new TabDetailPager(mActivity, mNewsTabData.get(i)));
		}

		mViewPager.setAdapter(new NewsMenuDetailPagerAdapter());

		mIndicator.setViewPager(mViewPager);// 该方法必须在setAdapter之后调用
	}

	/**
	 * ViewPager数据适配器
	 * 
	 * @author wfy
	 * 
	 */
	class NewsMenuDetailPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mPagerList.size();
		}

		/**
		 * 重写此方法，返回页面的标题，用于页签的显示
		 * 
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			return mNewsTabData.get(position).title;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			TabDetailPager tabDetailPager = mPagerList.get(position);
			container.addView(tabDetailPager.mRootView);
			return tabDetailPager.mRootView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		MainActivity mainUi = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUi.getSlidingMenu();

		if (arg0 == 0) {// 只有在第一个页面(北京), 侧边栏才允许出来
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}

}
