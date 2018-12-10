package com.mobo.zggkgisandroid.ClassfyInfo;

import java.io.Serializable;
import java.util.ArrayList;

public class PCIInfo implements Serializable {

	private ArrayList<IndicatorInfo> indicator_info;
	private ArrayList<RoadsInfo> road_indicator_list;
	private String result_mil_num;// 评定里程
	private String road_start_station;// 评定起点
	private String road_end_station;// 评定终点

	public String getRoad_start_station() {
		return road_start_station;
	}

	public void setRoad_start_station(String road_start_station) {
		this.road_start_station = road_start_station;
	}

	public String getRoad_end_station() {
		return road_end_station;
	}

	public void setRoad_end_station(String road_end_station) {
		this.road_end_station = road_end_station;
	}

	public ArrayList<IndicatorInfo> getIndicator_info() {
		return indicator_info;
	}

	public void setIndicator_info(ArrayList<IndicatorInfo> indicator_info) {
		this.indicator_info = indicator_info;
	}

	public ArrayList<RoadsInfo> getRoad_indicator_list() {
		return road_indicator_list;
	}

	public void setRoad_indicator_list(ArrayList<RoadsInfo> road_indicator_list) {
		this.road_indicator_list = road_indicator_list;
	}

	public String getResult_mil_num() {
		return result_mil_num;
	}

	public void setResult_mil_num(String result_mil_num) {
		this.result_mil_num = result_mil_num;
	}

}
