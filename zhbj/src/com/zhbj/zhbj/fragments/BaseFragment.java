package com.zhbj.zhbj.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 2016-8-23 ����1:51:57 ���� BaseFragment.java
 * 
 * Fragment����
 **/
public abstract class BaseFragment extends Fragment {

	public Activity mActivity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return initViews();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
	}

	// �������ʵ�ָ÷���
	public abstract View initViews();

	// ��ʼ������
	public void initData() {
	}

}
