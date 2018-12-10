package com.mobo.zggkgisandroid.ClassfyInfo;

import java.io.Serializable;

public class BridgeNationalInfo implements Serializable {

	private String province_code;// 省份编码
	private String province_name;// 省份名称
	private String bridge_num;// 桥梁数量
	private String longitude;// 经度
	private String latitude;// 纬度

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

	public String getBridge_num() {
		return bridge_num;
	}

	public void setBridge_num(String bridge_num) {
		this.bridge_num = bridge_num;
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
