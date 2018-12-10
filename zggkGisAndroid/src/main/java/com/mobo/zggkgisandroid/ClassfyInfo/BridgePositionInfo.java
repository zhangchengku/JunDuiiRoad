package com.mobo.zggkgisandroid.ClassfyInfo;

import java.io.Serializable;

public class BridgePositionInfo implements Serializable {

	private String bridge_name;// 桥梁名称
	private String bridge_code;// 桥梁编码
	private String road_code;// 所在路线编码
	private String road_name;// 所在了路线名称
	private String longitude;// 经度
	private String latitude;// 纬度
	private String bridge_is_ngtc;// 是否国检
	private String bridge_thumb_img_data;// 小图
	private String bridge_location_route;// 所在路线
	private String bridge_center_station;// 中心桩号
	private String bridge_length;// 桥梁长度
	private String bridge_width;// 桥梁宽度
	private String navigation_level;// 通航等级
	private String load_weight_level;// 设计载荷等级
	private String bridge_span_type;// 跨境分类 来自键值
	private String bridge_technical_grade;// 技术等级 来自键值

	private String bridge_wight;// 最大跨径
	private String bridge_clean_height;// 桥梁净高
	private String bridge_year;// 修建年限
	private String bridge_type;// 桥梁分类
	private String bridge_materia_type;// 桥梁类型

	public String getBridge_wight() {
		return bridge_wight;
	}

	public void setBridge_wight(String bridge_wight) {
		this.bridge_wight = bridge_wight;
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

	public String getRoad_name() {
		return road_name;
	}

	public void setRoad_name(String road_name) {
		this.road_name = road_name;
	}

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

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getBridge_is_ngtc() {
		return bridge_is_ngtc;
	}

	public void setBridge_is_ngtc(String bridge_is_ngtc) {
		this.bridge_is_ngtc = bridge_is_ngtc;
	}

	public String getBridge_thumb_img_data() {
		return bridge_thumb_img_data;
	}

	public void setBridge_thumb_img_data(String bridge_thumb_img_data) {
		this.bridge_thumb_img_data = bridge_thumb_img_data;
	}

	public String getBridge_location_route() {
		return bridge_location_route;
	}

	public void setBridge_location_route(String bridge_location_route) {
		this.bridge_location_route = bridge_location_route;
	}

	public String getBridge_center_station() {
		return bridge_center_station;
	}

	public void setBridge_center_station(String bridge_center_station) {
		this.bridge_center_station = bridge_center_station;
	}

	public String getBridge_length() {
		return bridge_length;
	}

	public void setBridge_length(String bridge_length) {
		this.bridge_length = bridge_length;
	}

	public String getBridge_width() {
		return bridge_width;
	}

	public void setBridge_width(String bridge_width) {
		this.bridge_width = bridge_width;
	}

	public String getNavigation_level() {
		return navigation_level;
	}

	public void setNavigation_level(String navigation_level) {
		this.navigation_level = navigation_level;
	}

	public String getLoad_weight_level() {
		return load_weight_level;
	}

	public void setLoad_weight_level(String load_weight_level) {
		this.load_weight_level = load_weight_level;
	}

	public String getBridge_span_type() {
		return bridge_span_type;
	}

	public void setBridge_span_type(String bridge_span_type) {
		this.bridge_span_type = bridge_span_type;
	}

	public String getBridge_technical_grade() {
		return bridge_technical_grade;
	}

	public void setBridge_technical_grade(String bridge_technical_grade) {
		this.bridge_technical_grade = bridge_technical_grade;
	}

}
