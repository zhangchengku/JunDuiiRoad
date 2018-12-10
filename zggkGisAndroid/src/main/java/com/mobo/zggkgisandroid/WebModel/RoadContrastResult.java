package com.mobo.zggkgisandroid.WebModel;

import java.util.ArrayList;

/**
 * 8.1.路况省份对比
 * 
 * @author WuXiaoHao
 *
 */
public class RoadContrastResult extends BaseResult {
	ArrayList<RoadContrast> results;

	public ArrayList<RoadContrast> getResults() {
		return results;
	}

	public void setResults(ArrayList<RoadContrast> results) {
		this.results = results;
	}

	public class RoadContrast {
		String indicator_id;// 指标id VARCHAR
		String indicator_value;// 指标名称 VARCHAR
		String indicator_number;// 指标数值 VARCHAR 多个省份的指标数值英文逗号“,”相隔

		public String getIndicator_id() {
			return indicator_id;
		}

		public void setIndicator_id(String indicator_id) {
			this.indicator_id = indicator_id;
		}

		public String getIndicator_value() {
			return indicator_value;
		}

		public void setIndicator_value(String indicator_value) {
			this.indicator_value = indicator_value;
		}

		public String getIndicator_number() {
			return indicator_number;
		}

		public void setIndicator_number(String indicator_number) {
			this.indicator_number = indicator_number;
		}

	}
}
