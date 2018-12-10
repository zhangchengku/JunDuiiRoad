package com.mobo.zggkgisandroid.ClassfyInfo;

import java.io.Serializable;

public class ServiceNationalInfo implements Serializable{

	private String province_code;//省份编码
	private String province_name;//省份名称
	private String service_area_num;//服务区数量
	private String longitude;//经度
	private String latitude;//纬度
	public String getProvince_code() {
		return province_code;
	}
	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
	public String getProvince_name() {
		return province_name;
	}
	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}
	public String getService_area_num() {
		return service_area_num;
	}
	public void setService_area_num(String service_area_num) {
		this.service_area_num = service_area_num;
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
