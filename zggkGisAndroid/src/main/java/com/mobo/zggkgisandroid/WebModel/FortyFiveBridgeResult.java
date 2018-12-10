package com.mobo.zggkgisandroid.WebModel;

import java.util.ArrayList;

/**
 * 9.3.45类桥分布
 * 
 * @author WuXiaoHao
 *
 */
public class FortyFiveBridgeResult extends BaseResult {
	ArrayList<FortyFiveBridge> results;

	public ArrayList<FortyFiveBridge> getResults() {
		return results;
	}

	public void setResults(ArrayList<FortyFiveBridge> results) {
		this.results = results;
	}

	public class FortyFiveBridge {
		String province_code;// 省份编码 VARCHAR
		String province_name;// 省份名称 VARCHAR
		String city_code;// 城市编码 VARCHAR
		String city_name;// 城市名称 VARCHAR
		String county_code;// 县编码 VARCHAR
		String county_name;// 县名称 VARCHAR
		String bridge_num;// 45类桥梁数量 VARCHAR

		public String getProvince_code() {
			return province_code;
		}

		public void setProvince_code(String province_code) {
			this.province_code = province_code;
		}

		public String getProvince_name() {
			return province_name;
		}

		public void setProvince_name(String province_name) {
			this.province_name = province_name;
		}

		public String getCity_code() {
			return city_code;
		}

		public void setCity_code(String city_code) {
			this.city_code = city_code;
		}

		public String getCity_name() {
			return city_name;
		}

		public void setCity_name(String city_name) {
			this.city_name = city_name;
		}

		public String getCounty_code() {
			return county_code;
		}

		public void setCounty_code(String county_code) {
			this.county_code = county_code;
		}

		public String getCounty_name() {
			return county_name;
		}

		public void setCounty_name(String county_name) {
			this.county_name = county_name;
		}

		public String getBridge_num() {
			return bridge_num;
		}

		public void setBridge_num(String bridge_num) {
			this.bridge_num = bridge_num;
		}

	}
}
