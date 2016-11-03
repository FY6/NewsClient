package com.zhbj.zhbj.global;

import cn.jpush.android.api.JPushInterface;
import android.app.Application;

/**
 * 重写Application
 * 
 * @author wfy
 * 
 */
public class JPullApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
		JPushInterface.init(this);// 初始化 JPush
	}

}
