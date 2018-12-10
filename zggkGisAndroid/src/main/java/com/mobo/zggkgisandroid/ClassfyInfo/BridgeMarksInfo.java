package com.mobo.zggkgisandroid.ClassfyInfo;

import java.io.Serializable;
import java.util.ArrayList;

import com.mobo.zggkgisandroid.ClassfyInfo.BridgePositionInfo;

public class BridgeMarksInfo implements Serializable {

	private String area_name;//区域名称
	private String area_code;//区域编码
	private String longitude;//经度
	private String latitude;//纬度
	private String bridge_number;//桥梁数量
	private ArrayList<BridgePositionInfo> bridge_positions_list;// 桥梁列表集合

//	public String getBridge_totle_num() {
//		return bridge_totle_num;
//	}
//
//	public void setBridge_totle_num(String bridge_totle_num) {
//		this.bridge_totle_num = bridge_totle_num;
//	}

	public ArrayList<BridgePositionInfo> getBridge_positions_list() {
		return bridge_positions_list;
	}

	public void setBridge_positions_list(ArrayList<BridgePositionInfo> bridge_positions_list) {
		this.bridge_positions_list = bridge_positions_list;
	}

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
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

	public String getBridge_number() {
		return bridge_number;
	}

	public void setBridge_number(String bridge_number) {
		this.bridge_number = bridge_number;
	}

}
