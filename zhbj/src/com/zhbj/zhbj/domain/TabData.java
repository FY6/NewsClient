package com.zhbj.zhbj.domain;

import java.util.ArrayList;

/**
 * 2016-8-30 ÉÏÎç1:19:03 ´´½¨ TabData.java
 * 
 **/
public class TabData {
	public String retcode;

	public TabDetail data;

	public class TabDetail {
		public String more;
		public ArrayList<TabNewsData> news;
		public String title;
		public ArrayList<TopNewsData> topnews;

		@Override
		public String toString() {
			return "TabDetail [title=" + title + "]";
		}

	}

	public class TabNewsData {
		public String id;
		public String listimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;

		@Override
		public String toString() {
			return "TabNewsData [title=" + title + "]";
		}

	}

	public class TopNewsData {
		public String id;
		public String topimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;

		@Override
		public String toString() {
			return "TopNewsData [title=" + title + "]";
		}
	}

	@Override
	public String toString() {
		return "TabData [data=" + data + "]";
	}

}
