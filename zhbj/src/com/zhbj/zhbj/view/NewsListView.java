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
 * 2016-9-8 ����12:42:44
 * 
 * �Զ���ListView
 * 
 **/
public class NewsListView extends ListView implements OnScrollListener,
		android.widget.AdapterView.OnItemClickListener {

	public static final int PULL_REFRESH = 0;// ����ˢ��
	public static final int RELEASE_REFRESH = 1;// �ͷ�ˢ��
	public static final int REFRESHING = 2;// ����ˢ��
	public int currentState = PULL_REFRESH;// Ĭ��״̬Ϊ ����ˢ��

	private int headerViewHeight;// headerView �ĸ�
	private int downY = -1;
	private View headerView;// ͷ����

	/**
	 * �������ϡ�������ת����
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

	private View footView;// �Ų���
	private int footViewHeight;

	/**
	 * xml�������ô˹��췽��
	 * 
	 * @param context
	 *            ������
	 * 
	 * @param attrs
	 *            ����
	 */
	public NewsListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * ���봴�����ô˹��췽��
	 * 
	 * @param context
	 *            ������
	 */
	public NewsListView(Context context) {
		super(context);
		init();
	}

	/**
	 * ��ʼ������
	 * 
	 */
	private void init() {
		setOnScrollListener(this);
		initHeaderView();
		initFootView();
		initAnimation();
	}

	/**
	 * ��ʼ��footView
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
	 * ��ʼ��HeaderView
	 * 
	 */
	private void initHeaderView() {
		headerView = inflate(getContext(),
				R.layout.pullrefresh_header_newslist, null);
		ViewUtils.inject(this, headerView);// ע��view�¼�

		headerView.measure(0, 0);// ϵͳ�Զ�����onMeasure��������view
		headerViewHeight = headerView.getMeasuredHeight();
		headerView.setPadding(0, -headerViewHeight, 0, 0);
		this.addHeaderView(headerView);
	}

	/**
	 * ��ʼ������
	 * 
	 */
	private void initAnimation() {
		// ���϶���
		upAnima = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		upAnima.setDuration(300);
		upAnima.setFillAfter(true);

		// ���¶���
		downAnima = new RotateAnimation(-180, -360, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		downAnima.setDuration(300);
		downAnima.setFillAfter(true);
	}

	/**
	 * ����ˢ���߼�: ��ϸ��������ˢ�µ��߼�˼��
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
			 * ���������ˢ��״̬����ô�ò������κβ���������Ӧ�Ĳ������û��������ݣ���ô����Ա��ô֪��ʲôʱ��
			 * ������ˢ��״̬�أ���������Ӧ�ñ�¶һ���ӿڣ����û��������ǵ�listview��ǰ��״̬
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
				// ������ˢ�� ����>// �ͷ�ˢ��
				if (paddingTop >= 0 && currentState == PULL_REFRESH) {
					currentState = NewsListView.RELEASE_REFRESH;
					refreshHeaderView();

					// �� �ͷ�ˢ�� ����>��Ϊ����ˢ��
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
				// ����ˢ�»ص�
				onBackCall();
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * ����ˢ�»ص�
	 * 
	 * ���û�����Ӧ�Ķ����ص����ض��ķ���
	 * 
	 * ���û�����ü��������׳��쳣
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
	 * ���ü���
	 * 
	 */

	public void setOnRefrshListener(OnRefrshListener listener) {
		this.listener = listener;
	}

	public interface OnRefrshListener {
		// ����ˢ��
		public void onPullRefresh();

		// ���ظ���
		public void onLoadingMore();
	}

	/*
	 * ˢ��HeaderView
	 */
	private void refreshHeaderView() {
		switch (currentState) {
		case NewsListView.PULL_REFRESH:// ����ˢ��
			tv_pullrefresh.setText("����ˢ��...");
			ivProgress.startAnimation(downAnima);
			break;
		case NewsListView.RELEASE_REFRESH:// �ɿ�ˢ��
			tv_pullrefresh.setText("�ɿ�ˢ��...");
			ivProgress.startAnimation(upAnima);
			break;
		case NewsListView.REFRESHING:// ����ˢ��
			ivProgress.clearAnimation();// ��������ն���������view����
			tv_pullrefresh.setText("����ˢ��...");
			ivProgress.setVisibility(View.INVISIBLE);
			pb_progress.setVisibility(View.VISIBLE);
			break;
		}
	}

	/**
	 * ������Ա�������������Ҫ����listview�������óɳ���״̬��������listview
	 * 
	 * ����������ɣ�Ӧ�õ��ô˷���������listview״̬
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

			tv_pullrefresh.setText("����ˢ��...");
			tv_regresh_date.setText("���ˢ��ʱ��:" + getCurrentTime());
		}

	}

	/*
	 * ��ȡ��ǰʱ��
	 */
	private String getCurrentTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	// ��ǰ״̬�Ƿ��Ǽ��ظ���
	private boolean isLoadingMore = false;

	/**
	 * ����״̬�ı���ô˷���
	 * 
	 * SCROLL_STATE_IDLE:����״̬
	 * 
	 * SCROLL_STATE_TOUCH_SCROLL:����״̬
	 * 
	 * SCROLL_STATE_FLING:���Թ���״̬
	 */
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				|| scrollState == OnScrollListener.SCROLL_STATE_FLING) {
			if (getLastVisiblePosition() == (getCount() - 1) && !isLoadingMore) {
				footView.setPadding(0, 0, 0, 0);
				this.setSelection(getCount() - 1);// ѡ��λ��
				isLoadingMore = true;
				onBackCall();
			}
		}
	}

	/**
	 * ����ʱ���ôη���
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
