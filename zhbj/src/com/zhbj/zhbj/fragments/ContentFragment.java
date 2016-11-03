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
 * 2016-8-23 下午1:59:03 创建 MainFragment.java
 * 
 * 主页面Fragment
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
		ViewUtils.inject(this, view); // 注入view和事件
		return view;
	}

	@Override
	public void initData() {
		initListener();

		rgGroup.check(R.id.btn_home);// 默认选中首页
		pagers = new ArrayList<BasePager>();

		pagers.add(new HomePager(mActivity));
		pagers.add(new NewsCenterPager(mActivity));
		pagers.add(new SmartServicePager(mActivity));
		pagers.add(new GovAffairsPager(mActivity));
		pagers.add(new SettingPager(mActivity));

		viewPager.setAdapter(new ContentAdapter());

		// 默认加载首页数据
		pagers.get(0).initData();
	}

	// 获取NewsCenterPager
	public NewsCenterPager getNewsCenterPager() {
		return (NewsCenterPager) pagers.get(1);
	}

	/**
	 * 设置点击监听
	 */
	private void initListener() {
		// 设置底部标签点击监听
		rgGroup.setOnCheckedChangeListener(new RadioGroupCheckedChangeListener());

		// 设置ViewPager监听器
		viewPager.setOnPageChangeListener(new ContentPageChangeListener());
	}

	// 单选按钮的选中监听
	class RadioGroupCheckedChangeListener implements OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.btn_home:
				// viewPager.setCurrentItem(0) //设置当前页面
				viewPager.setCurrentItem(0, false);// 去掉页面的动画
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

	// viewpager监听器
	class ContentPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		/**
		 * 由于ViewPager的特点是先加载几个页面，这样会浪费用户的流量 ，所以我们应该等到页面被选中才加载数据
		 */
		@Override
		public void onPageSelected(int position) {
			pagers.get(position).initData();// 加载页面数据
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}

	}

	// ViewPager数据适配器
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
