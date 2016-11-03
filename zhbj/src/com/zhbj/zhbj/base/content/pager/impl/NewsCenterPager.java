package com.zhbj.zhbj.base.content.pager.impl;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.zhbj.zhbj.base.content.pager.BasePager;
import com.zhbj.zhbj.base.newsdetail.pager.BaseMenuDetailPager;
import com.zhbj.zhbj.base.newsdetail.pager.impl.InteractMenuDetailPager;
import com.zhbj.zhbj.base.newsdetail.pager.impl.NewsMenuDetailPager;
import com.zhbj.zhbj.base.newsdetail.pager.impl.PhotoMenuDetailPager;
import com.zhbj.zhbj.base.newsdetail.pager.impl.TopicMenuDetailPager;
import com.zhbj.zhbj.domain.NewsData;
import com.zhbj.zhbj.fragments.LeftMenuFragment;
import com.zhbj.zhbj.global.GlobalContants;

/**
 * 2016-8-24 下午3:36:49 创建 HomePager
 * 
 * 
 * 新闻中心页面
 **/
public class NewsCenterPager extends BasePager {

	// 新闻数据
	private NewsData mNewsData;

	// 维护4个菜单详情页
	private ArrayList<BaseMenuDetailPager> mMenuPagers;// 菜单详情页的集合

	private FrameLayout listToGrid;

	public NewsCenterPager(Activity mActivity) {
		super(mActivity);
	}

	/**
	 * 在ContentFragment中的ViewPager选中时调用
	 * 
	 */
	@Override
	public void initData() {
		// 设置SlidingMenu可见
		setSlidingMenuEnable(true);
		// 获取服务器数据,并将服务器数据传递给leftMenuFragment
		getDataFromService();
	}

	/**
	 * 设置当前菜单详情页面
	 * 
	 * @param position
	 */
	public void setCurrentMenuDetailPager(int position) {
		// 设置标题
		mTitle.setText(mNewsData.data.get(position).title);
		// 把之前的在FrameLayout中的view全部清除，以免造成重叠现象
		flContent.removeAllViews();
		BaseMenuDetailPager pager = mMenuPagers.get(position);
		pager.initData();// 只有选中才会请求数据
		if (pager instanceof PhotoMenuDetailPager) {
			listToGrid.setVisibility(View.VISIBLE);
			((PhotoMenuDetailPager) pager).listToGrid(listToGrid);
		} else {
			listToGrid.setVisibility(View.INVISIBLE);
		}
		flContent.addView(pager.mRootView);
	}

	/**
	 * 切换ListView和GridView
	 */
	@Override
	public void listToGrid(View v) {
		listToGrid = (FrameLayout) v;
	}

	/**
	 * 从服务器获取数据
	 * 
	 * xUtils 底层也是使用HttpClient,使用xUtils注意 6.0以后，google废弃使用HttpClient
	 */
	private void getDataFromService() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, GlobalContants.CATEGORIESURL,
				new RequestCallBack<String>() {

					// 请求成功
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String jsonData = (String) responseInfo.result;
						// 解析json数据
						parseData(jsonData);
					}

					// 请求失败
					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(mActivity, "请求失败", Toast.LENGTH_SHORT)
								.show();
					}
				});
	}

	/**
	 * 解析Json数据
	 * 
	 * @param jsonData
	 */
	private void parseData(String jsonData) {
		Gson gson = new Gson();
		mNewsData = gson.fromJson(jsonData, NewsData.class);
		// 获取LeftMenufargment,并将数据传递给leftmenu
		LeftMenuFragment leftMenuFragment = getLeftMenuFragment();
		leftMenuFragment.setData(mNewsData);

		// 初始化4个菜单详情页
		mMenuPagers = new ArrayList<BaseMenuDetailPager>();
		mMenuPagers.add(new NewsMenuDetailPager(mActivity, mNewsData.data
				.get(0).children));
		mMenuPagers.add(new TopicMenuDetailPager(mActivity));
		mMenuPagers.add(new PhotoMenuDetailPager(mActivity));
		mMenuPagers.add(new InteractMenuDetailPager(mActivity));

		// 默认选中新闻页
		setCurrentMenuDetailPager(0);
	}

}
