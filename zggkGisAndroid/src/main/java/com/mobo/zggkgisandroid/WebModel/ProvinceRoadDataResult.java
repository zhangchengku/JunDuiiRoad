package com.mobo.zggkgisandroid.WebModel;

import java.util.ArrayList;

/**
 * 8.3.省份路况数据
 * 
 * @author WuXiaoHao
 * 
 */
public class ProvinceRoadDataResult extends BaseResult {
	ArrayList<ProvinceRoadData> results;

	public ArrayList<ProvinceRoadData> getResults() {
		return results;
	}

	public void setResults(ArrayList<ProvinceRoadData> results) {
		this.results = results;
	}

	public class ProvinceRoadData {
		String road_code;// 公路编码 VARCHAR
		String road_name;// 公路名称 VARCHAR
		String indicator_number;// PCI指标数值 VARCHAR
		String indicator_grade;// 返回1到5 1 到5等级对应的颜色值固定。1 代表优，2 代表良，3 代表中，4 代表次，5
								// 代表差。具体颜色值见文档附件的图片说明。
		String road_mail;
		String guid;// 唯一键值 VARCHAR

		public String getGuid() {
			return guid;
		}

		public void setGuid(String guid) {
			this.guid = guid;
		}

		public String getRoad_mail() {
			return road_mail;
		}

		public void setRoad_mail(String road_mail) {
			this.road_mail = road_mail;
		}

		public String getRoad_code() {
			return road_code;
		}

		public String getIndicator_grade() {
			return indicator_grade;
		}

		public void setIndicator_grade(String indicator_grade) {
			this.indicator_grade = indicator_grade;
		}

		public void setRoad_code(String road_code) {
			this.road_code = road_code;
		}

		public String getRoad_name() {
			return road_name;
		}

		public void setRoad_name(String road_name) {
			this.road_name = road_name;
		}

		public String getIndicator_number() {
			return indicator_number;
		}

		public void setIndicator_number(String indicator_number) {
			this.indicator_number = indicator_number;
		}

	}
}
