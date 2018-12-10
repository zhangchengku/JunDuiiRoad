package com.mobo.zggkgisandroid.WebModel;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 搜索自动提示列表 2016/10/17
 */
public class SearchNoticesList extends BaseResult {

	private List<SearchListBean> results;//搜索结果集

	public List<SearchListBean> getResults() {
		return results;
	}

	public void setResults(List<SearchListBean> results) {
		this.results = results;
	}

	public class SearchListBean implements Serializable {
		private String province_code;// 省份编码 123001 代表全国
		private String province_name;// 省份名称
		private String road_code;// 线路编码
		private String result_mil_num;// 总里程数或总延米数
		private String Result_name;// 模糊提示名字
		private String type;// 0国道、1省道、2桥梁
		private String bridge_code;// 桥梁编码

		public String getProvince_code() {
			return province_code;
		}

		public void setProvince_code(String province_code) {
			this.province_code = province_code;
		}

		public String getBridge_code() {
			return bridge_code;
		}

		public void setBridge_code(String bridge_code) {
			this.bridge_code = bridge_code;
		}

		public String getProvince_name() {
			return province_name;
		}

		public void setProvince_name(String province_name) {
			this.province_name = province_name;
		}

		public String getRoad_code() {
			return road_code;
		}

		public void setRoad_code(String road_code) {
			this.road_code = road_code;
		}

		public String getResult_mil_num() {
			return result_mil_num;
		}

		public void setResult_mil_num(String result_mil_num) {
			this.result_mil_num = result_mil_num;
		}

		public String getResult_name() {
			return Result_name;
		}

		public void setResult_name(String result_name) {
			Result_name = result_name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}

}
