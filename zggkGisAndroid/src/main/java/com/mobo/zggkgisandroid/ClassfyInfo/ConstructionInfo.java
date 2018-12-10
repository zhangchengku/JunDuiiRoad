package com.mobo.zggkgisandroid.ClassfyInfo;

import java.io.Serializable;

public class ConstructionInfo implements Serializable {

	private String construction_id;// 施工id
	private String construction_name;// 施工名称
	private String construction_code;// 施工编码
	private String road_code;// 线路编码
	private String construction_thumb_img_data;// 小图
	private String longitude;// 经度
	private String latitude;// 纬度
	private String construction_location_route;// 所在路线
	private String construction_center_station;// 桩号
	private String construction_investment_money;// 投资金额
	private String construction_comp_progress;// 完成进度
	private String construction_manage_unit;// 管养单位
	private String construction_construct_unit;// 施工单位
	private String construction_technical_grade;// 技术等级

	public String getConstruction_id() {
		return construction_id;
	}

	public void setConstruction_id(String construction_id) {
		this.construction_id = construction_id;
	}

	public String getConstruction_name() {
		return construction_name;
	}

	public void setConstruction_name(String construction_name) {
		this.construction_name = construction_name;
	}

	public String getConstruction_code() {
		return construction_code;
	}

	public void setConstruction_code(String construction_code) {
		this.construction_code = construction_code;
	}

	public String getRoad_code() {
		return road_code;
	}

	public void setRoad_code(String road_code) {
		this.road_code = road_code;
	}

	public String getConstruction_thumb_img_data() {
		return construction_thumb_img_data;
	}

	public void setConstruction_thumb_img_data(
			String construction_thumb_img_data) {
		this.construction_thumb_img_data = construction_thumb_img_data;
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

	public String getConstruction_location_route() {
		return construction_location_route;
	}

	public void setConstruction_location_route(
			String construction_location_route) {
		this.construction_location_route = construction_location_route;
	}

	public String getConstruction_center_station() {
		return construction_center_station;
	}

	public void setConstruction_center_station(
			String construction_center_station) {
		this.construction_center_station = construction_center_station;
	}

	public String getConstruction_investment_money() {
		return construction_investment_money;
	}

	public void setConstruction_investment_money(
			String construction_investment_money) {
		this.construction_investment_money = construction_investment_money;
	}

	public String getConstruction_comp_progress() {
		return construction_comp_progress;
	}

	public void setConstruction_comp_progress(String construction_comp_progress) {
		this.construction_comp_progress = construction_comp_progress;
	}

	public String getConstruction_manage_unit() {
		return construction_manage_unit;
	}

	public void setConstruction_manage_unit(String construction_manage_unit) {
		this.construction_manage_unit = construction_manage_unit;
	}

	public String getConstruction_construct_unit() {
		return construction_construct_unit;
	}

	public void setConstruction_construct_unit(
			String construction_construct_unit) {
		this.construction_construct_unit = construction_construct_unit;
	}

	public String getConstruction_technical_grade() {
		return construction_technical_grade;
	}

	public void setConstruction_technical_grade(
			String construction_technical_grade) {
		this.construction_technical_grade = construction_technical_grade;
	}

}
