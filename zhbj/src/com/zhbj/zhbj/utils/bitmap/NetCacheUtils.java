package com.zhbj.zhbj.utils.bitmap;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * 从网络获取缓存
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
		System.out.println("从网络读取...");
		bitmapAsyncTask.execute(iv_photos, url);
	}

	/**
	 * 异步操作
	 * 
	 * @author wfy
	 * 
	 */
	class BitmapAsyncTask extends AsyncTask<Object, Integer, Bitmap> {

		private ImageView photos;
		private String url;

		/**
		 * 此方法在子线程中运行，可以做耗时操作
		 */
		@Override
		protected Bitmap doInBackground(Object... params) {
			photos = (ImageView) params[0];
			url = (String) params[1];
			photos.setTag(url);// 将url绑定到iamgeview
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
				connection.disconnect();// 释放连接
			}
			return bitmap;
		}

		/**
		 * 在主线程中执行 当doInBackground方法执行结束回调此方法，并将结果返回给此方法
		 */
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (bitmap != null) {
				if (url.equals(photos.getTag())) {// 如果当前的url绑定的url相同
					System.out.println("从网络读取图片...");
					photos.setImageBitmap(bitmap);
					localCacheUtils.setLocalCache(bitmap, url);// 缓存到本地
					memoryCacheUtils.setMemoryCache(url, bitmap);// 缓存到内存
				}
			}
		}

	}
}
