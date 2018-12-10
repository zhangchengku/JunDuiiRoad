package com.mobo.zggkgisandroid.WebModel;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;

public class CityList implements Serializable {
	@DatabaseField(id = true)
	String province_code;// 省份编码 VARCHAR 123001 代表全国
	@DatabaseField
	String province_name;// 省份名称 VARCHAR
	@DatabaseField
	long time;// 时间

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

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

}
