package com.zhbj.zhbj.activitys;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.zhbj.zhbj.R;
import com.zhbj.zhbj.fragments.ContentFragment;
import com.zhbj.zhbj.fragments.LeftMenuFragment;

/**
 * 2016-8-23 ����12:29:29 ���� MainActivity
 * 
 * ������
 **/
public class MainActivity extends SlidingFragmentActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		initViews();
		initFragments();
	}

	/**
	 * ��ʼ��view��SlidingMenu�Ĳ���
	 */
	private void initViews() {
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.leftmenu);// ���ò��������
		SlidingMenu slidingMenu = getSlidingMenu();// ��ȡ���������
		slidingMenu.setMode(SlidingMenu.LEFT);// ����ֻ����߲˵�
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// ����ȫ����ʾ
		// ��Ļ����
		int width = getWindowManager().getDefaultDisplay().getWidth();
		slidingMenu.setBehindOffset(200 * width / 320);// ����Ԥ����Ļ���
	}

	/**
	 * ��ʼ��Fragment
	 */
	private void initFragments() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		transaction
				.replace(R.id.fl_main, new ContentFragment(), "MainFragment");
		transaction.replace(R.id.fl_leftmenu, new LeftMenuFragment(),
				"LeftMenuFragment");
		transaction.commit();
	}

}
