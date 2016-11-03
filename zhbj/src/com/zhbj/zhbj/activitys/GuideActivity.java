package com.zhbj.zhbj.activitys;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import com.zhbj.zhbj.R;
import com.zhbj.zhbj.utils.DensityUtils;
import com.zhbj.zhbj.utils.PrefUtils;

/**
 * 2016-8-22 下午7:45:57 创建 GuideActivity.java
 * 
 * 向导页面
 **/
public class GuideActivity extends Activity {
	public static final String TAG = GuideActivity.class.getName();

	private ViewPager vpGuide;
	private LinearLayout llPointGroup;// 线性布局，存放灰色点
	private Button btnStart;// 开始体验按钮
	private View redPoint;// 红色点
	private int mPointWidth;// 灰色圆点间的距离

	// 向导页面资源id
	private static final int[] imageIds = new int[] { R.drawable.guide_1,
			R.drawable.guide_2, R.drawable.guide_3 };

	// 向导页面的imageview
	private ArrayList<ImageView> imageViews;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		initView();
		initListener();
		initData();
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		setContentView(R.layout.activity_guide);
		vpGuide = (ViewPager) findViewById(R.id.vp_guide);
		llPointGroup = (LinearLayout) findViewById(R.id.ll_point);
		btnStart = (Button) findViewById(R.id.btn_start);
		redPoint = findViewById(R.id.view_point_red);
	}

	/**
	 * 初始化监听器
	 * 
	 */
	private void initListener() {
		// 设置Viewpager滑动监听
		vpGuide.setOnPageChangeListener(new GuidePagerListener());

		// 获取视图树, 对onLayout行结束事件进行监听
		llPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					// 当onlayout执行结束后回调此方法
					@SuppressWarnings("deprecation")
					@Override
					public void onGlobalLayout() {
						// System.out.println("layout 结束");
						llPointGroup.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
						mPointWidth = llPointGroup.getChildAt(1).getLeft()
								- llPointGroup.getChildAt(0).getLeft();
						System.out.println("圆点距离:" + mPointWidth);
					}
				});

		// 设置开始体验button点击监听,点击开始体验，跳转至主页面
		btnStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PrefUtils.setBoolean(GuideActivity.this, "user_guide_showed",
						true);
				Intent intent = new Intent(GuideActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		imageViews = new ArrayList<ImageView>();
		for (int i = 0; i < imageIds.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setBackgroundResource(imageIds[i]);
			imageViews.add(imageView);
		}
		// 设置ViewPager 数据适配器
		vpGuide.setAdapter(new GuidePagerAdapter());

		// 初始小圆点
		for (int i = 0; i < imageIds.length; i++) {
			View view = new View(this);
			view.setBackgroundResource(R.drawable.shape_point_gray);
			// 屏幕适配，将dp转化为px
			LayoutParams params = new LinearLayout.LayoutParams(
					DensityUtils.dp2px(this, 10), DensityUtils.dp2px(this, 10));
			if (i > 0) {
				params.leftMargin = DensityUtils.dp2px(this, 5);
			}
			view.setLayoutParams(params);
			llPointGroup.addView(view);// 把小圆点添加到线性布局
		}
	}

	/**
	 * ViewPager适配器
	 * 
	 */
	class GuidePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageIds.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(imageViews.get(position));
			return imageViews.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

	/**
	 * ViewPager监听
	 * 
	 * @author wfy
	 * 
	 */
	class GuidePagerListener implements OnPageChangeListener {

		/**
		 * position: 当前显示页面的角标
		 * 
		 * positionOffset: 百分比
		 * 
		 * positionOffsetPixels:移动距离
		 */

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			// Log.e(TAG, "position: " + position + " ,positionOffset: "
			// + positionOffset + " ,positionOffsetPixels: "
			// + positionOffsetPixels);

			int len = (int) ((mPointWidth * positionOffset) + (position * mPointWidth));
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) redPoint
					.getLayoutParams();// 获取当前红点的布局参数
			params.leftMargin = len;
			redPoint.setLayoutParams(params);

		}

		@Override
		public void onPageSelected(int position) {
			/**
			 * 最后一个页面显示开始体验按钮
			 */
			if (position == imageIds.length - 1) {
				btnStart.setVisibility(View.VISIBLE);
			} else {
				btnStart.setVisibility(View.INVISIBLE);
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			// Log.e(TAG, "state: " + state);
		}

	}
}
