package com.zhbj.zhbj.utils;

import android.content.Context;

/**
 * 
 * 密度工具类:
 * 
 * 根据屏幕的密度，进行dp和px的互换
 * 
 * @author wfy
 * 
 */
public class DensityUtils {
	/**
	 * dp转为px
	 * 
	 * @return
	 * 
	 */
	public static int dp2px(Context context, float dp) {
		float density = context.getResources().getDisplayMetrics().density;
		int px = (int) (density * dp + 0.5f);// 加上0.5方式因为四舍五入
		return px;
	}

	public static int px2dp(Context context, int px) {

		float density = context.getResources().getDisplayMetrics().density;
		int dp = (int) (density / px + 0.5f);
		return dp;
	}

}
