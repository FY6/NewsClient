package com.zhbj.zhbj.global;

import cn.jpush.android.api.JPushInterface;
import android.app.Application;

/**
 * ��дApplication
 * 
 * @author wfy
 * 
 */
public class JPullApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		JPushInterface.setDebugMode(true); // ���ÿ�����־,����ʱ��ر���־
		JPushInterface.init(this);// ��ʼ�� JPush
	}

}
