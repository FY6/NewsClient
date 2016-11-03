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
 * 2016-8-24 ����3:36:49 ���� HomePager
 * 
 * 
 * ��������ҳ��
 **/
public class NewsCenterPager extends BasePager {

	// ��������
	private NewsData mNewsData;

	// ά��4���˵�����ҳ
	private ArrayList<BaseMenuDetailPager> mMenuPagers;// �˵�����ҳ�ļ���

	private FrameLayout listToGrid;

	public NewsCenterPager(Activity mActivity) {
		super(mActivity);
	}

	/**
	 * ��ContentFragment�е�ViewPagerѡ��ʱ����
	 * 
	 */
	@Override
	public void initData() {
		// ����SlidingMenu�ɼ�
		setSlidingMenuEnable(true);
		// ��ȡ����������,�������������ݴ��ݸ�leftMenuFragment
		getDataFromService();
	}

	/**
	 * ���õ�ǰ�˵�����ҳ��
	 * 
	 * @param position
	 */
	public void setCurrentMenuDetailPager(int position) {
		// ���ñ���
		mTitle.setText(mNewsData.data.get(position).title);
		// ��֮ǰ����FrameLayout�е�viewȫ���������������ص�����
		flContent.removeAllViews();
		BaseMenuDetailPager pager = mMenuPagers.get(position);
		pager.initData();// ֻ��ѡ�вŻ���������
		if (pager instanceof PhotoMenuDetailPager) {
			listToGrid.setVisibility(View.VISIBLE);
			((PhotoMenuDetailPager) pager).listToGrid(listToGrid);
		} else {
			listToGrid.setVisibility(View.INVISIBLE);
		}
		flContent.addView(pager.mRootView);
	}

	/**
	 * �л�ListView��GridView
	 */
	@Override
	public void listToGrid(View v) {
		listToGrid = (FrameLayout) v;
	}

	/**
	 * �ӷ�������ȡ����
	 * 
	 * xUtils �ײ�Ҳ��ʹ��HttpClient,ʹ��xUtilsע�� 6.0�Ժ�google����ʹ��HttpClient
	 */
	private void getDataFromService() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, GlobalContants.CATEGORIESURL,
				new RequestCallBack<String>() {

					// ����ɹ�
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String jsonData = (String) responseInfo.result;
						// ����json����
						parseData(jsonData);
					}

					// ����ʧ��
					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(mActivity, "����ʧ��", Toast.LENGTH_SHORT)
								.show();
					}
				});
	}

	/**
	 * ����Json����
	 * 
	 * @param jsonData
	 */
	private void parseData(String jsonData) {
		Gson gson = new Gson();
		mNewsData = gson.fromJson(jsonData, NewsData.class);
		// ��ȡLeftMenufargment,�������ݴ��ݸ�leftmenu
		LeftMenuFragment leftMenuFragment = getLeftMenuFragment();
		leftMenuFragment.setData(mNewsData);

		// ��ʼ��4���˵�����ҳ
		mMenuPagers = new ArrayList<BaseMenuDetailPager>();
		mMenuPagers.add(new NewsMenuDetailPager(mActivity, mNewsData.data
				.get(0).children));
		mMenuPagers.add(new TopicMenuDetailPager(mActivity));
		mMenuPagers.add(new PhotoMenuDetailPager(mActivity));
		mMenuPagers.add(new InteractMenuDetailPager(mActivity));

		// Ĭ��ѡ������ҳ
		setCurrentMenuDetailPager(0);
	}

}
