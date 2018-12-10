package com.mobo.zggkgisandroid.WebModel;

import java.util.ArrayList;

/**
 * 7.3统计-路况统计
 * 
 * @author WuXiaoHao
 *
 */
public class RoadTrafficResult extends BaseResult {
	ArrayList<RoadTraffic> results;

	public ArrayList<RoadTraffic> getResults() {
		return results;
	}

	public void setResults(ArrayList<RoadTraffic> results) {
		this.results = results;
	}

	public class RoadTraffic {
		String year;// 年份 VARCHAR 例如：2015
		ArrayList<DataCircle> data_circle;// 饼状图统计数据 集合
		ArrayList<TableData> table_data;// 表格数据 集合

		public String getYear() {
			return year;
		}

		public void setYear(String year) {
			this.year = year;
		}

		public ArrayList<DataCircle> getData_circle() {
			return data_circle;
		}

		public void setData_circle(ArrayList<DataCircle> data_circle) {
			this.data_circle = data_circle;
		}

		public ArrayList<TableData> getTable_data() {
			return table_data;
		}

		public void setTable_data(ArrayList<TableData> table_data) {
			this.table_data = table_data;
		}

		public class DataCircle {
			DataCircleData data_circle_data;

			public DataCircleData getData_circle_data() {
				return data_circle_data;
			}

			public void setData_circle_data(DataCircleData data_circle_data) {
				this.data_circle_data = data_circle_data;
			}

			public class DataCircleData {
				String data_circle_name;// 统计数据名称 VARCHAR 例如：沥青路面
				String data_circle_value;// 所占百分比 VARCHAR 例如：20

				public String getData_circle_name() {
					return data_circle_name;
				}

				public void setData_circle_name(String data_circle_name) {
					this.data_circle_name = data_circle_name;
				}

				public String getData_circle_value() {
					return data_circle_value;
				}

				public void setData_circle_value(String data_circle_value) {
					this.data_circle_value = data_circle_value;
				}

			}

		}

		public class TableData {
			String indicator_level_name;// 指标等级名称 VARCHAR 优，良，一类2类等
			String indicator_number;// 指标数值 VARCHAR 数值类的以逗号分隔数据
			String indicator_grade;// 指标等级 VARCHAR 返回1到5
			// 1到5等级对应的颜色值固定。1 代表优 ，2代表良，3代表中，4代表次，5代表差。具体颜色值见文档附件的图片说明。
			String indicator_percentage;// 指标所占百分比 VARCHAR 例如：20

			public String getIndicator_level_name() {
				return indicator_level_name;
			}

			public void setIndicator_level_name(String indicator_level_name) {
				this.indicator_level_name = indicator_level_name;
			}

			public String getIndicator_number() {
				return indicator_number;
			}

			public void setIndicator_number(String indicator_number) {
				this.indicator_number = indicator_number;
			}

			public String getIndicator_grade() {
				return indicator_grade;
			}

			public void setIndicator_grade(String indicator_grade) {
				this.indicator_grade = indicator_grade;
			}

			public String getIndicator_percentage() {
				return indicator_percentage;
			}

			public void setIndicator_percentage(String indicator_percentage) {
				this.indicator_percentage = indicator_percentage;
			}

		}
	}
}
