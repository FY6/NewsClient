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
 * 2016-8-23 ����1:46:59 ���� LeftMenuFragment.java
 * 
 * �໬menu
 **/
public class LeftMenuFragment extends BaseFragment {

	private ListView mListView;
	private ArrayList<NewsMenuData> newsMenuData;
	private LeftMenuAdapter adapter;

	private int mSelectPos;// ��¼��ǰѡ��item��λ��

	/**
	 * ����view��ΪleftMenuFagment�Ĳ�����ʾ
	 * 
	 */
	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.leftmenu_fragment, null);
		mListView = (ListView) view.findViewById(R.id.lv_leftmenu);
		return view;
	}

	/**
	 * ͨ��NewsCenterpager���ݹ�������
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
	 * ��leftMenuFragemnt������Activity�ɼ�ʱ����
	 */
	@Override
	public void initData() {
		initListener();
	}

	/**
	 * 
	 * ��ʼ��������
	 */
	private void initListener() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mSelectPos = position;
				setCurrentMenuDetailPager(position);
				// �˵�������
				toggle();
			}
		});
	}

	/**
	 * �˵����Ŀ���
	 * 
	 */
	private void toggle() {
		MainActivity mainUI = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		slidingMenu.toggle();
	}

	/**
	 * ���õ�ǰ�˵�����ҳ
	 * 
	 * @param position
	 */
	public void setCurrentMenuDetailPager(int position) {
		ContentFragment fragment = getContentfragment();// ��ȡ��ҳ��fragment
		NewsCenterPager pager = fragment.getNewsCenterPager();// ��ȡ��������ҳ��
		pager.setCurrentMenuDetailPager(position);// ���õ�ǰ�˵�����ҳ
		adapter.notifyDataSetChanged();
	}

	/**
	 * ��ȡContentfragment
	 * 
	 * @return
	 */
	public ContentFragment getContentfragment() {
		MainActivity mainUI = (MainActivity) mActivity;
		FragmentManager fm = mainUI.getSupportFragmentManager();

		return (ContentFragment) fm.findFragmentByTag("MainFragment");
	}

	/**
	 * listView����������
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
