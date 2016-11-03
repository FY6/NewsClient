package com.zhbj.zhbj.domain;

import java.util.ArrayList;

/**
 * 
 * ���������Ϣ�ķ�װ
 * 
 * �ֶ����ֱ���ͷ��������ص��ֶ���һ��, ����gson����
 * 
 * 
 * 2016-8-25 ����4:49:43 ���� NewsData.java
 * 
 * ��������
 **/
public class NewsData {
	public String retcode;
	public ArrayList<NewsMenuData> data;

	// ��������ݶ���
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

	// ����ҳ����11����ҳǩ�����ݶ���
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
