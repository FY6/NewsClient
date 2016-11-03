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
 * 2016-8-22 ����7:45:57 ���� GuideActivity.java
 * 
 * ��ҳ��
 **/
public class GuideActivity extends Activity {
	public static final String TAG = GuideActivity.class.getName();

	private ViewPager vpGuide;
	private LinearLayout llPointGroup;// ���Բ��֣���Ż�ɫ��
	private Button btnStart;// ��ʼ���鰴ť
	private View redPoint;// ��ɫ��
	private int mPointWidth;// ��ɫԲ���ľ���

	// ��ҳ����Դid
	private static final int[] imageIds = new int[] { R.drawable.guide_1,
			R.drawable.guide_2, R.drawable.guide_3 };

	// ��ҳ���imageview
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
	 * ��ʼ��view
	 */
	private void initView() {
		setContentView(R.layout.activity_guide);
		vpGuide = (ViewPager) findViewById(R.id.vp_guide);
		llPointGroup = (LinearLayout) findViewById(R.id.ll_point);
		btnStart = (Button) findViewById(R.id.btn_start);
		redPoint = findViewById(R.id.view_point_red);
	}

	/**
	 * ��ʼ��������
	 * 
	 */
	private void initListener() {
		// ����Viewpager��������
		vpGuide.setOnPageChangeListener(new GuidePagerListener());

		// ��ȡ��ͼ��, ��onLayout�н����¼����м���
		llPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					// ��onlayoutִ�н�����ص��˷���
					@SuppressWarnings("deprecation")
					@Override
					public void onGlobalLayout() {
						// System.out.println("layout ����");
						llPointGroup.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
						mPointWidth = llPointGroup.getChildAt(1).getLeft()
								- llPointGroup.getChildAt(0).getLeft();
						System.out.println("Բ�����:" + mPointWidth);
					}
				});

		// ���ÿ�ʼ����button�������,�����ʼ���飬��ת����ҳ��
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
	 * ��ʼ������
	 */
	private void initData() {
		imageViews = new ArrayList<ImageView>();
		for (int i = 0; i < imageIds.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setBackgroundResource(imageIds[i]);
			imageViews.add(imageView);
		}
		// ����ViewPager ����������
		vpGuide.setAdapter(new GuidePagerAdapter());

		// ��ʼСԲ��
		for (int i = 0; i < imageIds.length; i++) {
			View view = new View(this);
			view.setBackgroundResource(R.drawable.shape_point_gray);
			// ��Ļ���䣬��dpת��Ϊpx
			LayoutParams params = new LinearLayout.LayoutParams(
					DensityUtils.dp2px(this, 10), DensityUtils.dp2px(this, 10));
			if (i > 0) {
				params.leftMargin = DensityUtils.dp2px(this, 5);
			}
			view.setLayoutParams(params);
			llPointGroup.addView(view);// ��СԲ����ӵ����Բ���
		}
	}

	/**
	 * ViewPager������
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
	 * ViewPager����
	 * 
	 * @author wfy
	 * 
	 */
	class GuidePagerListener implements OnPageChangeListener {

		/**
		 * position: ��ǰ��ʾҳ��ĽǱ�
		 * 
		 * positionOffset: �ٷֱ�
		 * 
		 * positionOffsetPixels:�ƶ�����
		 */

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			// Log.e(TAG, "position: " + position + " ,positionOffset: "
			// + positionOffset + " ,positionOffsetPixels: "
			// + positionOffsetPixels);

			int len = (int) ((mPointWidth * positionOffset) + (position * mPointWidth));
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) redPoint
					.getLayoutParams();// ��ȡ��ǰ���Ĳ��ֲ���
			params.leftMargin = len;
			redPoint.setLayoutParams(params);

		}

		@Override
		public void onPageSelected(int position) {
			/**
			 * ���һ��ҳ����ʾ��ʼ���鰴ť
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
