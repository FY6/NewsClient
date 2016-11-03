package com.zhbj.zhbj.domain;

import java.util.ArrayList;

/**
 * ×éÍ¼bean
 * 
 * @author wfy
 * 
 */
public class GroupPhotoData {
	public GroupNewsData data;
	public String retcode;

	public class GroupNewsData {
		public String more;
		public String title;
		public ArrayList<News> news;

		@Override
		public String toString() {
			return "GroupNewsData [title=" + title + "]";
		}

	}

	public class News {
		public String id;
		public String listimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;

		@Override
		public String toString() {
			return "News [title=" + title + "]";
		}

	}

	@Override
	public String toString() {
		return "GroupPhotoData [data=" + data + "]";
	}

}
