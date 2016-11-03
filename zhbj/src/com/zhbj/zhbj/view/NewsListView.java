package com.zhbj.zhbj.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zhbj.zhbj.R;
import com.zhbj.zhbj.exception.NotHasOnRefrshListenerException;

/**
 * 2016-9-8 上午12:42:44
 * 
 * 自定义ListView
 * 
 **/
public class NewsListView extends ListView implements OnScrollListener,
		android.widget.AdapterView.OnItemClickListener {

	public static final int PULL_REFRESH = 0;// 下拉刷新
	public static final int RELEASE_REFRESH = 1;// 释放刷新
	public static final int REFRESHING = 2;// 正在刷新
	public int currentState = PULL_REFRESH;// 默认状态为 下拉刷新

	private int headerViewHeight;// headerView 的高
	private int downY = -1;
	private View headerView;// 头布局

	/**
	 * 定义向上、向下旋转动画
	 * 
	 */
	private RotateAnimation upAnima, downAnima;

	@ViewInject(R.id.iv_progress)
	private ImageView ivProgress;

	@ViewInject(R.id.pb_progress)
	private ProgressBar pb_progress;

	@ViewInject(R.id.tv_pullrefresh)
	private TextView tv_pullrefresh;

	@ViewInject(R.id.tv_regresh_date)
	private TextView tv_regresh_date;

	private OnRefrshListener listener;

	private View footView;// 脚布局
	private int footViewHeight;

	/**
	 * xml创建调用此构造方法
	 * 
	 * @param context
	 *            上下文
	 * 
	 * @param attrs
	 *            属性
	 */
	public NewsListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * 代码创建调用此构造方法
	 * 
	 * @param context
	 *            上下文
	 */
	public NewsListView(Context context) {
		super(context);
		init();
	}

	/**
	 * 初始化操作
	 * 
	 */
	private void init() {
		setOnScrollListener(this);
		initHeaderView();
		initFootView();
		initAnimation();
	}

	/**
	 * 初始化footView
	 * 
	 */
	private void initFootView() {
		footView = inflate(getContext(), R.layout.loadingmore_footview, null);
		footView.measure(0, 0);
		footViewHeight = footView.getMeasuredHeight();
		footView.setPadding(0, -footViewHeight, 0, 0);
		this.addFooterView(footView);
	}

	/**
	 * 初始化HeaderView
	 * 
	 */
	private void initHeaderView() {
		headerView = inflate(getContext(),
				R.layout.pullrefresh_header_newslist, null);
		ViewUtils.inject(this, headerView);// 注入view事件

		headerView.measure(0, 0);// 系统自动调用onMeasure方法测量view
		headerViewHeight = headerView.getMeasuredHeight();
		headerView.setPadding(0, -headerViewHeight, 0, 0);
		this.addHeaderView(headerView);
	}

	/**
	 * 初始化动画
	 * 
	 */
	private void initAnimation() {
		// 向上动画
		upAnima = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		upAnima.setDuration(300);
		upAnima.setFillAfter(true);

		// 向下动画
		downAnima = new RotateAnimation(-180, -360, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		downAnima.setDuration(300);
		downAnima.setFillAfter(true);
	}

	/**
	 * 下拉刷新逻辑: 仔细分析下拉刷新的逻辑思想
	 * 
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downY = (int) ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:

			if (downY == -1) {
				downY = (int) ev.getRawY();
			}

			/**
			 * 如果是正在刷新状态，那么久不能做任何操作，把相应的操作让用户请求数据，那么程序员怎么知道什么时候
			 * 是正在刷新状态呢？所以我们应该暴露一个接口，让用户监听我们的listview当前的状态
			 * 
			 */
			if (currentState == REFRESHING) {
				break;
			}

			int deltal = (int) (ev.getRawY() - downY);
			int paddingTop = -headerViewHeight + deltal;

			if (paddingTop > -headerViewHeight
					&& getFirstVisiblePosition() == 0) {
				headerView.setPadding(0, paddingTop, 0, 0);
				// 从下拉刷新 ——>// 释放刷新
				if (paddingTop >= 0 && currentState == PULL_REFRESH) {
					currentState = NewsListView.RELEASE_REFRESH;
					refreshHeaderView();

					// 从 释放刷新 ——>变为下拉刷新
				} else if (paddingTop < 0 && currentState == RELEASE_REFRESH) {
					currentState = NewsListView.PULL_REFRESH;
					refreshHeaderView();
				}
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			downY = -1;
			if (currentState == PULL_REFRESH) {
				headerView.setPadding(0, -headerViewHeight, 0, 0);
			} else if (currentState == RELEASE_REFRESH) {
				currentState = REFRESHING;
				headerView.setPadding(0, 0, 0, 0);
				refreshHeaderView();
				// 下拉刷新回调
				onBackCall();
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 下拉刷新回调
	 * 
	 * 当用户做相应的动作回调，特定的方法
	 * 
	 * 如果没有设置监听，将抛出异常
	 * 
	 */
	private void onBackCall() {
		if (listener != null) {
			if (!isLoadingMore) {
				listener.onPullRefresh();
			} else {
				listener.onLoadingMore();
			}

		} else {
			throw new NotHasOnRefrshListenerException(
					"NotHasOnRefrshListenerException");
		}
	}

	/**
	 * 设置监听
	 * 
	 */

	public void setOnRefrshListener(OnRefrshListener listener) {
		this.listener = listener;
	}

	public interface OnRefrshListener {
		// 下拉刷新
		public void onPullRefresh();

		// 加载更多
		public void onLoadingMore();
	}

	/*
	 * 刷新HeaderView
	 */
	private void refreshHeaderView() {
		switch (currentState) {
		case NewsListView.PULL_REFRESH:// 下拉刷新
			tv_pullrefresh.setText("下拉刷新...");
			ivProgress.startAnimation(downAnima);
			break;
		case NewsListView.RELEASE_REFRESH:// 松开刷新
			tv_pullrefresh.setText("松开刷新...");
			ivProgress.startAnimation(upAnima);
			break;
		case NewsListView.REFRESHING:// 正在刷新
			ivProgress.clearAnimation();// 必须先清空动画，才能view隐藏
			tv_pullrefresh.setText("正在刷新...");
			ivProgress.setVisibility(View.INVISIBLE);
			pb_progress.setVisibility(View.VISIBLE);
			break;
		}
	}

	/**
	 * 当程序员请求数据完成需要，把listview重新设置成出事状态，即重置listview
	 * 
	 * 数据请求完成，应该调用此方法，重置listview状态
	 * 
	 */
	public void refreshComplete(boolean isLoadingMore) {
		if (isLoadingMore) {
			footView.setPadding(0, -footViewHeight, 0, 0);
			this.isLoadingMore = false;
		} else {
			headerView.setPadding(0, -headerViewHeight, 0, 0);
			currentState = PULL_REFRESH;
			ivProgress.setVisibility(View.VISIBLE);
			pb_progress.setVisibility(View.INVISIBLE);

			tv_pullrefresh.setText("下拉刷新...");
			tv_regresh_date.setText("最后刷新时间:" + getCurrentTime());
		}

	}

	/*
	 * 获取当前时间
	 */
	private String getCurrentTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	// 当前状态是否是加载更多
	private boolean isLoadingMore = false;

	/**
	 * 滚动状态改变调用此方法
	 * 
	 * SCROLL_STATE_IDLE:闲置状态
	 * 
	 * SCROLL_STATE_TOUCH_SCROLL:滚动状态
	 * 
	 * SCROLL_STATE_FLING:惯性滚动状态
	 */
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				|| scrollState == OnScrollListener.SCROLL_STATE_FLING) {
			if (getLastVisiblePosition() == (getCount() - 1) && !isLoadingMore) {
				footView.setPadding(0, 0, 0, 0);
				this.setSelection(getCount() - 1);// 选中位置
				isLoadingMore = true;
				onBackCall();
			}
		}
	}

	/**
	 * 滚动时调用次方法
	 * 
	 */
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
	}

	private OnItemClickListener mListener;

	@Override
	public void setOnItemClickListener(
			android.widget.AdapterView.OnItemClickListener listener) {
		super.setOnItemClickListener(this);
		mListener = listener;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (position >= getHeaderViewsCount()) {
			mListener.onItemClick(parent, view, position
					- getHeaderViewsCount(), id);
		}
	}

}
