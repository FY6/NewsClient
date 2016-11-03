package com.zhbj.zhbj.utils.bitmap;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * �������ȡ����
 * 
 * @author wfy
 * 
 */
public class NetCacheUtils {
	private LocalCacheUtils localCacheUtils;
	private MemoryCacheUtils memoryCacheUtils;

	public NetCacheUtils(LocalCacheUtils localCacheUtils,
			MemoryCacheUtils memoryCacheUtils) {
		this.localCacheUtils = localCacheUtils;
		this.memoryCacheUtils = memoryCacheUtils;
	}

	public void getBitmapFromNet(ImageView iv_photos, String url) {
		BitmapAsyncTask bitmapAsyncTask = new BitmapAsyncTask();
		System.out.println("�������ȡ...");
		bitmapAsyncTask.execute(iv_photos, url);
	}

	/**
	 * �첽����
	 * 
	 * @author wfy
	 * 
	 */
	class BitmapAsyncTask extends AsyncTask<Object, Integer, Bitmap> {

		private ImageView photos;
		private String url;

		/**
		 * �˷��������߳������У���������ʱ����
		 */
		@Override
		protected Bitmap doInBackground(Object... params) {
			photos = (ImageView) params[0];
			url = (String) params[1];
			photos.setTag(url);// ��url�󶨵�iamgeview
			HttpURLConnection connection = null;
			Bitmap bitmap = null;
			try {
				connection = (HttpURLConnection) new URL(url).openConnection();
				connection.setReadTimeout(5000);
				connection.setConnectTimeout(5000);
				connection.connect();
				if (connection.getResponseCode() == 200) {
					InputStream is = connection.getInputStream();
					bitmap = BitmapFactory.decodeStream(is);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				connection.disconnect();// �ͷ�����
			}
			return bitmap;
		}

		/**
		 * �����߳���ִ�� ��doInBackground����ִ�н����ص��˷���������������ظ��˷���
		 */
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (bitmap != null) {
				if (url.equals(photos.getTag())) {// �����ǰ��url�󶨵�url��ͬ
					System.out.println("�������ȡͼƬ...");
					photos.setImageBitmap(bitmap);
					localCacheUtils.setLocalCache(bitmap, url);// ���浽����
					memoryCacheUtils.setMemoryCache(url, bitmap);// ���浽�ڴ�
				}
			}
		}

	}
}
