package com.zhbj.zhbj.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.zhbj.zhbj.R;
import com.zhbj.zhbj.utils.PrefUtils;

/**
 * 
 * @author wfy
 * 
 *         ����ҳ��
 */
public class SplashActivity extends Activity {

	private RelativeLayout rlRoot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		intitView();
		startSplashAnimationToXml();
	}

	/**
	 * ��ʼ��UI
	 */
	private void intitView() {
		setContentView(R.layout.activity_splash);
		rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
	}

	/**
	 * ��������ҳ��Ķ���
	 * 
	 * �Ӳ����ļ��м��ض���,�����Զ�̬ʹ�ô��봴������
	 */
	private void startSplashAnimationToXml() {
		Animation animation = AnimationUtils.loadAnimation(this,
				R.anim.splash_animationset);
		rlRoot.startAnimation(animation);
		// ���ö���������
		initAnimationListener(animation);
	}

	/**
	 * ���õ������
	 */
	private void initAnimationListener(Animation animation) {

		animation.setAnimationListener(new AnimationListener() {

			/**
			 * ������ʼִ�У��˷�������
			 */
			@Override
			public void onAnimationStart(Animation animation) {
			}

			/**
			 * ����ִ��ִ���У�һֱ���ô˷���
			 */
			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			/**
			 * ����ִ�н������˷�������
			 * 
			 * ������ִ�н�������ת��GuideActivity
			 */
			@Override
			public void onAnimationEnd(Animation animation) {
				boolean isShowedGuied = PrefUtils.getBoolean(
						SplashActivity.this, "user_guide_showed", false);

				if (!isShowedGuied) {
					Intent intent = new Intent(SplashActivity.this,
							GuideActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(SplashActivity.this,
							MainActivity.class);
					startActivity(intent);
				}
				finish();
			}
		});
	}

}
