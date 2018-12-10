package com.mobo.zggkgisandroid.ClassfyInfo;

import java.io.Serializable;
import java.util.ArrayList;

public class RoadPCIInfo implements Serializable{

	private ArrayList<LonLatsInfo> road_lon_lat_list;// 经纬度集合
	private IndicatorInfo indicator_info;// 指标集合

	public ArrayList<LonLatsInfo> getRoad_lon_lat_list() {
		return road_lon_lat_list;
	}

	public void setRoad_lon_lat_list(ArrayList<LonLatsInfo> road_lon_lat_list) {
		this.road_lon_lat_list = road_lon_lat_list;
	}

	public IndicatorInfo getIndicator_info() {
		return indicator_info;
	}

	public void setIndicator_info(IndicatorInfo indicator_info) {
		this.indicator_info = indicator_info;
	}

}
