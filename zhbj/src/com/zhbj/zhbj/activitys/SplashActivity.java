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
 *         闪屏页面
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
	 * 初始化UI
	 */
	private void intitView() {
		setContentView(R.layout.activity_splash);
		rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
	}

	/**
	 * 开启闪屏页面的动画
	 * 
	 * 从布局文件中加载动画,还可以动态使用代码创建动画
	 */
	private void startSplashAnimationToXml() {
		Animation animation = AnimationUtils.loadAnimation(this,
				R.anim.splash_animationset);
		rlRoot.startAnimation(animation);
		// 设置动画监听器
		initAnimationListener(animation);
	}

	/**
	 * 设置点击监听
	 */
	private void initAnimationListener(Animation animation) {

		animation.setAnimationListener(new AnimationListener() {

			/**
			 * 动画开始执行，此方法调用
			 */
			@Override
			public void onAnimationStart(Animation animation) {
			}

			/**
			 * 动画执行执行中，一直调用此方法
			 */
			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			/**
			 * 动画执行结束，此方法调用
			 * 
			 * 当动画执行结束，跳转至GuideActivity
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
