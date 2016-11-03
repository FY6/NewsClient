package com.zhbj.zhbj.base.newsdetail.pager.impl;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.CirclePageIndicator;
import com.zhbj.zhbj.R;
import com.zhbj.zhbj.activitys.NewsDetailPagerActivity;
import com.zhbj.zhbj.base.newsdetail.pager.BaseMenuDetailPager;
import com.zhbj.zhbj.domain.NewsData.NewsTabData;
import com.zhbj.zhbj.domain.TabData;
import com.zhbj.zhbj.domain.TabData.TabNewsData;
import com.zhbj.zhbj.domain.TabData.TopNewsData;
import com.zhbj.zhbj.global.GlobalContants;
import com.zhbj.zhbj.utils.CacheUtils;
import com.zhbj.zhbj.utils.PrefUtils;
import com.zhbj.zhbj.view.NewsListView;
import com.zhbj.zhbj.view.NewsListView.OnRefrshListener;

/**
 * 2016-8-26 下午4:46:06 创建 TabDetailPager.java
 * 
 * 页签详情页
 * 
 **/
public class TabDetailPager extends BaseMenuDetailPager implements
		OnPageChangeListener {

	private NewsTabData newsTabData;

	private String mUrl;

	@ViewInject(R.id.vp_topnews_pic)
	private ViewPager mVPTopNewsPic;

	@ViewInject(R.id.tv_title)
	private TextView tvTitle;

	@ViewInject(R.id.indicator)
	private CirclePageIndicator indicator;

	@ViewInject(R.id.lv_news_list)
	private NewsListView newsListView;// 新闻列表

	private TabData tabData;// 封装新闻数据

	private ArrayList<TopNewsData> topnews;// 封装头条新闻数据

	private ArrayList<TabNewsData> newsData;// 新闻列表数据

	private NewsListAdapter adapter;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (adapter == null) {
				adapter = new NewsListAdapter();
				newsListView.setAdapter(adapter);
			} else {
				adapter.notifyDataSetChanged();
			}
			switch (msg.what) {
			case 0:
				// 加载更多
				newsListView.refreshComplete(true);
				isLoadingMore = false;
				break;
			case 1:
				// 下拉刷新
				newsListView.refreshComplete(false);
				break;
			case 2:
				// 没有更多数据加载
				Toast.makeText(mActivity, "没有更多数据加载...", Toast.LENGTH_SHORT)
						.show();
				newsListView.refreshComplete(true);
				isLoadingMore = false;
				break;
			case 6:// 轮番图
				int currentItem = mVPTopNewsPic.getCurrentItem();
				currentItem++;
				currentItem = currentItem % topnews.size();
				mVPTopNewsPic.setCurrentItem(currentItem);
				handler.sendEmptyMessageDelayed(6, 3000);
				break;
			}
		};
	};

	public TabDetailPager(Activity mActivity, NewsTabData newsTabData) {
		super(mActivity);
		this.newsTabData = newsTabData;
		mUrl = GlobalContants.SERVICEURL + this.newsTabData.url;
		initData();
	}

	/**
	 * 初始化view
	 * 
	 */
	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
		ViewUtils.inject(this, view);// 注入view事件

		View headerView = View.inflate(mActivity, R.layout.listview_headerview,
				null);

		ViewUtils.inject(this, headerView);// 注入view事件
		newsListView.addHeaderView(headerView);// 给listview添加头布局

		initListener();
		return view;
	}

	/**
	 * 
	 * 初始化监听器
	 */
	private void initListener() {
		indicator.setOnPageChangeListener(this);

		newsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String ids = newsData.get(position).id;
				String mId = PrefUtils.getString(mActivity, "ids", "");

				// handler.sendEmptyMessage(8);//notificationDataSetChange()局部刷新
				if (PrefUtils.getString(mActivity, "ids", "").contains(ids)) {
					TextView tvTitle = (TextView) view
							.findViewById(R.id.tv_title);
					tvTitle.setTextColor(Color.GRAY);
					TextView tvDate = (TextView) view
							.findViewById(R.id.tv_date);
					tvDate.setTextColor(Color.GRAY);
				} else {

					if (!TextUtils.isEmpty(mId)) {
						mId = mId + "," + ids;
					} else {
						mId = ids;
					}
					PrefUtils.setString(mActivity, "ids", mId);
				}

				Intent intent = new Intent(mActivity,
						NewsDetailPagerActivity.class);
				String url = newsData.get(position).url;

				if (!TextUtils.isEmpty(url)) {
					intent.putExtra("url", url);
					mActivity.startActivity(intent);
				} else {
					Toast.makeText(mActivity, "url地址解析失败", Toast.LENGTH_SHORT)
							.show();
				}

			}
		});
		newsListView.setOnRefrshListener(new OnRefrshListener() {

			/**
			 * 下拉刷新
			 * 
			 */
			@Override
			public void onPullRefresh() {
				getDataFromService();
			}

			/**
			 * 加载更多
			 * 
			 */
			@Override
			public void onLoadingMore() {
				loadingMoreData();
			}
		});
	}

	/**
	 * 初始化数据
	 * 
	 */
	@Override
	public void initData() {
		// 如果缓存不为空，则拿缓存数据后再请求服务器更新数据
		String cache = CacheUtils.getCache(mActivity, mUrl);
		if (!TextUtils.isEmpty(cache)) {
			parseData(cache);
		}
		getDataFromService();
	}

	/**
	 * 从服务器获取数据
	 * 
	 */
	private void getDataFromService() {
		isLoadingMore = false;
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, mUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String jsonData = responseInfo.result;
				// 解析Json数据
				parseData(jsonData);

				// 缓存数据，，，以Json的方式缓存起来
				CacheUtils.putCache(mActivity, mUrl, jsonData);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, "请求数据失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * 
	 * 解析网络数据
	 * 
	 * @param jsonData
	 */
	private String more;
	private boolean isLoadingMore = false;

	protected void parseData(String jsonData) {
		Gson gson = new Gson();
		tabData = gson.fromJson(jsonData, TabData.class);
		topnews = tabData.data.topnews;// 拿到头条新闻数据

		// 拿到加载更多数据地址
		more = tabData.data.more;
		if (!TextUtils.isEmpty(more)) {
			more = GlobalContants.SERVICEURL + more;
		} else {
			more = null;
		}

		if (topnews != null) {
			mVPTopNewsPic.setAdapter(new TabDetailAdapter());

			indicator.setViewPager(mVPTopNewsPic);
			indicator.setSnap(true);

			indicator.onPageSelected(0);// 默认选中第一项
			tvTitle.setText(topnews.get(0).title);

			handler.sendEmptyMessageDelayed(6, 3000);// 轮番图
		}

		if (newsData != null && newsData.size() > 0 && isLoadingMore) {
			newsData.addAll(tabData.data.news);
		} else {
			newsData = tabData.data.news;
		}

		if (newsData != null) {
			Message msg = handler.obtainMessage();
			if (isLoadingMore) {
				msg.what = 0;// 加载更多数据
			} else {
				msg.what = 1;// 下拉刷新
			}
			handler.sendMessage(msg);
		}
	}

	/**
	 * 加载更多
	 * 
	 */
	private void loadingMoreData() {
		if (more != null) {
			isLoadingMore = true;
			HttpUtils utils = new HttpUtils();
			utils.send(HttpMethod.GET, more, new RequestCallBack<String>() {

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					String jsonData = responseInfo.result;
					// 解析Json数据
					parseData(jsonData);
				}

				@Override
				public void onFailure(HttpException error, String msg) {
					Toast.makeText(mActivity, "请求数据失败", Toast.LENGTH_SHORT)
							.show();
				}
			});
		} else {
			// 没有更多数据加载
			Message msg = handler.obtainMessage();
			msg.what = 2;
			handler.sendMessageDelayed(msg, 500);
		}

	}

	/**
	 * 新闻列表数据适配器
	 * 
	 * @author wfy
	 * 
	 */
	class NewsListAdapter extends BaseAdapter {

		private BitmapUtils bitmapUtils;

		public NewsListAdapter() {
			bitmapUtils = new BitmapUtils(mActivity);
			// 配置默认加载图片
			bitmapUtils.configDefaultLoadingImage(R.drawable.news_pic_default);
		}

		@Override
		public int getCount() {
			return newsData.size();
		}

		@Override
		public TabNewsData getItem(int position) {
			return newsData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(mActivity, R.layout.news_list_item,
						null);
				holder.image = (ImageView) convertView
						.findViewById(R.id.images);
				holder.tvTitle = (TextView) convertView
						.findViewById(R.id.tv_title);
				holder.tvDate = (TextView) convertView
						.findViewById(R.id.tv_date);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			TabNewsData item = getItem(position);

			// 标志已读状态
			if (PrefUtils.getString(mActivity, "ids", "").contains(item.id)) {
				holder.tvTitle.setTextColor(Color.GRAY);
				holder.tvDate.setTextColor(Color.GRAY);
			}

			holder.tvTitle.setText(item.title);
			holder.tvDate.setText(item.pubdate);

			bitmapUtils.display(holder.image, item.listimage);

			return convertView;
		}
	}

	/**
	 * 新闻列表
	 * 
	 * @author wfy
	 * 
	 */
	class ViewHolder {
		ImageView image;
		TextView tvTitle;
		TextView tvDate;
	}

	/**
	 * 
	 * 头条图片适配器
	 * 
	 * @author wfy
	 * 
	 */
	class TabDetailAdapter extends PagerAdapter {

		private BitmapUtils bitmapUtils;

		public TabDetailAdapter() {
			bitmapUtils = new BitmapUtils(mActivity);
			bitmapUtils.configDefaultLoadingImage(R.drawable.news_pic_default);// 设置图片加载时默认显示的图片
		}

		@Override
		public int getCount() {
			return tabData.data.topnews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView iv = new ImageView(mActivity);
			iv.setScaleType(ScaleType.FIT_XY);// 基于控件大小填充图片

			TopNewsData topNewsData = tabData.data.topnews.get(position);
			bitmapUtils.display(iv, topNewsData.topimage);
			// iv.setImageResource(R.drawable.news_pic_default);
			container.addView(iv);
			iv.setOnTouchListener(new TopNewsTouchListener());

			return iv;
		}
	}

	class TopNewsTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				System.out.println("按下...");
				handler.removeCallbacksAndMessages(null);// 移除handler所有的消息
				break;
			case MotionEvent.ACTION_CANCEL:
				System.out.println("事件取消...");
				handler.sendEmptyMessageDelayed(6, 3000);
				break;
			case MotionEvent.ACTION_UP:
				System.out.println("抬起...");
				handler.sendEmptyMessageDelayed(6, 3000);
				break;
			}
			return true;
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
		tvTitle.setText(topnews.get(arg0).title);
	}
}
