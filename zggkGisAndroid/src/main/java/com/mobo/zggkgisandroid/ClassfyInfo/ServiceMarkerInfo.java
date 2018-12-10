package com.mobo.zggkgisandroid.ClassfyInfo;

import java.util.ArrayList;

public class ServiceMarkerInfo {

	private String service_area_totle_num;// 全省服务区总数
	private ArrayList<ServicePositionInfo> service_area_position_list;// 服务区列表

	public String getService_area_totle_num() {
		return service_area_totle_num;
	}

	public void setService_area_totle_num(String service_area_totle_num) {
		this.service_area_totle_num = service_area_totle_num;
	}

	public ArrayList<ServicePositionInfo> getService_area_position_list() {
		return service_area_position_list;
	}

	public void setService_area_position_list(
			ArrayList<ServicePositionInfo> service_area_position_list) {
		this.service_area_position_list = service_area_position_list;
	}

}
