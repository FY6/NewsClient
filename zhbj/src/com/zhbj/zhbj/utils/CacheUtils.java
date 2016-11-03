package com.zhbj.zhbj.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * ª∫¥Êπ§æﬂ¿‡
 * 
 * @author wfy
 * 
 */
public class CacheUtils {
	private static final String CACHE_NAME = "cache";

	public static void putCache(Context context, String urlToKey,
			String JsonToValue) {
		SharedPreferences mPref = context.getSharedPreferences(CACHE_NAME,
				Context.MODE_PRIVATE);
		mPref.edit().putString(urlToKey, JsonToValue).commit();
	}

	public static String getCache(Context context, String urlToKey) {
		SharedPreferences mPref = context.getSharedPreferences(CACHE_NAME,
				Context.MODE_PRIVATE);
		return mPref.getString(urlToKey, null);
	}
}
