package com.zhbj.zhbj.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.widget.ImageView;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.zhbj.zhbj.R;

/**
 * 打开网页
 * 
 * @author wfy
 * 
 */
public class NewsDetailPagerActivity extends Activity implements
		OnClickListener {

	private WebView content;// 网页内容
	private ImageView back;// 返回键
	private ImageView share;// 共享app
	private ImageView textSize;// 设置字体大小
	private WebSettings settings;
	private String[] items;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initViews();
		initWebSettings();
		initListener();
		initData();
	}

	/**
	 * 初始化WebSettings
	 * 
	 */
	private void initWebSettings() {
		settings = content.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setDisplayZoomControls(true);
	}

	/**
	 * 初始化view
	 * 
	 */
	private void initViews() {
		setContentView(R.layout.activity_open_pager);
		content = (WebView) findViewById(R.id.wv_content);
		back = (ImageView) findViewById(R.id.iv_back);
		share = (ImageView) findViewById(R.id.iv_share);
		textSize = (ImageView) findViewById(R.id.iv_textSize);
	}

	/**
	 * 初始化监听
	 * 
	 */
	private void initListener() {
		back.setOnClickListener(this);
		share.setOnClickListener(this);
		textSize.setOnClickListener(this);
	}

	/**
	 * 初始化数据
	 * 
	 */
	private void initData() {
		items = new String[] { "超大号字体", "大号字体", "正常字体", "小号字体", "超小号字体" };

		Intent intent = getIntent();
		String url = intent.getExtras().getString("url", null);
		if (!TextUtils.isEmpty(url)) {
			content.loadUrl(url);
		}
	}

	/**
	 * 返回、分享、设置字体大小
	 * 
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			back();// 返回
			break;
		case R.id.iv_share:
			showShare();// 共享
			break;
		case R.id.iv_textSize:
			textSize();// 设置字体大小
			break;
		}
	}

	/**
	 * 返回
	 * 
	 */
	private void back() {
		finish();
	}

	/**
	 * 共享
	 * 
	 */
	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle("一键分享");
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		oks.setText("分享理由");
		// 分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
		oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		// oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl("http://sharesdk.cn");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("我是测试评论文本");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://sharesdk.cn");

		// 启动分享GUI
		oks.show(this);
	}

	/**
	 * 设置字体大小
	 * 
	 */
	private int currentChooseItem = 2;
	private int currentItem = 0;

	private void textSize() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("字体设置");
		builder.setSingleChoiceItems(items, currentChooseItem,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						System.out.println("选中了 " + which);
						currentItem = which;
					}
				});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (currentItem) {
				case 0:
					settings.setTextSize(TextSize.LARGEST);
					break;
				case 1:
					settings.setTextSize(TextSize.LARGER);
					break;
				case 2:
					settings.setTextSize(TextSize.NORMAL);
					break;
				case 3:
					settings.setTextSize(TextSize.SMALLER);
					break;
				case 4:
					settings.setTextSize(TextSize.SMALLEST);
					break;
				}
				currentChooseItem = currentItem;
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", null);
		builder.show();
	}
}
