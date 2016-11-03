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
 * 本地缓存
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
			// 需要注意Url中有斜杠，会造成IO异常，所以可以进行Md5编码
			File file = new File(CACHEPATH, MD5Encoder.encode(url));
			if (file.exists()) {
				bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
				memoryCacheUtils.setMemoryCache(url, bitmap);
				System.out.println("从本地读取...");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	public void setLocalCache(Bitmap result, String url) {

		/**
		 * 将bitmap保存至本地
		 */
		try {
			// 需要注意Url中有斜杠，会造成IO异常
			File file = new File(CACHEPATH, MD5Encoder.encode(url));
			File parentFile = file.getParentFile();
			if (!parentFile.exists()) {// 如果文件夹不存在，就创建文件夹
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
