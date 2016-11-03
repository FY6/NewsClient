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
 * 2016-8-26 ����1:03:47 ���� NewsMenuDetailPager.java
 * 
 * �˵�����ҳ-����
 **/
public class NewsMenuDetailPager extends BaseMenuDetailPager implements
		OnPageChangeListener {
	private ViewPager mViewPager;

	private ArrayList<TabDetailPager> mPagerList;// ά��11��ҳǩ

	private ArrayList<NewsTabData> mNewsTabData;// ҳǩ����������

	private TabPageIndicator mIndicator;

	public NewsMenuDetailPager(Activity mActivity,
			ArrayList<NewsTabData> children) {
		super(mActivity);
		mNewsTabData = children;
		// initData();
	}

	/**
	 * ����д�����initViews���������г�ʼ��view������news_menu_detail�����ļ���
	 * 
	 * �Ӳ����ļ��У��õ�ViewPager������ViewPager�����������
	 * 
	 */
	@Override
	public View initViews() {
		mRootView = View.inflate(mActivity, R.layout.news_menu_detail, null);
		mViewPager = (ViewPager) mRootView.findViewById(R.id.vp_menu_detail);

		ViewUtils.inject(this, mRootView);// ע��view�¼�

		// �õ�ViewPaerIndicator����
		mIndicator = (TabPageIndicator) mRootView.findViewById(R.id.indicator);

		initListener();
		return mRootView;
	}

	private void initListener() {
		// mViewPager.setOnPageChangeListener(this);//ע��:��viewpager��Indicator��ʱ,
		// ����������Ҫ���ø�Indicator������viewpager
		mIndicator.setOnPageChangeListener(this);
	}

	/**
	 * �л�ҳǩ
	 * 
	 * @param v
	 */
	@OnClick(R.id.btn_next)
	public void nextPage(View v) {
		int currentItem = mViewPager.getCurrentItem();
		mViewPager.setCurrentItem(++currentItem);
	}

	/**
	 * ��д����initData���������г�ʼ������
	 * 
	 */
	@Override
	public void initData() {
		mPagerList = new ArrayList<TabDetailPager>();
		for (int i = 0; i < mNewsTabData.size(); i++) {
			mPagerList.add(new TabDetailPager(mActivity, mNewsTabData.get(i)));
		}

		mViewPager.setAdapter(new NewsMenuDetailPagerAdapter());

		mIndicator.setViewPager(mViewPager);// �÷���������setAdapter֮�����
	}

	/**
	 * ViewPager����������
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
		 * ��д�˷���������ҳ��ı��⣬����ҳǩ����ʾ
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

		if (arg0 == 0) {// ֻ���ڵ�һ��ҳ��(����), ��������������
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}

}
