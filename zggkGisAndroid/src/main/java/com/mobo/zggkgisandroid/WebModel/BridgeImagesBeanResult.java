package com.mobo.zggkgisandroid.WebModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 9.5获取桥梁图片
 * 
 * @author wuhao
 * 
 */
public class BridgeImagesBeanResult extends BaseResult {
	ArrayList<BridgeImagesBean> results;

	public ArrayList<BridgeImagesBean> getResults() {
		return results;
	}

	public void setResults(ArrayList<BridgeImagesBean> results) {
		this.results = results;
	}

	public class BridgeImagesBean {
		ArrayList<ImageList> image_list;

		public ArrayList<ImageList> getImage_list() {
			return image_list;
		}

		public void setImage_list(ArrayList<ImageList> image_list) {
			this.image_list = image_list;
		}

		public class ImageList {
			String image_data;

			public String getImage_data() {
				return image_data;
			}

			public void setImage_data(String image_data) {
				this.image_data = image_data;
			}
		}
	}

}
