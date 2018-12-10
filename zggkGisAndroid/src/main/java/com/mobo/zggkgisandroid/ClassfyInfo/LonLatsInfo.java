package com.mobo.zggkgisandroid.ClassfyInfo;

import java.io.Serializable;

public class LonLatsInfo implements Serializable {

	private String road_station;// 桩号
	private String longitude;// 经度
	private String latitude;// 纬度
	private String end_longitude;// 末端经度
	private String end_latitude;// 末端纬度
	private String current_position;// 当前位置
	private String current_feed_unit;// 管养单位
	private String current_indicator_value;// 当前桩号的指标值

	private String road_way_width;// 路面全宽
	private String road_face_type;// 路面类型
	private String daily_traffic_volume;// 日交通量
	private String road_build_years;// 修建年度
	private String road_conservation_years;// 养护年度
	private String road_surface_condition;// 路面状况
	private String road_conservation_advise;// 养护建议

	private String road_data_year;// 路线数据年份
	private String road_direction;// 路线上下行

	public String getRoad_data_year() {
		return road_data_year;
	}

	public void setRoad_data_year(String road_data_year) {
		this.road_data_year = road_data_year;
	}

	public String getRoad_direction() {
		return road_direction;
	}

	public void setRoad_direction(String road_direction) {
		this.road_direction = road_direction;
	}

	private String road_lane_num;// 车道数
	private String bridge_technical_grade;// 技术等级

	public String getDaily_traffic_volume() {
		return daily_traffic_volume;
	}

	public void setDaily_traffic_volume(String daily_traffic_volume) {
		this.daily_traffic_volume = daily_traffic_volume;
	}

	public String getRoad_build_years() {
		return road_build_years;
	}

	public void setRoad_build_years(String road_build_years) {
		this.road_build_years = road_build_years;
	}

	public String getRoad_conservation_years() {
		return road_conservation_years;
	}

	public void setRoad_conservation_years(String road_conservation_years) {
		this.road_conservation_years = road_conservation_years;
	}

	public String getRoad_surface_condition() {
		return road_surface_condition;
	}

	public void setRoad_surface_condition(String road_surface_condition) {
		this.road_surface_condition = road_surface_condition;
	}

	public String getRoad_conservation_advise() {
		return road_conservation_advise;
	}

	public void setRoad_conservation_advise(String road_conservation_advise) {
		this.road_conservation_advise = road_conservation_advise;
	}

	public String getBridge_technical_grade() {
		return bridge_technical_grade;
	}

	public void setBridge_technical_grade(String bridge_technical_grade) {
		this.bridge_technical_grade = bridge_technical_grade;
	}

	public String getRoad_way_width() {
		return road_way_width;
	}

	public void setRoad_way_width(String road_way_width) {
		this.road_way_width = road_way_width;
	}

	public String getRoad_lane_num() {
		return road_lane_num;
	}

	public void setRoad_lane_num(String road_lane_num) {
		this.road_lane_num = road_lane_num;
	}

	public String getRoad_face_type() {
		return road_face_type;
	}

	public void setRoad_face_type(String road_face_type) {
		this.road_face_type = road_face_type;
	}

	public String getCurrent_position() {
		return current_position;
	}

	public void setCurrent_position(String current_position) {
		this.current_position = current_position;
	}

	public String getCurrent_indicator_value() {
		return current_indicator_value;
	}

	public void setCurrent_indicator_value(String current_indicator_value) {
		this.current_indicator_value = current_indicator_value;
	}

	public String getCurrent_feed_unit() {
		return current_feed_unit;
	}

	public void setCurrent_feed_unit(String current_feed_unit) {
		this.current_feed_unit = current_feed_unit;
	}

	public String getEnd_longitude() {
		return end_longitude;
	}

	public void setEnd_longitude(String end_longitude) {
		this.end_longitude = end_longitude;
	}

	public String getEnd_latitude() {
		return end_latitude;
	}

	public void setEnd_latitude(String end_latitude) {
		this.end_latitude = end_latitude;
	}

	public String getRoad_station() {
		return road_station;
	}

	public void setRoad_station(String road_station) {
		this.road_station = road_station;
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

}
