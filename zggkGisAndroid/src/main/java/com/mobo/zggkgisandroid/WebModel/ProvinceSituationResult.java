package com.mobo.zggkgisandroid.WebModel;

import java.io.Serializable;

/**
 * 8.2.省份概况
 * 
 * @author WuXiaoHao
 * 
 */
public class ProvinceSituationResult extends BaseResult implements Serializable {
	ProvinceSituation results;

	public ProvinceSituation getResults() {
		return results;
	}

	public void setResults(ProvinceSituation results) {
		this.results = results;
	}

	public class ProvinceSituation implements Serializable {
		String province_code;// 省份编码
		String national_road_num;// 国道数 VARCHAR
		String province_road_num;// 省道数 VARCHAR
		String county_road_num;// 县道数 VARCHAR
		String road_mil_num;// 道路总里程 VARCHAR
		String Bridge_num;// 桥梁数 VARCHAR
		String Bridge_mil_num;// 桥梁总里程 VARCHAR
		String new_road_mil_num;// 十二五新建公路里程 VARCHAR
		String result_mil_num;// 评定里程
		String result_total_num;// 评定总数
		String result_indicator_value;// 指标信息

		public String getResult_mil_num() {
			return result_mil_num;
		}

		public void setResult_mil_num(String result_mil_num) {
			this.result_mil_num = result_mil_num;
		}

		public String getResult_total_num() {
			return result_total_num;
		}

		public void setResult_total_num(String result_total_num) {
			this.result_total_num = result_total_num;
		}

		public String getResult_indicator_value() {
			return result_indicator_value;
		}

		public void setResult_indicator_value(String result_indicator_value) {
			this.result_indicator_value = result_indicator_value;
		}

		public String getProvince_code() {
			return province_code;
		}

		public void setProvince_code(String province_code) {
			this.province_code = province_code;
		}

		public String getNational_road_num() {
			return national_road_num;
		}

		public void setNational_road_num(String national_road_num) {
			this.national_road_num = national_road_num;
		}

		public String getProvince_road_num() {
			return province_road_num;
		}

		public void setProvince_road_num(String province_road_num) {
			this.province_road_num = province_road_num;
		}

		public String getCounty_road_num() {
			return county_road_num;
		}

		public void setCounty_road_num(String county_road_num) {
			this.county_road_num = county_road_num;
		}

		public String getRoad_mil_num() {
			return road_mil_num;
		}

		public void setRoad_mil_num(String road_mil_num) {
			this.road_mil_num = road_mil_num;
		}

		public String getBridge_num() {
			return Bridge_num;
		}

		public void setBridge_num(String bridge_num) {
			Bridge_num = bridge_num;
		}

		public String getBridge_mil_num() {
			return Bridge_mil_num;
		}

		public void setBridge_mil_num(String bridge_mil_num) {
			Bridge_mil_num = bridge_mil_num;
		}

		public String getNew_road_mil_num() {
			return new_road_mil_num;
		}

		public void setNew_road_mil_num(String new_road_mil_num) {
			this.new_road_mil_num = new_road_mil_num;
		}

	}
}
