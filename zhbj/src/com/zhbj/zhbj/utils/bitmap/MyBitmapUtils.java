package com.zhbj.zhbj.utils.bitmap;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * ��������
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
		// �ڴ滺��
		if (memoryCacheUtils != null) {
			bitmap = memoryCacheUtils.getBitmapFromMemory(listimage);
			if (bitmap != null) {
				System.out.println("���ڴ��ȡͼƬ...");
				iv_photos.setImageBitmap(bitmap);
				return;
			}
		}
		// ���ػ���
		if (localCacheUtils != null) {
			bitmap = localCacheUtils.getBitmapFromLocal(listimage);
			if (bitmap != null) {
				System.out.println("�ӱ��ض�ȡͼƬ...");
				iv_photos.setImageBitmap(bitmap);
				return;
			}
		}

		// ���绺��
		if (netCacheUtils != null) {
			netCacheUtils.getBitmapFromNet(iv_photos, listimage);
		}
	}
}
