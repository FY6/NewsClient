package com.zhbj.zhbj.base.newsdetail.pager.impl;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.zhbj.zhbj.R;
import com.zhbj.zhbj.base.newsdetail.pager.BaseMenuDetailPager;
import com.zhbj.zhbj.domain.GroupPhotoData;
import com.zhbj.zhbj.domain.GroupPhotoData.News;
import com.zhbj.zhbj.global.GlobalContants;
import com.zhbj.zhbj.utils.bitmap.MyBitmapUtils;

/**
 * 2016-8-26 上午1:26:37 创建 PhotoMenuDetailPager.java
 * 
 * 组图
 * 
 **/
public class PhotoMenuDetailPager extends BaseMenuDetailPager implements
		OnScrollListener {

	private ListView lv_photo;
	private GridView gv_photo;
	private ArrayList<News> news;

	public PhotoMenuDetailPager(Activity mActivity) {
		super(mActivity);
	}

	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.photos_group, null);
		lv_photo = (ListView) view.findViewById(R.id.lv_photo);
		gv_photo = (GridView) view.findViewById(R.id.gv_photo);
		mRootView = view;
		initListener();
		return mRootView;
	}

	private void initListener() {
		lv_photo.setOnScrollListener(this);
		gv_photo.setOnScrollListener(this);
	}

	/**
	 * 切换ListView和GridView
	 * 
	 */
	private boolean isListDisplay = true;

	public void listToGrid(ViewGroup v) {
		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ViewGroup vg = (ViewGroup) v;
				for (int i = 0; i < vg.getChildCount(); i++) {
					ImageView child = (ImageView) vg.getChildAt(i);
					switch (child.getVisibility()) {
					case View.VISIBLE:
						child.setVisibility(View.INVISIBLE);
						break;
					case View.INVISIBLE:
						child.setVisibility(View.VISIBLE);
						if (isListDisplay) {
							gv_photo.setVisibility(View.VISIBLE);
							lv_photo.setVisibility(View.GONE);
						} else {
							gv_photo.setVisibility(View.GONE);
							lv_photo.setVisibility(View.VISIBLE);
						}
						break;
					default:
						break;
					}
				}
				isListDisplay = !isListDisplay;
			}
		});
	}

	@Override
	public void initData() {
		getDataFromService();
	}

	private void getDataFromService() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, GlobalContants.PHOTOURL,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String json = responseInfo.result;
						parseData(json);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(mActivity, "求情数据失败", Toast.LENGTH_SHORT)
								.show();
					}
				});
	}

	/**
	 * 解析数据
	 * 
	 * @param json
	 */
	protected void parseData(String json) {
		Gson gson = new Gson();
		news = gson.fromJson(json, GroupPhotoData.class).data.news;

		if (news != null) {
			adapter = new PhotosAdapter();
			lv_photo.setAdapter(adapter);
			gv_photo.setAdapter(adapter);
		}
	}

	class PhotosAdapter extends BaseAdapter {

		// private BitmapUtils bitmapUtils;

		private MyBitmapUtils myBitmapUtils;

		public PhotosAdapter() {
			myBitmapUtils = new MyBitmapUtils();
		}

		@Override
		public int getCount() {
			return news.size();
		}

		@Override
		public News getItem(int position) {
			return news.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(mActivity, R.layout.item_lv_photos,
						null);
				ImageView iv_photos = (ImageView) convertView
						.findViewById(R.id.iv_photos);
				TextView tv_title = (TextView) convertView
						.findViewById(R.id.tv_title);

				holder = new ViewHolder();
				holder.iv_photos = iv_photos;
				holder.tv_title = tv_title;

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			News item = getItem(position);

			if (IsViewIdle) {
				holder.tv_title.setText(item.title);
				// bitmapUtils.display(holder.iv_photos, item.listimage);
				myBitmapUtils.display(holder.iv_photos, item.listimage);
			}

			return convertView;
		}
	}

	class ViewHolder {
		ImageView iv_photos;
		TextView tv_title;
	}

	private boolean IsViewIdle = true;
	private PhotosAdapter adapter;

	/**
	 * 当滚动停止，才加载图片
	 */
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			IsViewIdle = true;
			adapter.notifyDataSetChanged();
		} else {
			IsViewIdle = false;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
	}
}
