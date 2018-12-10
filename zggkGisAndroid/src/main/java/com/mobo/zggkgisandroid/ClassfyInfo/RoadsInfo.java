package com.mobo.zggkgisandroid.ClassfyInfo;

import java.io.Serializable;
import java.util.ArrayList;

public class RoadsInfo implements Serializable{

	private String road_code;// 公路编码
	private String road_level;// 线路行政等级
	private String road_name;// 公路名称
	private ArrayList<RoadPCIInfo> road_indicator_distribute;// 公路PCI分布结果

	public String getRoad_code() {
		return road_code;
	}

	public void setRoad_code(String road_code) {
		this.road_code = road_code;
	}

	public String getRoad_level() {
		return road_level;
	}

	public void setRoad_level(String road_level) {
		this.road_level = road_level;
	}

	public String getRoad_name() {
		return road_name;
	}

	public void setRoad_name(String road_name) {
		this.road_name = road_name;
	}

	public ArrayList<RoadPCIInfo> getRoad_indicator_distribute() {
		return road_indicator_distribute;
	}

	public void setRoad_indicator_distribute(
			ArrayList<RoadPCIInfo> road_indicator_distribute) {
		this.road_indicator_distribute = road_indicator_distribute;
	}

}
