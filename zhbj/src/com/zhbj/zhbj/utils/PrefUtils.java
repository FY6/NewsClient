package com.zhbj.zhbj.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 2016-8-23 下午1:11:44 创建 PreUtils.java
 * 
 * SharedPreferences工具类
 **/
public class PrefUtils {

	private static final String PRE_NAME = "config";

	public static boolean getBoolean(Context context, String key,
			boolean defValue) {
		SharedPreferences mPref = context.getSharedPreferences(PRE_NAME,
				Context.MODE_PRIVATE);
		return mPref.getBoolean(key, defValue);
	}

	public static void setBoolean(Context context, String key, boolean value) {
		SharedPreferences mPref = context.getSharedPreferences(PRE_NAME,
				Context.MODE_PRIVATE);
		mPref.edit().putBoolean(key, value).commit();
	}

	public static void setString(Context context, String key, String value) {
		SharedPreferences mPref = context.getSharedPreferences(PRE_NAME,
				Context.MODE_PRIVATE);
		mPref.edit().putString(key, value).commit();
	}

	public static String getString(Context context, String key, String defValue) {
		SharedPreferences mPref = context.getSharedPreferences(PRE_NAME,
				Context.MODE_PRIVATE);
		return mPref.getString(key, defValue);
	}
}
