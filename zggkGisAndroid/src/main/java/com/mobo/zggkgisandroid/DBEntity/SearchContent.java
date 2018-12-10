package com.mobo.zggkgisandroid.DBEntity;

import com.j256.ormlite.field.DatabaseField;

/**
 * 搜索页面搜索记录
 * 
 * @author WuXiaoHao
 *
 */
public class SearchContent {
	@DatabaseField(generatedId = true)
	int id;
	@DatabaseField
	String route_code;// 路线编码/桥梁所在路线的编码
	@DatabaseField
	String route_name;// 路线名称/桥梁所在路线的名称
	@DatabaseField
	String route_length;// 路线长度(单位米 数据库不存储单位)
	@DatabaseField
	String city_name;// 所属省份
	@DatabaseField
	String province_code;// 省份编码
	@DatabaseField
	String bridge_name;// 桥梁名称
	@DatabaseField
	String bridge_length;// 桥梁长度(单位米 数据库不存储单位)
	@DatabaseField
	String type;// 0桥梁，1国道，2省道
	@DatabaseField
	long time;// 时间
	@DatabaseField
	String bridge_code;//桥梁编码

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoute_code() {
		return route_code;
	}

	public void setRoute_code(String route_code) {
		this.route_code = route_code;
	}

	public String getRoute_name() {
		return route_name;
	}

	public void setRoute_name(String route_name) {
		this.route_name = route_name;
	}

	public String getRoute_length() {
		return route_length;
	}

	public void setRoute_length(String route_length) {
		this.route_length = route_length;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getBridge_name() {
		return bridge_name;
	}

	public void setBridge_name(String bridge_name) {
		this.bridge_name = bridge_name;
	}

	public String getBridge_length() {
		return bridge_length;
	}

	public void setBridge_length(String bridge_length) {
		this.bridge_length = bridge_length;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getBridge_code() {
		return bridge_code;
	}

	public void setBridge_code(String bridge_code) {
		this.bridge_code = bridge_code;
	}

	public String getProvince_code() {
		return province_code;
	}

	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}

}
