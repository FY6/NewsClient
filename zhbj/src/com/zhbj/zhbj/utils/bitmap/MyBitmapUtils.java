package com.zhbj.zhbj.utils.bitmap;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * 三级缓存
 * 
 * @author wfy
 * 
 */
public class MyBitmapUtils {

	private NetCacheUtils netCacheUtils;
	private LocalCacheUtils localCacheUtils;
	private MemoryCacheUtils memoryCacheUtils;

	public MyBitmapUtils() {
		memoryCacheUtils = new MemoryCacheUtils();
		localCacheUtils = new LocalCacheUtils(memoryCacheUtils);
		netCacheUtils = new NetCacheUtils(localCacheUtils, memoryCacheUtils);
	}

	public void display(ImageView iv_photos, String listimage) {

		if (iv_photos == null) {
			return;
		}

		Bitmap bitmap = null;
		// 内存缓存
		if (memoryCacheUtils != null) {
			bitmap = memoryCacheUtils.getBitmapFromMemory(listimage);
			if (bitmap != null) {
				System.out.println("从内存读取图片...");
				iv_photos.setImageBitmap(bitmap);
				return;
			}
		}
		// 本地缓存
		if (localCacheUtils != null) {
			bitmap = localCacheUtils.getBitmapFromLocal(listimage);
			if (bitmap != null) {
				System.out.println("从本地读取图片...");
				iv_photos.setImageBitmap(bitmap);
				return;
			}
		}

		// 网络缓存
		if (netCacheUtils != null) {
			netCacheUtils.getBitmapFromNet(iv_photos, listimage);
		}
	}
}
