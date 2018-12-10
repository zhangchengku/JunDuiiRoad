package com.mobo.zggkgisandroid.WebModel;

import java.io.Serializable;
import java.util.ArrayList;

/***
 * 11.4.省份高速公路列表
 * 
 * @author WuXiaoHao
 *
 */
public class ExpresswayListResult extends BaseResult implements Serializable {
	ArrayList<ExpresswayList> results;

	public ArrayList<ExpresswayList> getResults() {
		return results;
	}

	public void setResults(ArrayList<ExpresswayList> results) {
		this.results = results;
	}

	public class ExpresswayList implements Serializable {
		String road_name;// 高速公路名称 VARCHAR
		String road_code;// 高速公路编码 VARCHAR
		String road_mail;// 公路全长 VARCHAR

		public String getRoad_mail() {
			return road_mail;
		}

		public void setRoad_mail(String road_mail) {
			this.road_mail = road_mail;
		}

		public String getRoad_name() {
			return road_name;
		}

		public void setRoad_name(String road_name) {
			this.road_name = road_name;
		}

		public String getRoad_code() {
			return road_code;
		}

		public void setRoad_code(String road_code) {
			this.road_code = road_code;
		}

	}
}
