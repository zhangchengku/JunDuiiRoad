package com.mobo.zggkgisandroid.ClassfyInfo;

import java.io.Serializable;

public class TunnelInfo implements Serializable {

	private String tunnel_id;// 设施id
	private String tunnel_name;// 设施名称
	private String tunnel_code;// 设施编码
	private String road_code;// 线路编码
	private String tunnel_thumb_img_data;// 小图
	private String longitude;// 经度
	private String latitude;// 纬度
	private String tunnel_location_route;// 所在路线
	private String tunnel_center_station;// 中心桩号
	private String tunnel_length;// 隧道长度
	private String tunnel_height;// 隧道净高
	private String tunnel_evaluate_grade;// 评定等级，来自键值

	public String getTunnel_id() {
		return tunnel_id;
	}

	public void setTunnel_id(String tunnel_id) {
		this.tunnel_id = tunnel_id;
	}

	public String getTunnel_name() {
		return tunnel_name;
	}

	public void setTunnel_name(String tunnel_name) {
		this.tunnel_name = tunnel_name;
	}

	public String getTunnel_code() {
		return tunnel_code;
	}

	public void setTunnel_code(String tunnel_code) {
		this.tunnel_code = tunnel_code;
	}

	public String getRoad_code() {
		return road_code;
	}

	public void setRoad_code(String road_code) {
		this.road_code = road_code;
	}

	public String getTunnel_thumb_img_data() {
		return tunnel_thumb_img_data;
	}

	public void setTunnel_thumb_img_data(String tunnel_thumb_img_data) {
		this.tunnel_thumb_img_data = tunnel_thumb_img_data;
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

	public String getTunnel_location_route() {
		return tunnel_location_route;
	}

	public void setTunnel_location_route(String tunnel_location_route) {
		this.tunnel_location_route = tunnel_location_route;
	}

	public String getTunnel_center_station() {
		return tunnel_center_station;
	}

	public void setTunnel_center_station(String tunnel_center_station) {
		this.tunnel_center_station = tunnel_center_station;
	}

	public String getTunnel_length() {
		return tunnel_length;
	}

	public void setTunnel_length(String tunnel_length) {
		this.tunnel_length = tunnel_length;
	}

	public String getTunnel_height() {
		return tunnel_height;
	}

	public void setTunnel_height(String tunnel_height) {
		this.tunnel_height = tunnel_height;
	}

	public String getTunnel_evaluate_grade() {
		return tunnel_evaluate_grade;
	}

	public void setTunnel_evaluate_grade(String tunnel_evaluate_grade) {
		this.tunnel_evaluate_grade = tunnel_evaluate_grade;
	}

}
