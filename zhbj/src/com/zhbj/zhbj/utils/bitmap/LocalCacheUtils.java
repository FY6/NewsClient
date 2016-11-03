package com.zhbj.zhbj.utils.bitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.zhbj.zhbj.utils.MD5Encoder;

/**
 * ���ػ���
 * 
 * @author wfy
 * 
 */
public class LocalCacheUtils {

	private static final String CACHEPATH = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/zhbj_cache";

	private MemoryCacheUtils memoryCacheUtils;

	public LocalCacheUtils(MemoryCacheUtils memoryCacheUtils) {
		this.memoryCacheUtils = memoryCacheUtils;
	}

	public Bitmap getBitmapFromLocal(String url) {
		Bitmap bitmap = null;
		try {
			// ��Ҫע��Url����б�ܣ������IO�쳣�����Կ��Խ���Md5����
			File file = new File(CACHEPATH, MD5Encoder.encode(url));
			if (file.exists()) {
				bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
				memoryCacheUtils.setMemoryCache(url, bitmap);
				System.out.println("�ӱ��ض�ȡ...");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	public void setLocalCache(Bitmap result, String url) {

		/**
		 * ��bitmap����������
		 */
		try {
			// ��Ҫע��Url����б�ܣ������IO�쳣
			File file = new File(CACHEPATH, MD5Encoder.encode(url));
			File parentFile = file.getParentFile();
			if (!parentFile.exists()) {// ����ļ��в����ڣ��ʹ����ļ���
				parentFile.mkdirs();
			}
			if (!file.exists()) {
				result.compress(CompressFormat.JPEG, 100, new FileOutputStream(
						file));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
