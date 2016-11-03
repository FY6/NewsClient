package com.zhbj.zhbj.domain;

import java.util.ArrayList;

/**
 * 
 * 网络分类信息的封装
 * 
 * 字段名字必须和服务器返回的字段名一致, 方便gson解析
 * 
 * 
 * 2016-8-25 下午4:49:43 创建 NewsData.java
 * 
 * 新闻数据
 **/
public class NewsData {
	public String retcode;
	public ArrayList<NewsMenuData> data;

	// 侧边栏数据对象
	public class NewsMenuData {
		public String id;
		public String title;
		public int type;
		public String url;

		public ArrayList<NewsTabData> children;

		@Override
		public String toString() {
			return "NewsMenuData [id=" + id + ", title=" + title + ", type="
					+ type + ", url=" + url + ", children=" + children + "]";
		}

	}

	// 新闻页面下11个子页签的数据对象
	public class NewsTabData {
		public String id;
		public String title;
		public int type;
		public String url;

		@Override
		public String toString() {
			return "NewsTabData [id=" + id + ", title=" + title + ", type="
					+ type + ", url=" + url + "]";
		}

	}

	@Override
	public String toString() {
		return "NewsData [retcode=" + retcode + ", data=" + data + "]";
	}

}
