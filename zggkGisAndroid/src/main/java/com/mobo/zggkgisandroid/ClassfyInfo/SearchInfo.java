package com.mobo.zggkgisandroid.ClassfyInfo;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchInfo implements Serializable{

	private String province_code;// 省份编码
	private String province_name;// 省份名称
	private String has_pci_data;// 是否有PCI分布数据（即路况）
	private String has_img_data;// 是否有大图数据（即前方图像）

	private String result_mil_num;// 总里程数或总延米数
	private String result_size;// 返回结果条数
	private String result_type;// 返回结果类型
	private String up_or_down;// 上下行

	private ArrayList<RoadInfo> road_list;// 公路搜索集合
	private ArrayList<TunnelInfo> tunnel_list;// 隧道搜索集合

	private ArrayList<ConstructionInfo> construction_list;// 施工搜索集合
	private ArrayList<FacilityInfo> facility_list;// 设施搜索集合
	private ArrayList<ServiceNationalInfo> service_area_national_list;// 服务区搜索全国数据
	private ArrayList<ServicePositionInfo> service_area_positions_list;// 服务区定位点集合
	private ArrayList<BridgeNationalInfo> bridge_national_list;// 桥梁搜索全国数据集
	private ArrayList<BridgePositionInfo> bridge_positions_list;// 桥梁定位点集合
	private ArrayList<ConstructionNationalInfo> construction_national_list;// 施工点搜索全国数据集合
	private ArrayList<ConstructionPositionInfo> construction_positions_list;// 施工点定位点集合
	private ArrayList<DamageNationalInfo> damage_national_list;// 灾毁搜索全国数据集合
	private ArrayList<DamagePositionInfo> damage_positions_list;// 灾毁定位点集合
	private ArrayList<VedioNationalInfo> vedio_national_list;// 视频搜索全国数据集合
	private ArrayList<VedioPositionlInfo> vedio_positions_list;// /视频定位点集合

	public ArrayList<VedioNationalInfo> getVedio_national_list() {
		return vedio_national_list;
	}

	public void setVedio_national_list(
			ArrayList<VedioNationalInfo> vedio_national_list) {
		this.vedio_national_list = vedio_national_list;
	}

	public ArrayList<VedioPositionlInfo> getVedio_positions_list() {
		return vedio_positions_list;
	}

	public void setVedio_positions_list(
			ArrayList<VedioPositionlInfo> vedio_positions_list) {
		this.vedio_positions_list = vedio_positions_list;
	}

	public ArrayList<DamageNationalInfo> getDamage_national_list() {
		return damage_national_list;
	}

	public void setDamage_national_list(
			ArrayList<DamageNationalInfo> damage_national_list) {
		this.damage_national_list = damage_national_list;
	}

	public ArrayList<DamagePositionInfo> getDamage_positions_list() {
		return damage_positions_list;
	}

	public void setDamage_positions_list(
			ArrayList<DamagePositionInfo> damage_positions_list) {
		this.damage_positions_list = damage_positions_list;
	}

	public ArrayList<ConstructionNationalInfo> getConstruction_national_list() {
		return construction_national_list;
	}

	public void setConstruction_national_list(
			ArrayList<ConstructionNationalInfo> construction_national_list) {
		this.construction_national_list = construction_national_list;
	}

	public ArrayList<ConstructionPositionInfo> getConstruction_positions_list() {
		return construction_positions_list;
	}

	public void setConstruction_positions_list(
			ArrayList<ConstructionPositionInfo> construction_positions_list) {
		this.construction_positions_list = construction_positions_list;
	}

	public ArrayList<ServiceNationalInfo> getService_area_national_list() {
		return service_area_national_list;
	}

	public void setService_area_national_list(
			ArrayList<ServiceNationalInfo> service_area_national_list) {
		this.service_area_national_list = service_area_national_list;
	}

	public ArrayList<ServicePositionInfo> getService_area_positions_list() {
		return service_area_positions_list;
	}

	public void setService_area_positions_list(
			ArrayList<ServicePositionInfo> service_area_positions_list) {
		this.service_area_positions_list = service_area_positions_list;
	}

	public ArrayList<BridgeNationalInfo> getBridge_national_list() {
		return bridge_national_list;
	}

	public void setBridge_national_list(
			ArrayList<BridgeNationalInfo> bridge_national_list) {
		this.bridge_national_list = bridge_national_list;
	}

	public ArrayList<BridgePositionInfo> getBridge_positions_list() {
		return bridge_positions_list;
	}

	public void setBridge_positions_list(
			ArrayList<BridgePositionInfo> bridge_positions_list) {
		this.bridge_positions_list = bridge_positions_list;
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

	public String getUp_or_down() {
		return up_or_down;
	}

	public void setUp_or_down(String up_or_down) {
		this.up_or_down = up_or_down;
	}

	public ArrayList<ConstructionInfo> getConstruction_list() {
		return construction_list;
	}

	public void setConstruction_list(
			ArrayList<ConstructionInfo> construction_list) {
		this.construction_list = construction_list;
	}

	public ArrayList<FacilityInfo> getFacility_list() {
		return facility_list;
	}

	public void setFacility_list(ArrayList<FacilityInfo> facility_list) {
		this.facility_list = facility_list;
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

	public String getResult_mil_num() {
		return result_mil_num;
	}

	public void setResult_mil_num(String result_mil_num) {
		this.result_mil_num = result_mil_num;
	}

	public String getResult_size() {
		return result_size;
	}

	public void setResult_size(String result_size) {
		this.result_size = result_size;
	}

	public String getResult_type() {
		return result_type;
	}

	public void setResult_type(String result_type) {
		this.result_type = result_type;
	}

	public ArrayList<RoadInfo> getRoad_list() {
		return road_list;
	}

	public void setRoad_list(ArrayList<RoadInfo> road_list) {
		this.road_list = road_list;
	}

	public ArrayList<TunnelInfo> getTunnel_list() {
		return tunnel_list;
	}

	public void setTunnel_list(ArrayList<TunnelInfo> tunnel_list) {
		this.tunnel_list = tunnel_list;
	}

}
