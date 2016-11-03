package com.zhbj.zhbj.fragments;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zhbj.zhbj.R;
import com.zhbj.zhbj.activitys.MainActivity;
import com.zhbj.zhbj.base.content.pager.BasePager;
import com.zhbj.zhbj.base.content.pager.impl.GovAffairsPager;
import com.zhbj.zhbj.base.content.pager.impl.HomePager;
import com.zhbj.zhbj.base.content.pager.impl.NewsCenterPager;
import com.zhbj.zhbj.base.content.pager.impl.SettingPager;
import com.zhbj.zhbj.base.content.pager.impl.SmartServicePager;
import com.zhbj.zhbj.global.GlobalContants;

/**
 * 2016-8-23 ����1:59:03 ���� MainFragment.java
 * 
 * ��ҳ��Fragment
 **/
public class ContentFragment extends BaseFragment {

	@ViewInject(R.id.vp_content)
	private ViewPager viewPager;
	@ViewInject(R.id.rg_group)
	private RadioGroup rgGroup;

	private List<BasePager> pagers;

	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.content_fragment, null);
		ViewUtils.inject(this, view); // ע��view���¼�
		return view;
	}

	@Override
	public void initData() {
		initListener();

		rgGroup.check(R.id.btn_home);// Ĭ��ѡ����ҳ
		pagers = new ArrayList<BasePager>();

		pagers.add(new HomePager(mActivity));
		pagers.add(new NewsCenterPager(mActivity));
		pagers.add(new SmartServicePager(mActivity));
		pagers.add(new GovAffairsPager(mActivity));
		pagers.add(new SettingPager(mActivity));

		viewPager.setAdapter(new ContentAdapter());

		// Ĭ�ϼ�����ҳ����
		pagers.get(0).initData();
	}

	// ��ȡNewsCenterPager
	public NewsCenterPager getNewsCenterPager() {
		return (NewsCenterPager) pagers.get(1);
	}

	/**
	 * ���õ������
	 */
	private void initListener() {
		// ���õײ���ǩ�������
		rgGroup.setOnCheckedChangeListener(new RadioGroupCheckedChangeListener());

		// ����ViewPager������
		viewPager.setOnPageChangeListener(new ContentPageChangeListener());
	}

	// ��ѡ��ť��ѡ�м���
	class RadioGroupCheckedChangeListener implements OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.btn_home:
				// viewPager.setCurrentItem(0) //���õ�ǰҳ��
				viewPager.setCurrentItem(0, false);// ȥ��ҳ��Ķ���
				break;
			case R.id.btn_news:
				viewPager.setCurrentItem(1, false);
				break;
			case R.id.btn_smart:
				viewPager.setCurrentItem(2, false);
				break;
			case R.id.btn_gov:
				viewPager.setCurrentItem(3, false);
				break;
			case R.id.btn_setting:
				viewPager.setCurrentItem(4, false);
				break;
			}
		}

	}

	// viewpager������
	class ContentPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		/**
		 * ����ViewPager���ص����ȼ��ؼ���ҳ�棬�������˷��û������� ����������Ӧ�õȵ�ҳ�汻ѡ�вż�������
		 */
		@Override
		public void onPageSelected(int position) {
			pagers.get(position).initData();// ����ҳ������
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}

	}

	// ViewPager����������
	class ContentAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return pagers.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {

			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			BasePager pager = pagers.get(position);
			container.addView(pager.mRootView);
			return pager.mRootView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

}
