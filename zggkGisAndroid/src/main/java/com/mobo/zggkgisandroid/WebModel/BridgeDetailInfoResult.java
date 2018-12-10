package com.mobo.zggkgisandroid.WebModel;

import java.util.ArrayList;

/**
 * 9.4 获取桥梁更具体的信息
 * 
 * @author wuhao
 * 
 */
public class BridgeDetailInfoResult extends BaseResult {
	private ArrayList<BridgeDetailInfo> results;

	public ArrayList<BridgeDetailInfo> getResults() {
		return results;
	}

	public void setResults(ArrayList<BridgeDetailInfo> results) {
		this.results = results;
	}

	public class BridgeDetailInfo {
		BaseInfo base_info;// 桥梁信息 对象
		ArrayList<DamageInfo> damage_info;// 损害信息
		EvaluateInfo evaluate_info;// 评定信息 对象

		public BaseInfo getBase_info() {
			return base_info;
		}

		public void setBase_info(BaseInfo base_info) {
			this.base_info = base_info;
		}

		public ArrayList<DamageInfo> getDamage_info() {
			return damage_info;
		}

		public void setDamage_info(ArrayList<DamageInfo> damage_info) {
			this.damage_info = damage_info;
		}

		public EvaluateInfo getEvaluate_info() {
			return evaluate_info;
		}

		public void setEvaluate_info(EvaluateInfo evaluate_info) {
			this.evaluate_info = evaluate_info;
		}

		public class BaseInfo {
			String bridge_name;// 桥梁名称 VARCHAR
			String bridge_code;// 桥梁编码 VARCHAR
			String road_code;// 所在线路 VARCHAR 线路编码 + 线路名称
			String belong_area;// 所在省市 VARCHAR
			String bridge_length;// 桥梁长度 VARCHAR
			String bridge_clean_height;// 桥梁净高 VARCHAR
			String bridge_year;// 修建年限 VARCHAR
			String bridge_type;// 桥梁分类 VARCHAR
			String bridge_materia_type;// 桥梁类型 VARCHAR
			String manage_unit;// 管养单位 VARCHAR
			String bridge_technical_grade;// 技术等级 VARCHAR
			String bridge_station;// 桥梁位置 VARCHAR

			public String getBridge_name() {
				return bridge_name;
			}

			public void setBridge_name(String bridge_name) {
				this.bridge_name = bridge_name;
			}

			public String getBridge_code() {
				return bridge_code;
			}

			public void setBridge_code(String bridge_code) {
				this.bridge_code = bridge_code;
			}

			public String getRoad_code() {
				return road_code;
			}

			public void setRoad_code(String road_code) {
				this.road_code = road_code;
			}

			public String getBelong_area() {
				return belong_area;
			}

			public void setBelong_area(String belong_area) {
				this.belong_area = belong_area;
			}

			public String getBridge_length() {
				return bridge_length;
			}

			public void setBridge_length(String bridge_length) {
				this.bridge_length = bridge_length;
			}

			public String getBridge_clean_height() {
				return bridge_clean_height;
			}

			public void setBridge_clean_height(String bridge_clean_height) {
				this.bridge_clean_height = bridge_clean_height;
			}

			public String getBridge_year() {
				return bridge_year;
			}

			public void setBridge_year(String bridge_year) {
				this.bridge_year = bridge_year;
			}

			public String getBridge_type() {
				return bridge_type;
			}

			public void setBridge_type(String bridge_type) {
				this.bridge_type = bridge_type;
			}

			public String getBridge_materia_type() {
				return bridge_materia_type;
			}

			public void setBridge_materia_type(String bridge_materia_type) {
				this.bridge_materia_type = bridge_materia_type;
			}

			public String getManage_unit() {
				return manage_unit;
			}

			public void setManage_unit(String manage_unit) {
				this.manage_unit = manage_unit;
			}

			public String getBridge_technical_grade() {
				return bridge_technical_grade;
			}

			public void setBridge_technical_grade(String bridge_technical_grade) {
				this.bridge_technical_grade = bridge_technical_grade;
			}

			public String getBridge_station() {
				return bridge_station;
			}

			public void setBridge_station(String bridge_station) {
				this.bridge_station = bridge_station;
			}

		}

		public class DamageInfo {
			String damage_class;// 病害类别 VARCHAR
			String damage_id;// 唯一的病害id VARCHAR
			String belong_parts;// 所属部件 VARCHAR
			String defect_position;// 缺损位置 VARCHAR
			String at_component;// 所在构件 VARCHAR
			String scale;// 标度 VARCHAR
			String damage_detail;// 病害描述 VARCHAR

			public String getDamage_class() {
				return damage_class;
			}

			public void setDamage_class(String damage_class) {
				this.damage_class = damage_class;
			}

			public String getDamage_id() {
				return damage_id;
			}

			public void setDamage_id(String damage_id) {
				this.damage_id = damage_id;
			}

			public String getBelong_parts() {
				return belong_parts;
			}

			public void setBelong_parts(String belong_parts) {
				this.belong_parts = belong_parts;
			}

			public String getDefect_position() {
				return defect_position;
			}

			public void setDefect_position(String defect_position) {
				this.defect_position = defect_position;
			}

			public String getAt_component() {
				return at_component;
			}

			public void setAt_component(String at_component) {
				this.at_component = at_component;
			}

			public String getScale() {
				return scale;
			}

			public void setScale(String scale) {
				this.scale = scale;
			}

			public String getDamage_detail() {
				return damage_detail;
			}

			public void setDamage_detail(String damage_detail) {
				this.damage_detail = damage_detail;
			}

		}

		public class EvaluateInfo {
			String bci;// 桥梁技术状况指数（BCI）VARCHAR
			String evaluate_grade;// 桥梁技术状况评定等级VARCHAR 1到5代表一级到五级
			String evaluate_data;// 评定日期 VARCHAR
			String evaluate_function;// 评定方法 VARCHAR
			String last_time;// 上次检测
			String next_time;// 下次检测
			String evaluate_detail;// 评定说明

			public String getBci() {
				return bci;
			}

			public void setBci(String bci) {
				this.bci = bci;
			}

			public String getEvaluate_grade() {
				return evaluate_grade;
			}

			public void setEvaluate_grade(String evaluate_grade) {
				this.evaluate_grade = evaluate_grade;
			}

			public String getEvaluate_data() {
				return evaluate_data;
			}

			public void setEvaluate_data(String evaluate_data) {
				this.evaluate_data = evaluate_data;
			}

			public String getEvaluate_function() {
				return evaluate_function;
			}

			public void setEvaluate_function(String evaluate_function) {
				this.evaluate_function = evaluate_function;
			}

			public String getLast_time() {
				return last_time;
			}

			public void setLast_time(String last_time) {
				this.last_time = last_time;
			}

			public String getNext_time() {
				return next_time;
			}

			public void setNext_time(String next_time) {
				this.next_time = next_time;
			}

			public String getEvaluate_detail() {
				return evaluate_detail;
			}

			public void setEvaluate_detail(String evaluate_detail) {
				this.evaluate_detail = evaluate_detail;
			}

		}
	}
}
