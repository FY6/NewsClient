package com.zhbj.zhbj.utils.bitmap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * ÄÚ´æ»º´æ
 * 
 * @author wfy
 * 
 */
public class MemoryCacheUtils {
	// LruDiskCache.open(directory, appVersion, valueCount, maxSize)
	private LruCache<String, Bitmap> lruCache;

	public MemoryCacheUtils() {
		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 8);
		lruCache = new LruCache<String, Bitmap>(maxMemory) {

			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			}

		};
	}

	public Bitmap getBitmapFromMemory(String url) {
		return lruCache.get(url);
	}

	public void setMemoryCache(String url, Bitmap bitmap) {
		lruCache.put(url, bitmap);
	}

}
