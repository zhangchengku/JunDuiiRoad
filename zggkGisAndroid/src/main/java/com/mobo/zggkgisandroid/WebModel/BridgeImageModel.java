package com.mobo.zggkgisandroid.WebModel;

import java.util.List;

public class BridgeImageModel extends BaseResult {

	private List<ResultsBean> results;

	public List<ResultsBean> getResults() {
		return results;
	}

	public void setResults(List<ResultsBean> results) {
		this.results = results;
	}

	public static class ResultsBean {

		private List<ImageListBean> image_list;

		public List<ImageListBean> getImage_list() {
			return image_list;
		}

		public void setImage_list(List<ImageListBean> image_list) {
			this.image_list = image_list;
		}

		public static class ImageListBean {
			private String image_data;

			public String getImage_data() {
				return image_data;
			}

			public void setImage_data(String image_data) {
				this.image_data = image_data;
			}
		}

	}
}
