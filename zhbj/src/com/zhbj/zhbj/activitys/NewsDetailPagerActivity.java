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
 * ����ҳ
 * 
 * @author wfy
 * 
 */
public class NewsDetailPagerActivity extends Activity implements
		OnClickListener {

	private WebView content;// ��ҳ����
	private ImageView back;// ���ؼ�
	private ImageView share;// ����app
	private ImageView textSize;// ���������С
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
	 * ��ʼ��WebSettings
	 * 
	 */
	private void initWebSettings() {
		settings = content.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setDisplayZoomControls(true);
	}

	/**
	 * ��ʼ��view
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
	 * ��ʼ������
	 * 
	 */
	private void initListener() {
		back.setOnClickListener(this);
		share.setOnClickListener(this);
		textSize.setOnClickListener(this);
	}

	/**
	 * ��ʼ������
	 * 
	 */
	private void initData() {
		items = new String[] { "���������", "�������", "��������", "С������", "��С������" };

		Intent intent = getIntent();
		String url = intent.getExtras().getString("url", null);
		if (!TextUtils.isEmpty(url)) {
			content.loadUrl(url);
		}
	}

	/**
	 * ���ء��������������С
	 * 
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			back();// ����
			break;
		case R.id.iv_share:
			showShare();// ����
			break;
		case R.id.iv_textSize:
			textSize();// ���������С
			break;
		}
	}

	/**
	 * ����
	 * 
	 */
	private void back() {
		finish();
	}

	/**
	 * ����
	 * 
	 */
	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// �ر�sso��Ȩ
		oks.disableSSOWhenAuthorize();

		// ����ʱNotification��ͼ������� 2.5.9�Ժ�İ汾�����ô˷���
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
		oks.setTitle("һ������");
		// titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
		oks.setTitleUrl("http://sharesdk.cn");
		// text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
		oks.setText("��������");
		// ��������ͼƬ������΢����������ͼƬ��Ҫͨ����˺�����߼�д��ӿڣ�������ע�͵���������΢��
		oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
		// imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
		// oks.setImagePath("/sdcard/test.jpg");//ȷ��SDcard������ڴ���ͼƬ
		// url����΢�ţ��������Ѻ�����Ȧ����ʹ��
		oks.setUrl("http://sharesdk.cn");
		// comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
		oks.setComment("���ǲ��������ı�");
		// site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
		oks.setSite(getString(R.string.app_name));
		// siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
		oks.setSiteUrl("http://sharesdk.cn");

		// ��������GUI
		oks.show(this);
	}

	/**
	 * ���������С
	 * 
	 */
	private int currentChooseItem = 2;
	private int currentItem = 0;

	private void textSize() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("��������");
		builder.setSingleChoiceItems(items, currentChooseItem,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						System.out.println("ѡ���� " + which);
						currentItem = which;
					}
				});
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

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
		builder.setNegativeButton("ȡ��", null);
		builder.show();
	}
}
