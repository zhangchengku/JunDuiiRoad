package com.mobo.zggkgisandroid.ClassfyInfo;

import java.io.Serializable;

public class ServicePositionInfo implements Serializable {

	private String service_area_name;// 服务区名称
	private String service_area_code;// 服务区编码
	private String road_code;// 所在路线编码
	private String road_name;// 所在路线编码
	private String longitude;// 经度
	private String latitude;// 纬度
	private String service_area_center_station;// 中心桩号

	public String getService_area_name() {
		return service_area_name;
	}

	public void setService_area_name(String service_area_name) {
		this.service_area_name = service_area_name;
	}

	public String getService_area_code() {
		return service_area_code;
	}

	public void setService_area_code(String service_area_code) {
		this.service_area_code = service_area_code;
	}

	public String getRoad_code() {
		return road_code;
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

	public String getService_area_center_station() {
		return service_area_center_station;
	}

	public void setService_area_center_station(
			String service_area_center_station) {
		this.service_area_center_station = service_area_center_station;
	}

}
