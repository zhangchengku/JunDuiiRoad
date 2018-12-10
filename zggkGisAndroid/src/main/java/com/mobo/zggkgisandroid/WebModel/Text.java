package com.mobo.zggkgisandroid.WebModel;

import java.util.ArrayList;

public class Text extends BaseResult {
	public Text() {
		setStatus("Y");
		setError_code("");
		setError_msg("");
		results = new T();
	}

	T results;

	public T getResults() {
		return results;
	}

	public void setResults(T results) {
		this.results = results;
	}

	public class T {
		String carousel_time = "3";// 轮播时间 VARCHAR 单位秒 默认3秒
		ArrayList<I> images;// 图片集合
		// VARCHAR 数组

		public T() {
			images = new ArrayList<Text.T.I>();
			I name = new I();
			name.setImage_url("http://cdn.duitang.com/uploads/item/201509/10/20150910194703_E3RKW.jpeg");
			images.add(name);
			name = new I();
			name.setImage_url("http://d.hiphotos.baidu.com/zhidao/pic/item/91529822720e0cf38c6f9f6f0b46f21fbf09aa8e.jpg");
			images.add(name);
			name = new I();
			name.setImage_url("http://cdn.duitang.com/uploads/item/201510/18/20151018164141_Azthm.jpeg");
			images.add(name);
		}

		public class I {
			String image_url;// 图片链接 VARCHAR

			public String getImage_url() {
				return image_url;
			}

			public void setImage_url(String image_url) {
				this.image_url = image_url;
			}

		}
	}
}
