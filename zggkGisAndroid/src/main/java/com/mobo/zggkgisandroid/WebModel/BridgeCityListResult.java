package com.mobo.zggkgisandroid.WebModel;

import java.util.ArrayList;

import com.mobo.zggkgisandroid.ClassfyInfo.BridgeCityInfo;

public class BridgeCityListResult extends BaseResult {

	private String province_name;// 省份名称
	private String province_code;// 省份编码
	private String province_bridge_num;// 省份桥梁总数
	private ArrayList<BridgeCityInfo> city_data_list;// 城市桥梁

	public String getProvince_name() {
		return province_name;
	}

	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}

	public String getProvince_code() {
		return province_code;
	}

	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}

	public String getProvince_bridge_num() {
		return province_bridge_num;
	}

	public void setProvince_bridge_num(String province_bridge_num) {
		this.province_bridge_num = province_bridge_num;
	}

	public ArrayList<BridgeCityInfo> getCity_data_list() {
		return city_data_list;
	}

	public void setCity_data_list(ArrayList<BridgeCityInfo> city_data_list) {
		this.city_data_list = city_data_list;
	}

}
