package com.mobo.zggkgisandroid.ClassfyInfo;

import java.io.Serializable;

public class FacilityInfo implements Serializable {

	private String facility_id;// 设施id
	private String facility_name;// 设施名称
	private String facility_code;// 设施编码
	private String road_code;// 线路编码
	private String facility_thumb_img_data;// 小图
	private String longitude;// 经度
	private String latitude;// 纬度
	private String facility_location_route;// 所在路线
	private String facility_center_station;// 桩号

	public String getFacility_id() {
		return facility_id;
	}

	public void setFacility_id(String facility_id) {
		this.facility_id = facility_id;
	}

	public String getFacility_name() {
		return facility_name;
	}

	public void setFacility_name(String facility_name) {
		this.facility_name = facility_name;
	}

	public String getFacility_code() {
		return facility_code;
	}

	public void setFacility_code(String facility_code) {
		this.facility_code = facility_code;
	}

	public String getRoad_code() {
		return road_code;
	}

	public void setRoad_code(String road_code) {
		this.road_code = road_code;
	}

	public String getFacility_thumb_img_data() {
		return facility_thumb_img_data;
	}

	public void setFacility_thumb_img_data(String facility_thumb_img_data) {
		this.facility_thumb_img_data = facility_thumb_img_data;
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

	public String getFacility_location_route() {
		return facility_location_route;
	}

	public void setFacility_location_route(String facility_location_route) {
		this.facility_location_route = facility_location_route;
	}

	public String getFacility_center_station() {
		return facility_center_station;
	}

	public void setFacility_center_station(String facility_center_station) {
		this.facility_center_station = facility_center_station;
	}

}
