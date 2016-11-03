package com.zhbj.zhbj.utils;

import android.content.Context;

/**
 * 
 * �ܶȹ�����:
 * 
 * ������Ļ���ܶȣ�����dp��px�Ļ���
 * 
 * @author wfy
 * 
 */
public class DensityUtils {
	/**
	 * dpתΪpx
	 * 
	 * @return
	 * 
	 */
	public static int dp2px(Context context, float dp) {
		float density = context.getResources().getDisplayMetrics().density;
		int px = (int) (density * dp + 0.5f);// ����0.5��ʽ��Ϊ��������
		return px;
	}

	public static int px2dp(Context context, int px) {

		float density = context.getResources().getDisplayMetrics().density;
		int dp = (int) (density / px + 0.5f);
		return dp;
	}

}
