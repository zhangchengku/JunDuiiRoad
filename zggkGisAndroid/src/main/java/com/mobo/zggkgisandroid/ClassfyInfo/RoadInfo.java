package com.mobo.zggkgisandroid.ClassfyInfo;

import java.io.Serializable;
import java.util.ArrayList;

public class RoadInfo implements Serializable {

	private String road_level;// 线路行政等级，来自键值
	private String road_name;// 线路名称
	private String road_code;// 线路编码
	private String road_mil_num;// 里程数
	private String road_through_place;// 途径
	private String road_start_station;// 起始桩号
	private String road_end_station;// 终点桩号
	private String road_traffic_num;// 交通量
	private LonLatsInfo road_lon_lat;// 起始桩号信息
	
	private String has_pci_data;// 是否有PCI分布数据（即路况）
	private String has_img_data;// 是否有大图数据（即前方图像）

	private ArrayList<RoadLonLatProvince> road_lon_lat_province;// 经纬度集合
	private ArrayList<LonLatsInfo> road_lon_lat_list;// 经纬度列表
	private ArrayList<RoadPCIInfo> road_indicator_distribute;// 搜索结果为单公路时PCI分布结果

	public LonLatsInfo getRoad_lon_lat() {
		return road_lon_lat;
	}

	public void setRoad_lon_lat(LonLatsInfo road_lon_lat) {
		this.road_lon_lat = road_lon_lat;
	}

	public ArrayList<RoadLonLatProvince> getRoad_lon_lat_province() {
		return road_lon_lat_province;
	}

	public void setRoad_lon_lat_province(
			ArrayList<RoadLonLatProvince> road_lon_lat_province) {
		this.road_lon_lat_province = road_lon_lat_province;
	}

	public String getHas_pci_data() {
		return has_pci_data;
	}

	public void setHas_pci_data(String has_pci_data) {
		this.has_pci_data = has_pci_data;
	}

	public String getHas_img_data() {
		return has_img_data;
	}

	public void setHas_img_data(String has_img_data) {
		this.has_img_data = has_img_data;
	}

	public ArrayList<RoadPCIInfo> getRoad_indicator_distribute() {
		return road_indicator_distribute;
	}

	public void setRoad_indicator_distribute(
			ArrayList<RoadPCIInfo> road_indicator_distribute) {
		this.road_indicator_distribute = road_indicator_distribute;
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

	public String getRoad_code() {
		return road_code;
	}

	public void setRoad_code(String road_code) {
		this.road_code = road_code;
	}

	public String getRoad_mil_num() {
		return road_mil_num;
	}

	public void setRoad_mil_num(String road_mil_num) {
		this.road_mil_num = road_mil_num;
	}

	public String getRoad_through_place() {
		return road_through_place;
	}

	public void setRoad_through_place(String road_through_place) {
		this.road_through_place = road_through_place;
	}

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

	public String getRoad_traffic_num() {
		return road_traffic_num;
	}

	public void setRoad_traffic_num(String road_traffic_num) {
		this.road_traffic_num = road_traffic_num;
	}

	public ArrayList<LonLatsInfo> getRoad_lon_lat_list() {
		return road_lon_lat_list;
	}

	public void setRoad_lon_lat_list(ArrayList<LonLatsInfo> road_lon_lat_list) {
		this.road_lon_lat_list = road_lon_lat_list;
	}

}
