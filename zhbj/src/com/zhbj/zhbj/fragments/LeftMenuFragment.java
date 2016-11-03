package com.zhbj.zhbj.fragments;

import java.util.ArrayList;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.zhbj.zhbj.R;
import com.zhbj.zhbj.activitys.MainActivity;
import com.zhbj.zhbj.base.content.pager.impl.NewsCenterPager;
import com.zhbj.zhbj.domain.NewsData;
import com.zhbj.zhbj.domain.NewsData.NewsMenuData;

/**
 * 2016-8-23 下午1:46:59 创建 LeftMenuFragment.java
 * 
 * 侧滑menu
 **/
public class LeftMenuFragment extends BaseFragment {

	private ListView mListView;
	private ArrayList<NewsMenuData> newsMenuData;
	private LeftMenuAdapter adapter;

	private int mSelectPos;// 记录当前选中item的位置

	/**
	 * 返回view作为leftMenuFagment的布局显示
	 * 
	 */
	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.leftmenu_fragment, null);
		mListView = (ListView) view.findViewById(R.id.lv_leftmenu);
		return view;
	}

	/**
	 * 通过NewsCenterpager传递过来数据
	 * 
	 * @param data
	 */
	public void setData(NewsData data) {
		mSelectPos = 0;
		this.newsMenuData = data.data;
		adapter = new LeftMenuAdapter();
		mListView.setAdapter(adapter);
	}

	/**
	 * 当leftMenuFragemnt依附的Activity可见时调用
	 */
	@Override
	public void initData() {
		initListener();
	}

	/**
	 * 
	 * 初始化监听器
	 */
	private void initListener() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mSelectPos = position;
				setCurrentMenuDetailPager(position);
				// 菜单栏开关
				toggle();
			}
		});
	}

	/**
	 * 菜单栏的开关
	 * 
	 */
	private void toggle() {
		MainActivity mainUI = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		slidingMenu.toggle();
	}

	/**
	 * 设置当前菜单详情页
	 * 
	 * @param position
	 */
	public void setCurrentMenuDetailPager(int position) {
		ContentFragment fragment = getContentfragment();// 获取主页面fragment
		NewsCenterPager pager = fragment.getNewsCenterPager();// 获取新闻中心页面
		pager.setCurrentMenuDetailPager(position);// 设置当前菜单详情页
		adapter.notifyDataSetChanged();
	}

	/**
	 * 获取Contentfragment
	 * 
	 * @return
	 */
	public ContentFragment getContentfragment() {
		MainActivity mainUI = (MainActivity) mActivity;
		FragmentManager fm = mainUI.getSupportFragmentManager();

		return (ContentFragment) fm.findFragmentByTag("MainFragment");
	}

	/**
	 * listView数据适配器
	 * 
	 * @author wfy
	 * 
	 */
	class LeftMenuAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return newsMenuData.size();
		}

		@Override
		public NewsMenuData getItem(int position) {
			return newsMenuData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(mActivity,
					R.layout.item_listview_leftmenu, null);
			TextView tvTile = (TextView) view.findViewById(R.id.tv_title);

			NewsMenuData newsMenuData = getItem(position);
			tvTile.setText(newsMenuData.title);

			if (mSelectPos == position) {
				tvTile.setEnabled(true);
			} else {
				tvTile.setEnabled(false);
			}

			return view;
		}
	}
}
